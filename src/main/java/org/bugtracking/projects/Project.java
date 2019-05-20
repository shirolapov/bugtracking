package org.bugtracking.projects;

import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Id;
import javax.persistence.Column;

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
}