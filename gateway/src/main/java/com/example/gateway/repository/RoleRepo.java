package com.example.gateway.repository;

import com.example.gateway.model.Role;
import com.example.gateway.model.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepo extends JpaRepository<Role, Long> {

	Optional<Role> findByName(RoleName roleName);

}
