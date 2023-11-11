package com.example.mftbackendclientjavaspring.repository;


import com.example.mftbackendclientjavaspring.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(String role);
}
