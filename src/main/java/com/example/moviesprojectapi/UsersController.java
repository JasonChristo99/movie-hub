package com.example.moviesprojectapi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class UsersController {

    @Autowired
    private UserRepository userRepository;

    public UsersController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostMapping(value = "/login", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public ResponseEntity<HttpStatus> login(@RequestParam(value = "email") String email, @RequestParam(value = "password") String password) {
        System.out.println("Login request { " + email + ", " + password + " / " + PasswordEncryptor.toSHA1(password) + " } ");
        User result = this.userRepository.findByEmailAndPassword(email, PasswordEncryptor.toSHA1(password));
        if (result != null) {
            System.out.println("Login success");
            return new ResponseEntity<HttpStatus>(HttpStatus.OK);
        } else {
            System.out.println("Login fail");
            return new ResponseEntity<HttpStatus>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping(value = "/register", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public ResponseEntity<HttpStatus> register(@RequestParam(value = "email") String email, @RequestParam(value = "password") String password) {
        System.out.println("Register request { " + email + ", " + password + " / " + PasswordEncryptor.toSHA1(password) + " } ");
        if (this.userRepository.findByEmail(email) != null) {
            System.out.println("Register failed");
            return new ResponseEntity<HttpStatus>(HttpStatus.BAD_REQUEST);
        } else {
            User user = new User(email, PasswordEncryptor.toSHA1(password));
            this.userRepository.save(user);
            System.out.println("Register success");
            return new ResponseEntity<HttpStatus>(HttpStatus.CREATED);
        }
    }

    @GetMapping(value = "/users")
    public Iterable<User> users() {
        return this.userRepository.findAll();
    }

}
