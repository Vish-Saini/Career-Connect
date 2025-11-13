package com.PlacementPortal.CareerConnect.Service;

import com.PlacementPortal.CareerConnect.Dto.ApplicationResponseDTO;
import com.PlacementPortal.CareerConnect.Entity.Application;
import com.PlacementPortal.CareerConnect.Entity.Job;
import com.PlacementPortal.CareerConnect.Entity.User;
import com.PlacementPortal.CareerConnect.Repository.ApplicationRepository;
import com.PlacementPortal.CareerConnect.Repository.JobRepository;
import com.PlacementPortal.CareerConnect.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ApplicationService {

    @Autowired
    private ApplicationRepository applicationRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JobRepository jobRepository;

    public Application applyForJob(String userEmail, Long jobId) {
        // Find the user and job
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("User not found"));
        Job job = jobRepository.findById(jobId)
                .orElseThrow(() -> new RuntimeException("Job not found"));

        // Check if the user has already applied for this job
        Optional<Application> existingApplication = applicationRepository.findByUserAndJob(user, job);
        if (existingApplication.isPresent()) {
            throw new IllegalStateException("You have already applied for this job.");
        }

        // Create and save the new application
        Application newApplication = new Application();
        newApplication.setUser(user);
        newApplication.setJob(job);
        newApplication.setApplicationDate(LocalDate.now());
        newApplication.setStatus("APPLIED");

        return applicationRepository.save(newApplication);
    }

    public List<ApplicationResponseDTO> getApplicationsForJob(Long jobId) {
        // First, check if the job exists
        jobRepository.findById(jobId).orElseThrow(() -> new RuntimeException("Job not found"));

        // Find all applications for the job
        List<Application> applications = applicationRepository.findAllByJobId(jobId);

        // Convert the list of Application entities to a list of DTOs
        return applications.stream()
                .map(application -> new ApplicationResponseDTO(
                        application.getId(),
                        application.getApplicationDate(),
                        application.getStatus(),
                        application.getUser().getName(),
                        application.getUser().getEmail()))
                .collect(Collectors.toList());
    }

    public Application updateApplicationStatus(Long applicationId, String newStatus) {
        // Find the application by its ID, or throw an exception if not found
        Application application = applicationRepository.findById(applicationId)
                .orElseThrow(() -> new RuntimeException("Application not found with ID: " + applicationId));

        // Update the status
        application.setStatus(newStatus);

        // Save the updated application back to the database
        return applicationRepository.save(application);
    }

    public List<ApplicationResponseDTO> getApplicationsForStudent(String userEmail) {
        // Find the user by their email
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Find all applications by that user's ID
        List<Application> applications = applicationRepository.findAllByUserId(user.getId());

        // Reuse the DTO to map the data
        return applications.stream()
                .map(application -> new ApplicationResponseDTO(
                        application.getId(),
                        application.getApplicationDate(),
                        application.getStatus(),
                        application.getJob().getCompanyName(), // You might want company name
                        application.getJob().getTitle()))      // and job title here
                .collect(Collectors.toList());
    }
}
