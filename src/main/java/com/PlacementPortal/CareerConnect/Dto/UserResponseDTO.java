package com.PlacementPortal.CareerConnect.Dto;


import com.PlacementPortal.CareerConnect.Entity.Role;
import lombok.Data;

@Data
public class UserResponseDTO {
    private Long id;
    private String name;
    private String email;
    private Role role;
}
