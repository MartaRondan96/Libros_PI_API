package com.example.demo.controller;

import java.sql.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import com.example.demo.entity.User;
import com.example.demo.serviceImpl.UserService;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@RestController
public class UserController {

	@Autowired
	@Qualifier("userService")
	private UserService userService;

	@Autowired
	private AuthenticationManager authenticationManager;

	@PostMapping("/login")
	public com.example.demo.entity.User login(@RequestParam("user") String username,
			@RequestParam("password") String pwd) {
		Authentication authentication = authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(username, pwd));
		SecurityContextHolder.getContext().setAuthentication(authentication);
		com.example.demo.entity.User user = userService.findUser(username);
		String token = getJWTToken(username);
		user.setToken(token);
		return user;

	}

	@PostMapping("/register")
	private User saveUser(@RequestBody User user) {
		return userService.register(user);
	}

	private String getJWTToken(String username) {
		String secretKey = "mySecretKey";
		com.example.demo.entity.User u=userService.findUser(username);
		List<GrantedAuthority> grantedAuthorities = AuthorityUtils.commaSeparatedStringToAuthorityList(u.getRole());

		String token = Jwts.builder().setId("softtekJWT").setSubject(username)
				.claim("authorities",
						grantedAuthorities.stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + 600000))
				.signWith(SignatureAlgorithm.HS512, secretKey.getBytes()).compact();

		return "Bearer " + token;
	}

	@GetMapping("/all/{id}")
	private ResponseEntity<?> getUserById(@PathVariable int id) {
		boolean exist = userService.findUserId(id)!=null;
		System.out.println(exist);
		if(exist) {
			User user=userService.findUserId(id);
			return ResponseEntity.ok(user);
		}
		else
			return ResponseEntity.noContent().build();
	}
	@GetMapping("/all/")
	private ResponseEntity<?> getUser() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String username = authentication.getName();
		boolean exist = userService.findUser(username)!=null;
		if(exist) {
			User u = userService.findUser(username);
			return ResponseEntity.ok(u);
		}
		else
			return ResponseEntity.noContent().build();
	}
}
