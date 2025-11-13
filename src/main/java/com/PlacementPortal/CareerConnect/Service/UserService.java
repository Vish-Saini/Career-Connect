package com.PlacementPortal.CareerConnect.Service;

import com.PlacementPortal.CareerConnect.Dto.UserResponseDTO;
import com.PlacementPortal.CareerConnect.Entity.Role;
import com.PlacementPortal.CareerConnect.Entity.User;
import com.PlacementPortal.CareerConnect.Repository.ApplicationRepository;
import com.PlacementPortal.CareerConnect.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ApplicationRepository applicationRepository;

    /**
     * Finds all users with the ROLE_STUDENT and converts them to DTOs.
     * @return A list of UserResponseDTO objects.
     */
    public List<UserResponseDTO> findAllStudents() {
        return userRepository.findAll()
                .stream()
                .filter(user -> user.getRole() == Role.ROLE_STUDENT) // Filter to include only students
                .map(this::convertToDTO) // Convert each student to a DTO
                .collect(Collectors.toList());
    }

    public void deleteStudent(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + userId));

        if (user.getRole() != Role.ROLE_STUDENT) {
            throw new IllegalStateException("Cannot delete a user who is not a student.");
        }

        // 1. First, delete all applications associated with this user.
        applicationRepository.deleteAllByUserId(userId);

        // 2. Then, delete the user themselves.
        userRepository.delete(user);
    }

    /**
     * Helper method to convert a User entity to a UserResponseDTO.
     * @param user The User entity.
     * @return The corresponding DTO.
     */
    private UserResponseDTO convertToDTO(User user) {
        UserResponseDTO dto = new UserResponseDTO();
        dto.setId(user.getId());
        dto.setName(user.getName());
        dto.setEmail(user.getEmail());
        dto.setRole(user.getRole());
        return dto;
    }
}
