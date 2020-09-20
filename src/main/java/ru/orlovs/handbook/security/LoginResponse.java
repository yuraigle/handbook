package ru.orlovs.handbook.security;

import lombok.Data;

@Data
public class LoginResponse {

    private final String token;

    private final AccountDetails user;
}
