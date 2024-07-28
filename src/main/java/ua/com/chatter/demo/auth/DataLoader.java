// package ua.com.chatter.demo.auth;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.CommandLineRunner;
// import org.springframework.security.crypto.password.PasswordEncoder;
// import org.springframework.stereotype.Component;

// import ua.com.chatter.demo.model.ChatterUser;
// import ua.com.chatter.demo.repository.UserRepository;

// @Component
// public class DataLoader implements CommandLineRunner {

//     @Autowired
//     private UserRepository userRepository;

//     @Autowired
//     private PasswordEncoder passwordEncoder;
// // 
//     @Override
//     public void run(String... args) throws Exception {
//         ChatterUser user = new ChatterUser();
//         userRepository.save(user);
//     }
// }
