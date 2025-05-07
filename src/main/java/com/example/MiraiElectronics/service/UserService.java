package com.example.MiraiElectronics.service;

import com.example.MiraiElectronics.dto.UpdateUserDataDTO;
import com.example.MiraiElectronics.repository.UserRepository;
import com.example.MiraiElectronics.repository.realization.User;
import com.example.MiraiElectronics.service.Parser.UserParserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final UserParserService parserService;

    public UserService(UserRepository userRepository, UserParserService userParserService) {
        this.userRepository = userRepository;
        this.parserService = userParserService;
    }

    @Transactional
    public void saveUser(User user){
        userRepository.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .roles(user.getRole().toString())
                .build();
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Пользователь не найден: " + username));
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Пользователь с email " + email + " не найден"));
    }

    public User findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("Пользователь с ID " + id + " не найден"));
    }

    public User getUser(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
    }

    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found with email: " + email));
    }

    public User createUser(User user){
        Optional<User> optionalUser = userRepository.findByEmail(user.getEmail());
        if (optionalUser.isPresent())
            throw new IllegalStateException("user with such email exists");

        return userRepository.save(user);
    }

    @Transactional
    public User updateUser(User currentUser, UpdateUserDataDTO updateUserDataDTO) {
        UpdateUserDataDTO validUpdateUserData = validateAndNormalize(updateUserDataDTO);

        if (!currentUser.getUsername().equals(validUpdateUserData.getUsername()) &&
                isUserExist(null, validUpdateUserData.getUsername())) {
            throw new IllegalArgumentException("username already used");
        }

        currentUser.setUsername(validUpdateUserData.getUsername());
        currentUser.setPhone(validUpdateUserData.getPhoneNumber());
        currentUser.setAddress(validUpdateUserData.getAddress().toString());

        return userRepository.save(currentUser);
    }


    public ResponseEntity<?> deleteUser(User user){
        userRepository.delete(user);
        return ResponseEntity.ok("deleted" + user.getUsername());
    }

    public boolean isUserExist(String email, String username) {
        boolean emailExists = email != null && userRepository.findByEmail(email).isPresent();
        boolean usernameExists = username != null && userRepository.findByUsername(username).isPresent();
        return emailExists || usernameExists;
    }


    public UpdateUserDataDTO validateAndNormalize(UpdateUserDataDTO dto) {
        if (!parserService.isValidUsername(dto.getUsername()))
            throw new IllegalArgumentException("Invalid username format");

        if (!parserService.isValidPhoneNumber(dto.getPhoneNumber()))
            throw new IllegalArgumentException("Invalid phone number format");

        if (!parserService.isValidAddress(dto.getAddress()))
            throw new IllegalArgumentException("Invalid address format");

        return new UpdateUserDataDTO(
                dto.getUsername().trim().toLowerCase(),
                parserService.parsePhoneNumber(dto.getPhoneNumber()),
                dto.getAddress()
        );
    }

}
