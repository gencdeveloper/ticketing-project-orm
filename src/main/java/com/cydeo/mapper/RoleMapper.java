package com.cydeo.mapper;

import com.cydeo.dto.RoleDTO;
import com.cydeo.entity.Role;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class RoleMapper {
    //if you want to use any method from ModelMapper class, what I need to do?
        //inject!
    private final ModelMapper modelMapper;

    public RoleMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    //convertEntity
    public Role convertToEntity(RoleDTO dto){
        return modelMapper.map(dto, Role.class);
    }

    //convertoDto
    public RoleDTO converToDto(Role entity){
        return modelMapper.map(entity, RoleDTO.class);
    }


}
