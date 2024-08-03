package ua.com.chatter.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ua.com.chatter.demo.model.dto.ErrorResponse;
import ua.com.chatter.demo.model.dto.ErrorType;
import ua.com.chatter.demo.model.dto.UserDTO;
import ua.com.chatter.demo.service.UserService;
import ua.com.chatter.demo.utils.exceptions.ExceptionUtils;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public List<UserDTO> getAllUsers() {
        return userService.getUsers();
    }

    @GetMapping("/active")
    public List<UserDTO> getActiveUsers() {
        return userService.getActiveUsers();
    }

    @DeleteMapping
    public ResponseEntity<?> deleteUser(@RequestParam Long userId) {
        try {
            userService.deleteUser(userId);
            return ResponseEntity.ok().build();
        } catch (RuntimeException exc) {
            return ResponseEntity.badRequest().body(new ErrorResponse(400, exc.getMessage(), ExceptionUtils.getErrorType(exc, ErrorType.DELETE_USER)));
        }
    }
}
