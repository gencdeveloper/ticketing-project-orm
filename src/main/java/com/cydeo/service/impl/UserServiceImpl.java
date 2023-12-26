package com.cydeo.service.impl;

import com.cydeo.dto.ProjectDTO;
import com.cydeo.dto.TaskDTO;
import com.cydeo.dto.UserDTO;
import com.cydeo.entity.User;
import com.cydeo.mapper.UserMapper;
import com.cydeo.repository.UserRepository;
import com.cydeo.service.ProjectService;
import com.cydeo.service.TaskService;
import com.cydeo.service.UserService;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
// how spring understand this class implemented for UserService you have to put @Service annotation.
//Because you can implement many classes to UserService interface:)
public class UserServiceImpl implements UserService {
    /**Private final injection advantages; no need to remember create constructor, because if you did not build constructor
     * for your Private final injection, java automatically will remind you:) */
    private final UserRepository userRepository;// Inject the UserRepository
    private final UserMapper userMapper; // Inject the UserRepository
    private final ProjectService projectService;
    private final TaskService taskService;

    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper, @Lazy ProjectService projectService, TaskService taskService) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.projectService = projectService;
        this.taskService = taskService;
    }



    @Override
    public List<UserDTO> listAllUsers() {
        //go to database then bring data
        List<User> userList = userRepository.findAll(Sort.by("firstName"));//this method provided by the JPA
        return userList.stream().map(userMapper::convertToDTO).collect(Collectors.toList());
    }

    @Override
    public UserDTO findByUserName(String username) {
        User user = userRepository.findByUserName(username);
        return userMapper.convertToDTO(user);
    }

    @Override
    public void save(UserDTO dto) {

        userRepository.save(userMapper.convertToEntity(dto));
    }

    @Override
    public UserDTO update(UserDTO dto) {
        //find current user as a capturing id.I am getting object from database
        User user = userRepository.findByUserName(dto.getUserName());

        //converting from dto to entity with map as a updated.
       User convertedUser =  userMapper.convertToEntity(dto);

        //set id to converted object.
        convertedUser.setId(user.getId());

        //save updated user
        userRepository.save(convertedUser);

        return findByUserName(dto.getUserName());
    }

    @Override
    public void deleteByUserName(String username) {
      userRepository.deleteByUserName(username);
    }

    @Override
    public void delete(String username) {
        //I will not delete from db
        //I will change the flag and keep it in the db

        User user = userRepository.findByUserName(username);
        if(checkIfUserCanBeDeleted(user)){
            user.setIsDeleted(true);
            user.setUserName(user.getUserName() + "-" +user.getId());
            userRepository.save(user);
        }
    }

    private boolean checkIfUserCanBeDeleted(User user){
        switch (user.getRole().getDescription()){
            case "Manager" :
                List<ProjectDTO> projectDTOList = projectService.readAllByAssignedManager(user);
                return projectDTOList.size() == 0;
            case "Employee":
                List<TaskDTO> tasktDTOList = taskService.readAllByAssignedManager(user);
                return tasktDTOList.size() == 0;
            default:
                return true;

        }
    }

    @Override
    public List<UserDTO> listAllByRole(String role) {
        List<User> users = userRepository.findAllByRoleDescriptionIgnoreCase(role);

        return users.stream().map(userMapper::convertToDTO).collect(Collectors.toList());
    }
}
