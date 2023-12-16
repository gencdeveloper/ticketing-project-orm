package com.cydeo.entity;

import com.cydeo.dto.UserDTO;
import com.cydeo.enums.Status;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Where;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Entity
@Table(name="projects")
@NoArgsConstructor
@Getter
@Setter
@Where(clause = "is_deleted=false")
public class Project extends BaseEntity{
    @Column(unique = true)
    private String projectCode;
    private String projectName;

    @ManyToOne(fetch = FetchType.LAZY) //ONE MANAGER CAN BE ASSIGNED TO MANY PROJECT
    @JoinColumn(name = "manager_id") //changing column name instead of assignedManager
    private User assignedManager;

    @Column(columnDefinition = "DATE")
    private LocalDate startDate;

    @Column(columnDefinition = "DATE")
    private LocalDate endDate;

    private String projectDetail;
    @Enumerated(EnumType.STRING)
    private Status projectStatus;

}
