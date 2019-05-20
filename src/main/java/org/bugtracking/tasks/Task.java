package org.bugtracking.tasks;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Task {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Integer id;
    @Column(nullable=false)
    private String name;
    private String description;
    private String link;
    private Integer priority;
    private Date dateOfCreation;
    private Date dateOfLastChange;
    private Status status;

    @PrePersist
    protected void onCreate() {
        dateOfCreation = new Date();
        dateOfLastChange = new Date();
        status = Status.NEW;
    }

    @PreUpdate
    protected void onUpdate() {
        dateOfLastChange = new Date();
    }

    enum Status {
        NEW,
        INPROGRESS,
        CLOSE
    }

    public void setName(String newName) {
        this.name = newName;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    private class TaskBlockException extends RuntimeException {
        public String toString() {
            return "Task is blocked.";
        }
    }

    public Integer getId() { return this.id; };

    public String getName() {
        return this.name;
    }

    public String getDescription() {
        return this.description;
    }

    public String getLink() { return this.link; };

    public Integer getPriority() { return this.priority; };

    public Date getDateOfCreation() {
        return this.dateOfCreation;
    }

    public Date getDateOfLastChange() {
        return this.dateOfLastChange;
    }

    public Status getStatus() { return this.status; };

    public Boolean equalsAndChangeTask(Task task) {
        if (!task.getName().equals(this.getName())) {
            return true;
        } else if (!task.getDescription().equals(this.getDescription())) {
            return true;
        } else if (!task.getLink().equals(this.getLink())) {
            return true;
        } else if (!task.getPriority().equals(this.getPriority())) {
            return true;
        } else if (!task.getStatus().equals(this.getStatus())) {
            return true;
        } else {
            return false;
        }
    }
}
