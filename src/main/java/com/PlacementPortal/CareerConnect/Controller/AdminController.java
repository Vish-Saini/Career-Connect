package com.PlacementPortal.CareerConnect.Controller;

import com.PlacementPortal.CareerConnect.Dto.ApplicationResponseDTO;
import com.PlacementPortal.CareerConnect.Dto.UpdateApplicationStatusDTO;
import com.PlacementPortal.CareerConnect.Dto.UserResponseDTO;
import com.PlacementPortal.CareerConnect.Entity.Application;
import com.PlacementPortal.CareerConnect.Entity.Job;
import com.PlacementPortal.CareerConnect.Service.ApplicationService;
import com.PlacementPortal.CareerConnect.Service.JobService;
import com.PlacementPortal.CareerConnect.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired
    private ApplicationService applicationService;

    @Autowired
    private JobService jobService; // Inject JobService

    @Autowired
    private UserService userService; // Inject UserService

    @GetMapping("/jobs/{jobId}/applications")
    public ResponseEntity<List<ApplicationResponseDTO>> getApplicationsForJob(@PathVariable Long jobId) {
        List<ApplicationResponseDTO> applications = applicationService.getApplicationsForJob(jobId);
        return ResponseEntity.ok(applications);
    }

    @PutMapping("/applications/{applicationId}/status")
    public ResponseEntity<Application> updateApplicationStatus(
            @PathVariable Long applicationId,
            @RequestBody UpdateApplicationStatusDTO statusDTO,
            Authentication authentication) { // <-- Add this parameter

        System.out.println("ADMIN ACTION: User '" + authentication.getName() + "' with roles " + authentication.getAuthorities() + " is updating status.");

        Application updatedApplication = applicationService.updateApplicationStatus(applicationId, statusDTO.getStatus());
        return ResponseEntity.ok(updatedApplication);
    }

    @PostMapping("/jobs") // Moved from JobController
    public ResponseEntity<Job> createJob(@RequestBody Job job) {
        Job createdJob = jobService.createJob(job);
        return new ResponseEntity<>(createdJob, HttpStatus.CREATED);
    }

    @PutMapping("/jobs/{jobId}") // New endpoint for updating a job
    public ResponseEntity<Job> updateJob(@PathVariable Long jobId, @RequestBody Job jobDetails,Authentication authentication) {

        System.out.println("DEBUG: User '" + authentication.getName() + "' with roles " + authentication.getAuthorities() + " is trying to update a job.");

        Job updatedJob = jobService.updateJob(jobId, jobDetails);
        return ResponseEntity.ok(updatedJob);
    }

    @DeleteMapping("/jobs/{jobId}") // New endpoint for deleting a job
    public ResponseEntity<String> deleteJob(@PathVariable Long jobId) {
        jobService.deleteJob(jobId);
        return ResponseEntity.ok("Job deleted successfully with ID: " + jobId);
    }

    @GetMapping("/users")
    public ResponseEntity<List<UserResponseDTO>> getAllStudents() {
        List<UserResponseDTO> students = userService.findAllStudents();
        return ResponseEntity.ok(students);
    }

    @DeleteMapping("/users/{userId}")
    public ResponseEntity<String> deleteStudent(@PathVariable Long userId) {
        try {
            userService.deleteStudent(userId);
            return ResponseEntity.ok("Student with ID " + userId + " deleted successfully.");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
