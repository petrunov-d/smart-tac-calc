package com.dp.trains.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PasswordChangeDto {

    private String username;
    private String oldPassword;
    private String newPassword;
    private String newPasswordConfirm;
}
