package com.example.demo.service;


import com.example.demo.model.Job;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class RemotiveJobService {

    public List<Job> fetchJobs() {
        List<Job> jobs = new ArrayList<>();
        try {
            String url = "https://remotive.io/api/remote-jobs";
            RestTemplate restTemplate = new RestTemplate();
            JSONObject response = new JSONObject(restTemplate.getForObject(url, String.class));
            JSONArray jobsArray = response.getJSONArray("jobs");

            for (int i = 0; i < jobsArray.length(); i++) {
                JSONObject jobJson = jobsArray.getJSONObject(i);

                Job job = new Job();
                job.setTitle(jobJson.getString("title"));
                job.setCompany(jobJson.getString("company_name"));
                job.setLocation(jobJson.getString("candidate_required_location"));
                job.setUrl(jobJson.getString("url"));
                job.setDescription(jobJson.getString("description"));
                job.setSource("Remotive");

                jobs.add(job);
            }
        } catch (Exception e) {
            System.out.println("❌ Remotive error: " + e.getMessage());
        }
        return jobs;
    }
}

