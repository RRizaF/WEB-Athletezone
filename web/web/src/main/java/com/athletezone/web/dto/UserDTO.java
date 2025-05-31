package com.athletezone.web.dto;

import lombok.*;

import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDTO {
    @Size(min = 3, max = 20, message = "Username must be between 3 and 20 characters")
    private String username;
    private String email;
    @Size(min = 8, message = "Password must be at least 8 characters long")
    private String password;
    @Size(min = 3, max = 50, message = "Full name must be between 3 and 50 characters")
    private String fullName;
    private String profileImage; // Bisa berupa URL atau nama file. Validasi bisa ditambahkan sesuai kebutuhan.
}

