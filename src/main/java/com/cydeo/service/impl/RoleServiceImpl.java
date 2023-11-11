package com.cydeo.service.impl;

import com.cydeo.dto.RoleDTO;
import com.cydeo.entity.Role;
import com.cydeo.repository.RoleRepository;
import com.cydeo.service.RoleService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {
    //How can i call any method from RoleRepository? --- with injection
    public final RoleRepository roleRepository;

    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public List<RoleDTO> listAllRoles() {
        //controller calling me and requesting all the roles.
        //So... I need to go to database and bring all the roles from there.
        List<Role>roleList = roleRepository.findAll();

        //convert entity to DTO --Mapper

        return roleList;
    }

    @Override
    public RoleDTO findById(Long id) {
        return null;
    }
}
