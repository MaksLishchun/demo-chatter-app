package ua.com.chatter.demo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public List<ChatterUser> getAllUsers() {
        return userRepository.findAll();
    }

    @PostMapping
    public ResponseEntity<ChatterUser> createUser(@RequestBody ChatterUser chatterUser) {
        ChatterUser savedUser = userRepository.save(chatterUser);
        return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
    }
    
    // POST запит для додавання списку користувачів
    @PostMapping("/bulk")
    public List<ChatterUser> createUsers(@RequestBody List<ChatterUser> users) {
        return userRepository.saveAll(users);
    }
}
