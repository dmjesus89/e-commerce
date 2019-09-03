package com.dm.config.controller;

import java.security.Principal;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;

@RestController
@RequestMapping("/")
@Api(value = "auth", description = "Tudo sobre auth", produces = "application/json")
public class AuthController {

	@RequestMapping("/user")
	public Principal getCurrentLoggedInUser(Principal user) {
		return user;
	}
}