package org.bugtracking;

import java.util.Date;

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
