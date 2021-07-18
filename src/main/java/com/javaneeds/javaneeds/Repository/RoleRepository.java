package com.javaneeds.javaneeds.Repository;

import java.util.Optional;

import com.javaneeds.javaneeds.models.ERole;
import com.javaneeds.javaneeds.models.Role;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role,Long> {
    Optional<Role> findByName(ERole name);
}
