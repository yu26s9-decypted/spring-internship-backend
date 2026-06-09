package com.pluralsight.demo.internship.model;

import java.time.LocalDateTime;

import jakarta.persistence.*;

@Entity
@Table(name = "candidates")
public class Candidate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    
    private String email;
    
    private String fieldOfStudy;

    private LocalDateTime registeredAt;

    private boolean isVisible;

    // Constructors
    public Candidate() {
    }

    public Candidate(String name, String email, String fieldOfStudy) {
        this.name = name;
        this.email = email;
        this.fieldOfStudy = fieldOfStudy;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFieldOfStudy() {
        return fieldOfStudy;
    }

    public void setFieldOfStudy(String fieldOfStudy) {
        this.fieldOfStudy = fieldOfStudy;
    }

    public LocalDateTime getRegisteredAt(){
        return registeredAt;
    }

    public void setRegisteredAt(LocalDateTime registeredAt){
        this.registeredAt = registeredAt;
    }

    public boolean getVisible(){
        return isVisible;
    }

    public void setVisible(boolean isVisible){
        this.isVisible = isVisible;
    }
}
