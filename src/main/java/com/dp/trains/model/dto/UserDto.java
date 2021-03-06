package com.dp.trains.model.dto;

import com.dp.trains.model.entities.user.UserAccess;
import com.google.common.collect.Sets;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

    private String username;
    private String password;
    private String passwordConfirm;

    @Builder.Default
    private Set<Authority> authorities = Sets.newHashSet();

    @Builder.Default
    private Set<UserAccess> userAccesses = Sets.newHashSet();
}
