package com.example.firstcrud.controller;

import com.example.firstcrud.model.User;
import com.example.firstcrud.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/")
    public String helloWorld(){
        return "Hello World";
    }

    @GetMapping("/users")
    public List<User> getUsers(){
        return userRepository.findAll();
    }

    //capture by id
    @GetMapping("/user/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id){
        Optional<User> user = userRepository.findById(id);

        //check if the user exists
        if(user.isPresent()){
            return new ResponseEntity<>(user.get(), HttpStatus.OK);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    //create users with post
    @PostMapping("/user")
    public ResponseEntity<User> createUser(@RequestBody User user){
        try {
            User savedUser = userRepository.save(user);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedUser);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    //delete user using the delete method
    @DeleteMapping("/user/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id){
        try{
            if(userRepository.existsById(id)){
                userRepository.deleteById(id);
                return ResponseEntity.ok("User deleted successfully");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error ocurred while deleting the user");
        }
    }

    //update user using the put method
    @PutMapping("/user/{id}")
    public ResponseEntity<String> updateUser(@PathVariable Long id, @RequestBody User updateUser){
        try{
            Optional<User> userOptional=userRepository.findById(id);

            if(userOptional.isPresent()){
                User user = userOptional.get();
                //Update user fields
                user.setName(updateUser.getName());
                user.setPassword(updateUser.getPassword());

                //Save updated user to database
                userRepository.save(user);

                //return successful response
                return ResponseEntity.ok("User updated successfully ");
            } else {
                //User not found
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error updating user"+e.getMessage());
        }


    }

}
