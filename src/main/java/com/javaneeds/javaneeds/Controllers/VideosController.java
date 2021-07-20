package com.javaneeds.javaneeds.Controllers;

import java.util.Optional;

import com.javaneeds.javaneeds.Repository.VideosRepository;
import com.javaneeds.javaneeds.models.Videos;
import com.javaneeds.javaneeds.pojo.MessageResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/videos")
public class VideosController {
    
    @Autowired
    VideosRepository videosRepository;

    @GetMapping("/")
    public ResponseEntity<?> getAllVideos(@RequestParam(defaultValue = "0") Integer pageNo, 
    @RequestParam(defaultValue = "10") Integer pageSize){

        Pageable page = PageRequest.of(pageNo, pageSize);
        Page<Videos> video = videosRepository.findAll(page);

        return ResponseEntity.ok(video);


    }

    @PostMapping("/")
    public ResponseEntity<?> createVideo(@RequestBody Videos videos){
      
        if(AuthorityUtils.authorityListToSet(SecurityContextHolder.getContext().getAuthentication().getAuthorities()).contains("ROLE_ADMIN")){
            videos.setIs_approved(true);
        }

        videosRepository.save(videos);

        return ResponseEntity.ok(new MessageResponse("Successfuly added video"));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getVideo(@RequestParam("id") Long id){

        Optional<Videos> v = videosRepository.findById(id);

        return ResponseEntity.ok(v.get());
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateVideo(@RequestParam("id") Long id,Videos videos){

        Optional<Videos> videoData = videosRepository.findById(id);

        if (videoData.isPresent()) {
            Videos _video = videoData.get();
            _video.setChannel(videos.getChannel());
            _video.setDescription(videos.getDescription());
            _video.setTitle(videos.getTitle());
            _video.setUrl(videos.getUrl());
            return new ResponseEntity<>(videosRepository.save(_video), HttpStatus.OK);
          } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
          }
    }

}
