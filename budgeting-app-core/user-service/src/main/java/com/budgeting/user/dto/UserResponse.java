package com.budgeting.user.dto;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Builder;

import java.time.Instant;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserResponse {

    private String id;

    private String email;
    private String firstName;
    private String lastName;
    private Set<String> roles;
    private Instant createdAt;

}
