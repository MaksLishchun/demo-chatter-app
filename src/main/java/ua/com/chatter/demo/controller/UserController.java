package ua.com.chatter.demo.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ua.com.chatter.demo.model.dto.ChatterUserDTO;
import ua.com.chatter.demo.model.entity.ChatterUserEntity;
import ua.com.chatter.demo.repository.UserRepository;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public List<ChatterUserDTO> getAllUsers() {
        return mapEntityToDTO(userRepository.findAll());
    }

    @PostMapping
    public ResponseEntity<ChatterUserDTO> createUser(@RequestBody ChatterUserEntity chatterUser) {
        ChatterUserDTO userDTO = mapEntityToDTO(userRepository.save(chatterUser));
        return new ResponseEntity<>(userDTO, HttpStatus.CREATED);
    }

    @PostMapping("/list")
    public List<ChatterUserDTO> createUsers(@RequestBody List<ChatterUserEntity> users) {
        return mapEntityToDTO(userRepository.saveAll(users));
    }

    private List<ChatterUserDTO> mapEntityToDTO(List<ChatterUserEntity> usersEntities) {
        List<ChatterUserDTO> users = new ArrayList<>();
        for (ChatterUserEntity elem : usersEntities) {
            users.add(mapEntityToDTO(elem));
        }

        return users;
    }

    private ChatterUserDTO mapEntityToDTO(ChatterUserEntity usersEntities) {
        return new ChatterUserDTO(usersEntities.getUserId(),
                usersEntities.getFirstName(),
                usersEntities.getLastName(),
                usersEntities.getEmail(),
                usersEntities.getPhoneNumber(),
                usersEntities.getDateOfBirth(),
                usersEntities.getImageUrl(),
                usersEntities.getLastActiveTime());
    }
}
