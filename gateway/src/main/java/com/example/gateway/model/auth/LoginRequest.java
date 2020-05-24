package com.example.gateway.model.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class LoginRequest {

	@NotBlank
	private String usernameOrEmail;

	@NotBlank
	private String password;

}
