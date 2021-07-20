package com.javaneeds.javaneeds.Repository;

import java.util.List;
import java.util.Optional;

import com.javaneeds.javaneeds.models.Tutorials;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TutorialRepository extends JpaRepository<Tutorials,Long> {
    
    Optional<Tutorials> findById(int Id);
}
