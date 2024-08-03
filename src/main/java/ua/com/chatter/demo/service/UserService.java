package ua.com.chatter.demo.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import ua.com.chatter.demo.model.dto.UserDTO;
import ua.com.chatter.demo.model.entity.UserEntity;
import ua.com.chatter.demo.repository.UserRepository;
import ua.com.chatter.demo.utils.exceptions.EntityIsExistException;
import ua.com.chatter.demo.utils.exceptions.EntityIsNullException;

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

    public List<UserDTO> getActiveUsers() {
        List<UserEntity> userEntitys = userRepository.findAll();
        List<UserEntity> activeUsers = new ArrayList<>();
        for(UserEntity user : userEntitys) {
            if (user.isActive()) {
                activeUsers.add(user);
            }
        }
        return mapEntityToDTO(activeUsers);
    }

    public void deleteUser(Long userId) {
        try {
            userRepository.chageUserActiveStatus(userId, false);
        } catch (IllegalArgumentException exp) {
            throw new EntityIsNullException("User can not be null");
        } catch (OptimisticLockingFailureException e) {
            throw new EntityIsExistException("User can not be deleted");
        }
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
                usersEntities.getLastActiveTime(),
                usersEntities.getStatus());
    }
}
