package org.bugtracking.tasks;


import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Id;
import javax.persistence.Column;
import javax.persistence.ManyToOne;
import javax.persistence.JoinColumn;
import org.bugtracking.projects.Project;
import javax.persistence.FetchType;

@Entity
public class Task {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Integer id;
    @Column(nullable=false)
    private String name;
    private String description;
    private Integer priority;
    private Date dateOfCreation;
    private Date dateOfLastChange;
    private Status status;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="PROJECT_ID")
    private Project project;

    @PrePersist
    protected void onCreate() {
        dateOfCreation = new Date();
        dateOfLastChange = new Date();
        status = Status.NEW;
    }

    @PreUpdate
    protected void onUpdate() {
        this.dateOfLastChange = new Date();
    }

    public enum Status {
        NEW,
        INPROGRESS,
        CLOSE
    }

    //Setters

    public void setName(String newName) { this.name = newName; };

    public void setDescription(String description) { this.description = description; };

    public void setPriority(Integer priority) { this.priority = priority; };

    public void setStatus(Status status) { this.status = status; };

    public void setProject(Project project) {
        this.project = project;
        if (!project.getTasks().contains(this)) {
            project.getTasks().add(this);
        }
    }

    //Getters

    public Integer getId() { return this.id; };

    public String getName() {
        return this.name;
    };

    public String getDescription() {
        return this.description;
    };

    public Integer getPriority() { return this.priority; };

    public Date getDateOfCreation() {
        return this.dateOfCreation;
    };

    public Date getDateOfLastChange() {
        return this.dateOfLastChange;
    };

    public Status getStatus() { return this.status; };

    public Project getProject() { return this.project; };

    //Methods

    public Boolean equalsAndChange(Task task) {
        Boolean wasChanged = false;

        //Update Name
        if (task.getName() != null) {
            if (!task.getName().equals(this.getName())) {
                this.setName(task.getName());
                wasChanged = true;
            }
        }

        //Update Description
        if (task.getDescription() != null) {
            if (!task.getDescription().equals(this.getDescription())) {
                this.setDescription(task.getDescription());
                wasChanged = true;
            }
        }

        //Update Priority
        if (task.getPriority() != null) {
            if (!task.getPriority().equals(this.getPriority())) {
                this.setPriority(task.getPriority());
                wasChanged = true;
            }
        }

        //Update Status
        if (task.getStatus() != null) {
            if (!task.getStatus().equals(this.getStatus())) {
                this.setStatus(task.getStatus());
                wasChanged = true;
            }
        }

        if (wasChanged) {
            this.dateOfLastChange = new Date();
        }

        return wasChanged;
    }

    //Custom exceptions

    private class TaskBlockException extends RuntimeException {
        public String toString() {
            return "Task is blocked.";
        }
    }
}
