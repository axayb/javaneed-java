package com.javaneeds.javaneeds.Repository;

import com.javaneeds.javaneeds.models.Videos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VideosRepository extends JpaRepository<Videos,Long>  {
    
}
