package com.cydeo.repository;

import com.cydeo.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository  extends JpaRepository<Role,Long> {

    //build all queries that will bring data from DB.
    //We have 2 ways; derive, @Query(JPA-Native)
}
