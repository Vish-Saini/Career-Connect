package com.PlacementPortal.CareerConnect.Repository;

import com.PlacementPortal.CareerConnect.Entity.Application;
import com.PlacementPortal.CareerConnect.Entity.Job;
import com.PlacementPortal.CareerConnect.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface ApplicationRepository extends JpaRepository<Application, Long> {

    // Custom method to check if a user has already applied for a specific job
    Optional<Application> findByUserAndJob(User user, Job job);
    // New method to find all applications for a specific job
    List<Application> findAllByJobId(Long jobId);
    List<Application> findAllByUserId(Long userId);
    @Transactional
        // Add this to ensure the delete operation is handled in a transaction
    void deleteAllByUserId(Long userId);
}
