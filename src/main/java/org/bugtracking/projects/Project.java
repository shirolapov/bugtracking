package org.bugtracking.projects;

import org.bugtracking.tasks.Task;

import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Id;
import javax.persistence.Column;
import javax.persistence.OneToMany;
import javax.persistence.JoinColumn;
import java.util.List;

@Entity
public class Project {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Integer id;
    @Column(nullable=false)
    private String name;
    private String description;
    private Date dateOfCreation;
    private Date dateOfLastChange;

    @OneToMany
    @JoinColumn(name="PROJECT_ID", referencedColumnName="ID")
    private List<Task> tasks;

    @PrePersist
    protected void onCreate() {
        dateOfCreation = new Date();
        dateOfLastChange = new Date();
    }

    @PreUpdate
    protected void onUpdate() {
        dateOfLastChange = new Date();
    }

    //Setters

    public void setName(String newName) {
        this.name = newName;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    //Getters

    public String getName() {
        return this.name;
    }

    public String getDescription() {
        return this.description;
    }

    public Date getDateOfCreation() {
        return this.dateOfCreation;
    }

    public Date getDateOfLastChange() {
        return this.dateOfLastChange;
    }

    public Integer getId() { return this.id; }

    public List<Task> getTasks() {
        return this.tasks;
    }

    public void addTask(Task task) {
        this.tasks.add(task);
        if (task.getProject() != this) {
            task.setProject(this);
        }
    }

    //Methods

    public Boolean equalsAndChange(Project project) {
        Boolean wasChanged = false;

        //Update Name
        if (project.getName() != null) {
            if (!project.getName().equals(this.getName())) {
                this.setName(project.getName());
                wasChanged = true;
            }
        }

        //Update Description
        if (project.getDescription() != null) {
            if (!project.getDescription().equals(this.getDescription())) {
                this.setDescription(project.getDescription());
                wasChanged = true;
            }
        }

        if (wasChanged) {
            this.dateOfLastChange = new Date();
        }

        return wasChanged;
    }
}