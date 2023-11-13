package com.cydeo.repository;

import com.cydeo.entity.Role;
import com.cydeo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;

public interface UserRepository extends JpaRepository<User,Long> {
    //build all queries that will bring data from DB.
    //We have 2 ways; derive, @Query(JPA-Native)
    User findByUserName(String username);

    @Transactional // for the ui queriees
    void deleteByUserName(String username);
}
