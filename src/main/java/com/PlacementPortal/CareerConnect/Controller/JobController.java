package com.PlacementPortal.CareerConnect.Controller;


import com.PlacementPortal.CareerConnect.Entity.Job;
import com.PlacementPortal.CareerConnect.Service.JobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/jobs")
public class JobController {
    @Autowired
    private JobService jobService;

    @GetMapping
    public List<Job> getAllJobs(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String location,
            @RequestParam(required = false) String companyName) {

        return jobService.findFilteredJobs(title, location, companyName);
    }

}
