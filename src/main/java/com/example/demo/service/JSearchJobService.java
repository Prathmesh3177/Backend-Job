package com.example.demo.service;

import com.example.demo.model.Job;
import org.asynchttpclient.*;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
public class JSearchJobService {

    @Value("${rapidapi.key}")
    private String apiKey;

    private static final String API_HOST = "jsearch.p.rapidapi.com";

    public CompletableFuture<List<Job>> fetchJobs() {
        List<Job> jobList = new ArrayList<>();
        AsyncHttpClient client = Dsl.asyncHttpClient();

        return client.prepare("GET",
                "https://jsearch.p.rapidapi.com/search?query=entry%20level%20developer&page=1&num_pages=1&country=us")
            .setHeader("x-rapidapi-key", apiKey)
            .setHeader("x-rapidapi-host", API_HOST)
            .execute()
            .toCompletableFuture()
            .thenApply(response -> {
                JSONObject json = new JSONObject(response.getResponseBody());
                JSONArray data = json.getJSONArray("data");

                for (int i = 0; i < data.length(); i++) {
                    JSONObject obj = data.getJSONObject(i);
                    Job job = new Job();
                    job.setTitle(obj.getString("job_title"));
                    job.setCompany(obj.optString("employer_name", ""));
                    job.setLocation(obj.optString("job_city", "") + ", " + obj.optString("job_country", ""));
                    job.setUrl(obj.optString("job_apply_link", ""));
                    job.setDescription(obj.optString("job_description", ""));
                    job.setSource("JSearch");
                    jobList.add(job);
                }

                try {
                    client.close();
                } catch (IOException e) {
                    System.out.println("❌ Error closing AsyncHttpClient: " + e.getMessage());
                }

                return jobList;
            });
    }
}
