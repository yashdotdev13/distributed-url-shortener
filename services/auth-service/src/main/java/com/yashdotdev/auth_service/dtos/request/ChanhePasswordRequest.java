package com.yashdotdev.auth_service.dtos.request;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChanhePasswordRequest {

    @NotBlank
    private String currentPassword;


    @NotBlank
    @Size(min=  8, max = 100)
    private String newPassword;
}
