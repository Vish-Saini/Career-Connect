package com.PlacementPortal.CareerConnect.Service;

import com.PlacementPortal.CareerConnect.Entity.Job;
import jakarta.persistence.criteria.Predicate;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.PlacementPortal.CareerConnect.Repository.JobRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class JobService {
    @Autowired
    private JobRepository jobRepository;

    public List<Job> findFilteredJobs(String title, String location, String companyName) {
        // We use a Specification to build a dynamic query
        return jobRepository.findAll((root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            // If a title is provided, add a "like" predicate to our list
            if (title != null && !title.isEmpty()) {
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("title")), "%" + title.toLowerCase() + "%"));
            }

            // If a location is provided, add a "like" predicate
            if (location != null && !location.isEmpty()) {
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("location")), "%" + location.toLowerCase() + "%"));
            }

            // If a companyName is provided, add a "like" predicate
            if (companyName != null && !companyName.isEmpty()) {
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("companyName")), "%" + companyName.toLowerCase() + "%"));
            }

            // Combine all predicates with "AND"
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        });
    }

    //method to post a job
    public Job createJob(Job job){
        job.setPostedDate(LocalDate.now()); // Set the current date before saving
        return jobRepository.save(job);
    }

    public Job updateJob(Long jobId, Job jobDetails) {
        // Find the existing job by its ID, or throw an exception if not found
        Job existingJob = jobRepository.findById(jobId)
                .orElseThrow(() -> new RuntimeException("Job not found with ID: " + jobId));

        // Update the fields of the existing job with the new details
        existingJob.setTitle(jobDetails.getTitle());
        existingJob.setDescription(jobDetails.getDescription());
        existingJob.setCompanyName(jobDetails.getCompanyName());
        existingJob.setLocation(jobDetails.getLocation());
        existingJob.setApplicationDeadline(jobDetails.getApplicationDeadline());

        // Save the updated job back to the database
        return jobRepository.save(existingJob);
    }

    public void deleteJob(Long jobId) {
        // Check if the job exists before deleting, or throw an exception
        Job job = jobRepository.findById(jobId)
                .orElseThrow(() -> new RuntimeException("Job not found with ID: " + jobId));

        jobRepository.delete(job);
    }

}
