/*
 * Copyright 2020-2021 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.cdapps.sprintbootassessment.controllers;

import com.cdapps.sprintbootassessment.dto.AuthenticationRequestDto;
import com.cdapps.sprintbootassessment.dto.AuthenticationResponseDto;
import com.cdapps.sprintbootassessment.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * A controller for the token resource.
 *
 * @author Josh Cummings
 */
@RestController
public class UserController {
	final UserService userService;
	public UserController(UserService userService) {
		this.userService = userService;
	}

	@PostMapping("/signIn")
	public AuthenticationResponseDto signIn(Authentication authentication) {
		return userService.generateToken(authentication);
	}

	@PostMapping("/signUp")
	public void signUp(@RequestBody AuthenticationRequestDto dto) {
		userService.saveUser(dto);
	}

}