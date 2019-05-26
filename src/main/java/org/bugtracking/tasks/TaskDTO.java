package org.bugtracking.tasks;

import org.bugtracking.projects.Project;
import org.bugtracking.projects.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import javax.persistence.EntityManager;

import javax.persistence.Entity;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

public class TaskDTO {
    private Long id;
    private String name;
    private String description;
    private Integer priority;
    private Date dateOfCreation;
    private Date dateOfLastChange;
    private Task.Status status;
    private Project project;

    public TaskDTO(Task task) {
        this.id = task.getId();
        this.name = task.getName();
        this.description = task.getDescription();
        this.priority = task.getPriority();
        this.dateOfCreation = task.getDateOfCreation();
        this.dateOfLastChange = task.getDateOfLastChange();
        this.status = task.getStatus();
        this.project = task.getProject();
    }

    public Long getId() { return this.id; };
    public String getName() { return this.name; };
    public String getDescription() { return this.description; };
    public Integer getPriority() { return this.priority; };
    public Date getDateOfCreation() { return this.dateOfCreation; };
    public Date getDateOfLastChange() { return this.dateOfLastChange; };
    public Task.Status getStatus() { return this.status; };
    public Project getProject() { return this.project; };
}
