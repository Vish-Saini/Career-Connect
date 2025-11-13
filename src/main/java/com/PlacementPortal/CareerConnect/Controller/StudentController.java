package com.PlacementPortal.CareerConnect.Controller;

import com.PlacementPortal.CareerConnect.Dto.ApplicationResponseDTO;
import com.PlacementPortal.CareerConnect.Entity.User;
import com.PlacementPortal.CareerConnect.Service.FileStorageService;
import com.PlacementPortal.CareerConnect.Entity.Application;
import com.PlacementPortal.CareerConnect.Repository.UserRepository;
import com.PlacementPortal.CareerConnect.Service.ApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/student")

public class StudentController {

    @Autowired
    private ApplicationService applicationService;

    @Autowired
    private FileStorageService fileStorageService;

    @Autowired
    private UserRepository userRepository;

    //... existing methods (applyForJob, getMyApplications)

    @PostMapping("/resume")
    public ResponseEntity<String> uploadResume(@RequestParam("file") MultipartFile file, Principal principal) {
        User user = userRepository.findByEmail(principal.getName())
                .orElseThrow(() -> new RuntimeException("User not found"));

        String fileName = fileStorageService.storeFile(file, user.getId());

        user.setResumeFilename(fileName);
        userRepository.save(user);

        return ResponseEntity.ok("Resume uploaded successfully: " + fileName);
    }

    @PostMapping("/jobs/{jobId}/apply")
    public ResponseEntity<?> applyForJob(@PathVariable Long jobId, Principal principal) {
        try {
            // principal.getName() will return the email of the currently logged-in user
            Application application = applicationService.applyForJob(principal.getName(), jobId);
            return new ResponseEntity<>(application, HttpStatus.CREATED);
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/applications")
    public ResponseEntity<List<ApplicationResponseDTO>> getMyApplications(Principal principal) {
        // principal.getName() returns the email of the logged-in student
        List<ApplicationResponseDTO> applications = applicationService.getApplicationsForStudent(principal.getName());
        return ResponseEntity.ok(applications);
    }
}
