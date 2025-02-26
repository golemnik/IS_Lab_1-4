package com.golem.lab.repository;

import com.golem.lab.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;


@Repository
public interface RoleRepository extends JpaRepository<Role, Long>{
    Set<Role> findByName(String roleName);
}
