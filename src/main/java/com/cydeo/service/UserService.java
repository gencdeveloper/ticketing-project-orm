package com.cydeo.service;

import com.cydeo.dto.UserDTO;
import com.cydeo.entity.User;

import java.util.List;

/** !!!! Always Service layer you gonna put DTO! !!!
 * This comment suggests that DTOs should be used in the service layer.
 * DTOs (Data Transfer Objects) are objects used for transferring data between different layers,
 * and they often help in decoupling layers.
 *This interface defines basic functionalities related to users and expects classes that implement this interface to provide the actual implementation.
 * The service layer typically contains business logic and often interacts with lower layers, such as database operations.*/
public interface UserService  {
    //Always Service layer you gonna put DTO!

    /**This method is expected to return a list of UserDTO objects, representing all users.*/
    List<UserDTO> listAllUsers();

    /**This method is expected to return a UserDTO object for a given username.*/
    UserDTO findByUserName(String username);

    /**This method is used for saving a user. It takes a UserDTO as a parameter.*/
    void save(UserDTO dto);

    /**This method is used for updating a user. It takes a UserDTO as a parameter and should return the updated user.*/
    UserDTO update(UserDTO dto);

    /**This method is used for deleting a user based on the username.*/
    void deleteByUserName(String username);

}
