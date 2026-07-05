package com.app.ecom.Model.Dto;

import com.app.ecom.Enum.UserRole;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class UserResponseDto {

    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private UserRole role;
    private AddressDto address;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
