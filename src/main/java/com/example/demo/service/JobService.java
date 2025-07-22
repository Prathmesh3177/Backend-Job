package com.example.demo.service;

import com.example.demo.model.Job;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@Service
public class JobService {

    @Value("${adzuna.app.id}")
    private String appId;

    @Value("${adzuna.api.key}")
    private String apiKey;

    public List<Job> fetchJobsFromAPI() {
        List<Job> jobs = new ArrayList<>();
        try {
            String apiUrl = "https://api.adzuna.com/v1/api/jobs/in/search/1?app_id=" + appId
                    + "&app_key=" + apiKey
                    + "&results_per_page=20&what=entry%20level%20OR%20fresher&content-type=application/json";

            URL url = new URL(apiUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            Scanner scanner = new Scanner(conn.getInputStream());
            StringBuilder jsonStr = new StringBuilder();
            while (scanner.hasNext()) {
                jsonStr.append(scanner.nextLine());
            }
            scanner.close();

            JSONObject jsonObject = new JSONObject(jsonStr.toString());
            JSONArray results = jsonObject.getJSONArray("results");

            for (int i = 0; i < results.length(); i++) {
                JSONObject jobJson = results.getJSONObject(i);

                Job job = new Job();
                job.setId(jobJson.optString("id"));
                job.setTitle(jobJson.optString("title"));
                job.setCompany(jobJson.getJSONObject("company").optString("display_name"));
                job.setLocation(jobJson.getJSONObject("location").optString("display_name"));
                job.setUrl(jobJson.optString("redirect_url"));
                job.setDescription(jobJson.optString("description"));

                jobs.add(job);
            }

        } catch (Exception e) {
            System.out.println("❌ Error fetching jobs: " + e.getMessage());
        }

        return jobs;
    }
}
