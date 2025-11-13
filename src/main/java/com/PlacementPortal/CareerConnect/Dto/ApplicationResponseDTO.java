package com.PlacementPortal.CareerConnect.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.time.LocalDate;

@Data
@AllArgsConstructor
public class ApplicationResponseDTO {

    private Long applicationId;
    private LocalDate applicationDate;
    private String status;
    private String studentName;
    private String studentEmail;
}
