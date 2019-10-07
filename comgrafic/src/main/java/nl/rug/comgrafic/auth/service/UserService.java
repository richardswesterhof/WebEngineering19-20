package nl.rug.comgrafic.auth.service;

import nl.rug.comgrafic.auth.model.Role;
import nl.rug.comgrafic.auth.model.User;
import nl.rug.comgrafic.auth.repository.RoleRepository;
import nl.rug.comgrafic.auth.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

@Service
public class UserService {
    private UserRepository userRepository;
    private RoleRepository roleRepository;
//    private PrinterService printerService;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserService (UserRepository userRepository,
                        RoleRepository roleRepository,
//                        PrinterService printerService,
                        BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
//        this.printerService = printerService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public User findByUsername (String username) {
        return userRepository.findByUsername (username);
    }

    public List<User> getAllUsers () {
        return userRepository.findAll ();
    }

    public void saveUser (User user, String roleName) {
        System.out.println ("saving user " + user.getUsername () + " with role " + roleName);
        user.setPassword (bCryptPasswordEncoder.encode (user.getPassword ()));
//        user.setPassword ((user.getPassword ()));
        Role userRole = roleRepository.findByRole (roleName);
        user.setEnabled(true);
        user.setRoles (new HashSet<> (Arrays.asList (userRole)));
        userRepository.save (user);
//        if (roleName.equals ("PRINTER")) {
//            printerService.addPrinter (user);
//        }
    }
}
