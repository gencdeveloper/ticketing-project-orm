package com.cydeo.service.impl;

import com.cydeo.dto.ProjectDTO;
import com.cydeo.dto.TaskDTO;
import com.cydeo.entity.Task;
import com.cydeo.entity.User;
import com.cydeo.enums.Status;
import com.cydeo.mapper.ProjectMapper;
import com.cydeo.mapper.TaskMapper;
import com.cydeo.mapper.UserMapper;
import com.cydeo.repository.TaskRepository;
import com.cydeo.service.TaskService;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper; // Inject the UserRepository
    private final ProjectMapper projectMapper;

    public TaskServiceImpl(TaskRepository taskRepository, TaskMapper taskMapper, ProjectMapper projectMapper) {
        this.taskRepository = taskRepository;
        this.taskMapper = taskMapper;
        this.projectMapper = projectMapper;
    }

    @Override
    public TaskDTO findById(Long id) {

       Optional<Task> task = taskRepository.findById(id);
       if(task.isPresent()){
           return taskMapper.convertToDTO(task.get());
       }
        return null;
    }

    @Override
    public List<TaskDTO> listAllTasks() {
        //go to database then bring data
        return taskRepository.findAll().stream().map(taskMapper::convertToDTO).collect(Collectors.toList());


    }

    @Override
    public void save(TaskDTO dto) {

        dto.setTaskStatus(Status.OPEN);
        dto.setAssignedDate(LocalDate.now());
        taskRepository.save(taskMapper.convertToEntity(dto));

    }

    @Override
    public void update(TaskDTO dto) {
//find current user as a capturing id.I am getting object from database
        Optional<Task> task = taskRepository.findById(dto.getId());


        //converting from dto to entity with map as a updated.
        Task convertedTask =taskMapper.convertToEntity(dto);

        if(task.isPresent()){
            convertedTask.setId(task.get().getId());
            convertedTask.setTaskStatus(task.get().getTaskStatus());
            convertedTask.setAssignedDate(task.get().getAssignedDate());
            taskRepository.save(convertedTask);
        }

    }

    @Override
    public void delete(Long id) {
        //I will not delete from db
        //I will change the flag and keep it in the db

        Optional<Task> task = taskRepository.findById(id);

            if(task.isPresent()){
                task.get().setIsDeleted(true);
                taskRepository.save(task.get());

            }
    }

    @Override
    public int totalNonCompletedTask(String projectCode) {
        return taskRepository.totalNonCompletedTasks(projectCode);
    }

    @Override
    public int totalCompletedTask(String projectCode) {
        return taskRepository.totalCompletedTasks(projectCode);
    }

    @Override
    public void deleteByProject(ProjectDTO project) {
        List<TaskDTO> list = listAllByProject(project);
        list.forEach(taskDTO -> delete(taskDTO.getId()));
    }

    private List<TaskDTO> listAllByProject(ProjectDTO project) {
        List<Task> list = taskRepository.findAllByProject(projectMapper.convertToEntity(project));
        return list.stream().map(taskMapper::convertToDTO).collect(Collectors.toList());
    }
}
