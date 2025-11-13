package com.PlacementPortal.CareerConnect.Repository;

import com.PlacementPortal.CareerConnect.Entity.Job;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface JobRepository extends JpaRepository<Job, Long>, JpaSpecificationExecutor<Job> {
    // Spring Data JPA automatically provides all the necessary methods (save, findById, findAll, etc.)
}