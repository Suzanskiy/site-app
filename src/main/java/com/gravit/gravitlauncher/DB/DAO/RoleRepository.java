package com.gravit.gravitlauncher.DB.DAO;

import com.gravit.gravitlauncher.Entity.Role;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface RoleRepository extends CrudRepository <Role, Integer> {

    Optional<Role> findByRoleName (String name);
}
