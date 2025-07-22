package com.example.demo.model;

public class Job {

    private String id;
    private String title;
    private String company;
    private String location;
    private String url;
    private String description;

    // ✅ ADD this new field
    private String source;

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

    // ✅ ADD Getter & Setter for 'source'
    public String getSource() { return source; }
    public void setSource(String source) { this.source = source; }
}
