package com.example.demo.service;

import com.example.demo.model.Job;
import com.example.demo.repository.JobRepository;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;

import java.util.Scanner;

@Service
public class JobService {

    @Autowired
    private JobRepository jobRepository;

    @Value("${adzuna.app.id}")
    private String appId;

    @Value("${adzuna.api.key}")
    private String apiKey;


    public List<Job> fetchAndSaveJobsFromAPI() {
        List<Job> resultList = new ArrayList<>();
        try {
            String apiUrl = "https://api.adzuna.com/v1/api/jobs/in/search/1?app_id=" + appId
                    + "&app_key=" + apiKey
                    + "&results_per_page=20&what=entry%20level%20OR%20fresher&content-type=application/json";

            System.out.println("🌐 Connecting to Adzuna API...");
            URL url = new URL(apiUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            Scanner scanner = new Scanner(conn.getInputStream());
            StringBuilder jsonStr = new StringBuilder();
            while (scanner.hasNext()) {
                jsonStr.append(scanner.nextLine());
            }
            scanner.close();

            System.out.println("✅ Fetched API response.");
            JSONObject jsonObject = new JSONObject(jsonStr.toString());
            JSONArray results = jsonObject.getJSONArray("results");

            for (int i = 0; i < results.length(); i++) {
                JSONObject jobJson = results.getJSONObject(i);

                Job job = new Job();
                job.setId(jobJson.getString("id"));
                job.setTitle(jobJson.getString("title"));
                job.setCompany(jobJson.getJSONObject("company").getString("display_name"));
                job.setLocation(jobJson.getJSONObject("location").getString("display_name"));
                job.setUrl(jobJson.getString("redirect_url"));
                job.setDescription(jobJson.getString("description"));

                try {
                    if (!jobRepository.existsById(job.getId())) {
                        jobRepository.save(job);
                        resultList.add(job);
                        System.out.println("💾 Saved: " + job.getTitle() + " @ " + job.getCompany());
                    }
                } catch (Exception e) {
                    System.out.println("❌ Error saving job: " + e.getMessage());
                }
            }

            System.out.println("✅ Total new jobs saved: " + resultList.size());

        } catch (Exception e) {
            System.out.println("❌ API Fetch Error: " + e.getMessage());
            e.printStackTrace();
        }

        return resultList;
    }

    public List<Job> getAllJobsFromDB() {
        List<Job> jobs = jobRepository.findAll();
        System.out.println("📦 Fetched " + jobs.size() + " jobs from DB.");
        return jobs;
    }
}
