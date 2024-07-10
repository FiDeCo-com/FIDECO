//package project.boot.fideco.service;
//
//
//import java.util.Optional;
//
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Service;
//
//import lombok.RequiredArgsConstructor;
//import project.boot.fideco.entity.Users;
//import project.boot.fideco.repository.UserRepository;
//
//@RequiredArgsConstructor
//@Service
//public class UserService {
//
//    private final UserRepository userRepository ;
//    private final PasswordEncoder passwordEncoder;
//
//    public Users create(String username, String email, String password) {
//        Users user = new Users();
//        user.setUsername(username);
//        user.setEmail(email);
//        user.setPassword(passwordEncoder.encode(password));
//        this.userRepository.save(user);
//        return user;
//    }
//    public Users getUser(String username) {
//        Optional<Users> users = this.userRepository.findByUsername(username);
//        if (users.isPresent()) {
//            return users.get();
//        } else {
//            throw new DataNotFoundException("siteuser not found");
//        }
//    }
//}
