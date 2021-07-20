package com.javaneeds.javaneeds.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import com.javaneeds.javaneeds.Repository.TutorialRepository;
import com.javaneeds.javaneeds.Services.FileUploadService;
import com.javaneeds.javaneeds.models.Tutorials;

@RestController
@RequestMapping("/api/tutorials")
public class TutorialsController {

    @Autowired
    TutorialRepository tutorialRepository;

    @Autowired
    private FileUploadService fileStorageService;

    @GetMapping("/")
    public ResponseEntity<?> getAllTutorials(@RequestParam(defaultValue = "0") Integer pageNo, 
    @RequestParam(defaultValue = "10") Integer pageSize){
      
      Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by("id").ascending());
      Page<Tutorials> t =  tutorialRepository.findAll(paging);
      return ResponseEntity.ok(t);

    } 

    @GetMapping("/{id}")
    public ResponseEntity<?> getTutorial(@PathVariable("id") int id){
        Optional<Tutorials> t =  tutorialRepository.findById(id);
        return ResponseEntity.ok(t.get());
    } 
    
    @PostMapping(value = "/",consumes = MediaType.MULTIPART_FORM_DATA_VALUE )
    public ResponseEntity<Tutorials> createTutorial( Tutorials tutorial,@RequestPart MultipartFile file) {
      try {
        String fileName = fileStorageService.storeFile(file);
        tutorial.setLogo(fileName);
        tutorial.setIs_active(true);

        //checking the role if user or admin
        if (AuthorityUtils.authorityListToSet(SecurityContextHolder.getContext().getAuthentication().getAuthorities()).contains("ROLE_ADMIN")) {
          tutorial.setIs_approved(true);
        }
        else{
          tutorial.setIs_approved(false);
        }
       
        Tutorials _tutorial = tutorialRepository.save(tutorial);
        return ResponseEntity.ok(_tutorial);
      } catch (Exception e) {
        return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
      }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Tutorials> updateTutorial(@PathVariable("id") long id, @RequestBody Tutorials tutorial) {
      Optional<Tutorials> tutorialData = tutorialRepository.findById(id);
  
      if (tutorialData.isPresent()) {
        Tutorials _tutorial = tutorialData.get();
        _tutorial.setName(tutorial.getName());
        _tutorial.setDescription(tutorial.getDescription());
        return new ResponseEntity<>(tutorialRepository.save(_tutorial), HttpStatus.OK);
      } else {
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
      }
    }
    


}
