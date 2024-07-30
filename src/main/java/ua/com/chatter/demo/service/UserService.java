package ua.com.chatter.demo.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import ua.com.chatter.demo.model.dto.ChatterDefaultResponse;
import ua.com.chatter.demo.model.dto.UserDTO;
import ua.com.chatter.demo.model.entity.UserEntity;
import ua.com.chatter.demo.repository.UserRepository;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String phoneNumber) throws UsernameNotFoundException {
        UserEntity chatterUser = userRepository.findByPhoneNumber(phoneNumber)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return User.builder()
                .username(chatterUser.getPhoneNumber())
                .password(chatterUser.getPassword())
                .roles("USER")
                .build();
    }

    public List<UserDTO> getUsers() {
        return mapEntityToDTO(userRepository.findAll());
    }

    public ChatterDefaultResponse deleteUser(Long userId) {
        userRepository.deleteById(userId);
        return new ChatterDefaultResponse(200, "User deleted");
    }

    private List<UserDTO> mapEntityToDTO(List<UserEntity> usersEntities) {
        List<UserDTO> users = new ArrayList<>();
        for (UserEntity elem : usersEntities) {
            users.add(mapEntityToDTO(elem));
        }

        return users;
    }

    private UserDTO mapEntityToDTO(UserEntity usersEntities) {
        return new UserDTO(usersEntities.getId(),
                usersEntities.getFirstName(),
                usersEntities.getLastName(),
                usersEntities.getEmail(),
                usersEntities.getPhoneNumber(),
                usersEntities.getDateOfBirth(),
                usersEntities.getImageUrl(),
                usersEntities.getLastActiveTime());
    }
}
