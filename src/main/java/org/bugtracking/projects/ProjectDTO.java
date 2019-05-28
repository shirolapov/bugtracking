package org.bugtracking.projects;

import org.bugtracking.tasks.Task;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ProjectDTO {
    private Integer id;
    private String name;
    private String description;
    private Date dateOfCreation;
    private Date dateOfLastChange;

    public ProjectDTO(Project project) {
        this.id = project.getId();
        this.name = project.getName();
        this.description = project.getDescription();
        this.dateOfCreation = project.getDateOfCreation();
        this.dateOfLastChange = project.getDateOfLastChange();
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Date getDateOfCreation() {
        return dateOfCreation;
    }

    public Date getDateOfLastChange() {
        return dateOfLastChange;
    }
}
