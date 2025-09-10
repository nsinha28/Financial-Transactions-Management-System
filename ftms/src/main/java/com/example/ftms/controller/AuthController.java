package com.example.ftms.controller;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.ftms.model.UserEntity;
import com.example.ftms.repository.UserRepository;
import com.example.ftms.security.JWTUtil;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

	
	 private final UserRepository userRepository;
	    private final PasswordEncoder passwordEncoder;
	    private final JWTUtil jwtUtil;

	    public AuthController(UserRepository userRepository,
	                          PasswordEncoder passwordEncoder,
	                          JWTUtil jwtUtil) {
	        this.userRepository = userRepository;
	        this.passwordEncoder = passwordEncoder;
	        this.jwtUtil = jwtUtil;
	    }
	    
	    @PostMapping("/register")
	    public String register(@RequestBody UserEntity user) {
	    	user.setPassword(passwordEncoder.encode(user.getPassword()));
	    	userRepository.save(user);
	    	return "User registered successfully";
	    }
	    
	    @PostMapping("/login")
	    public String login(@RequestBody UserEntity user) {
	    	UserEntity dbUser= userRepository.findByUsername(user.getUsername());
	    	if(dbUser!=null && passwordEncoder.matches(user.getPassword(), dbUser.getPassword())) {
	    		return jwtUtil.generateToken(dbUser.getUsername());
	    	}
	    	throw new RuntimeException("Invalid Username or Password");
	    }
}
