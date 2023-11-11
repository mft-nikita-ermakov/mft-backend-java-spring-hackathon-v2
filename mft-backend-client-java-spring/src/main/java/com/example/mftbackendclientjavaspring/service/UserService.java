package com.example.mftbackendclientjavaspring.service;

import com.example.mftbackendclientjavaspring.entity.Role;
import com.example.mftbackendclientjavaspring.entity.User;
import com.example.mftbackendclientjavaspring.repository.RoleRepository;
import com.example.mftbackendclientjavaspring.repository.UserRepository;
import com.example.mftbackendclientjavaspring.request.RegistrationUserDto;
import com.example.mftbackendclientjavaspring.util.JwtTokenUtils;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService implements UserDetailsService {
    @PersistenceContext
    private EntityManager em;
    @Autowired
    UserRepository userRepository;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    private JwtTokenUtils jwtTokenUtils;

    @Autowired
    UserService userService;
    User user;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }

        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                user.getRole_set().stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList())
        );
    }

    public User createNewUser(RegistrationUserDto registrationUserDto) {
        User user = new User();
        String username = registrationUserDto.getUsername();
        if (Objects.equals(username, "admin")) {
            user.setUsername(registrationUserDto.getUsername());
            user.setPassword(passwordEncoder.encode(registrationUserDto.getPassword()));
            user.setRole_set(Collections.singleton(new Role(2L, "ROLE_ADMIN")));
            return userRepository.save(user);
        }
        user.setUsername(registrationUserDto.getUsername());
        user.setPassword(passwordEncoder.encode(registrationUserDto.getPassword()));
        user.setRole_set(Collections.singleton(new Role(1L, "ROLE_WORKER")));
        return userRepository.save(user);
    }

    public User findUserById(Long userId) {
        Optional<User> userFromDb = userRepository.findById(userId);
        return userFromDb.orElse(new User());
    }

    public Optional<User> findByUsername(String username) {
        return Optional.ofNullable(userRepository.findByUsername(username));
    }

    public List<User> allUsers() {
        return userRepository.findAll();
    }

    public boolean saveUser(User user) {
        User userFromDB = userRepository.findByUsername(user.getUsername());

        if (userFromDB != null) {
            return false;
        }

        user.setRole_set(Collections.singleton(new Role(1L, "ROLE_USER")));
        user.setPassword(DigestUtils.sha1Hex(user.getPassword()));
        userRepository.save(user);
        return true;
    }

    public boolean deleteUser(Long userId) {
        if (userRepository.findById(userId).isPresent()) {
            userRepository.deleteById(userId);
            return true;
        }
        return false;
    }

    public List<User> usergtList(Long idMin) {
        return em.createQuery("SELECT u FROM User u WHERE u.id > :paramId", User.class)
                .setParameter("paramId", idMin).getResultList();
    }

    public String decodeToken(String token) {
        System.out.println("token:" + token);
        // Decrypt the token and extract the claims
        Claims claims = Jwts.parser().setSigningKey(jwtTokenUtils.getSecretKey()).parseClaimsJws(token).getBody();

        // Extract the user's role from the claims
        String role = claims.get("roles").toString();

        // Return the user's role as a response
        System.out.println("Ответ /decode" + role);
        return role;
    }

}
