package org.bugtracking.tasks;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

public class TaskDTO {
    private Integer id;
    private String name;
    private String description;
    private String link;
    private Integer priority;
    private Date dateOfCreation;
    private Date dateOfLastChange;
    private Task.Status status;

    public TaskDTO(Task task) {
        this.id = task.getId();
        this.name = task.getName();
        this.description = task.getDescription();
        this.link = task.getLink();
        this.priority = task.getPriority();
        this.dateOfCreation = task.getDateOfCreation();
        this.dateOfLastChange = task.getDateOfLastChange();
        this.status = task.getStatus();
    }

    public Integer getId() { return this.id; };
    public String getName() { return this.name; };
    public String getDescription() { return this.description; };
    public String getLink() { return this.link; };
    public Integer getPriority() { return this.priority; };
    public Date getDateOfCreation() { return this.dateOfCreation; };
    public Date getDateOfLastChange() { return this.dateOfLastChange; };
    public Task.Status getStatus() { return this.status; };
}
