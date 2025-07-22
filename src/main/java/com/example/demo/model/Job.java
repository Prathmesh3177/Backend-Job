package com.example.demo.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "jobs")
public class Job {

    @Id
    private String id;
    private String title;
    private String company;
    private String location;
    private String url;
    private String description;

    // Getters and Setters
    public String getId() { return id; }

    public void setId(String id) { this.id = id; }

    public String getTitle() { return title; }

    public void setTitle(String title) { this.title = title; }

    public String getCompany() { return company; }

    public void setCompany(String company) { this.company = company; }

    public String getLocation() { return location; }

    public void setLocation(String location) { this.location = location; }

    public String getUrl() { return url; }

    public void setUrl(String url) { this.url = url; }

    public String getDescription() { return description; }

    public void setDescription(String description) { this.description = description; }
}
