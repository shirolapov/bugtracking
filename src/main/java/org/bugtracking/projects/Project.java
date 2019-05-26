package org.bugtracking.projects;

import org.bugtracking.tasks.Task;

import java.util.Date;
import javax.persistence.*;
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

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "project")
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

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }

    //Getters
    public List<Task> getTasks() {
        return tasks;
    }

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

    public void addTask(Task task) {
        this.tasks.add(task);
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