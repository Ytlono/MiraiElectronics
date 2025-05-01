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
        if (optionalUser.isPresent()){
            throw new IllegalStateException("user with such email exists");
        }
        return userRepository.save(user);
    }

    @Transactional
    public ResponseEntity<?> updateUser(Long id, UpdateUserDataDTO updateUserDataDTO){
        UpdateUserDataDTO validUpdateUserData = validateAndNormalize(updateUserDataDTO);

        User existingUser = findById(id);

        if (!existingUser.getUsername().equals(validUpdateUserData.getUsername()) && 
            isUserExist(null, validUpdateUserData.getUsername())) {
            throw new IllegalArgumentException("username already used");
        }

        existingUser.setUsername(validUpdateUserData.getUsername());
        existingUser.setPhone(validUpdateUserData.getPhoneNumber());
        existingUser.setAddress(validUpdateUserData.getAddress().toString());

        User updatedUser = userRepository.save(existingUser);
        return ResponseEntity.ok(updatedUser);
    }

    public ResponseEntity<?> deleteUser(Long id){
        Optional<User> optionalUser = userRepository.findById(id);
        if(optionalUser.isEmpty()){
            throw new IllegalStateException("No users with such id");
        }
        return ResponseEntity.ok("deleted" + optionalUser.get().getUsername());
    }

    public boolean isUserExist(String email, String username) {
        boolean emailExists = email != null && userRepository.findByEmail(email).isPresent();
        boolean usernameExists = username != null && userRepository.findByUsername(username).isPresent();
        return emailExists || usernameExists;
    }


    public UpdateUserDataDTO validateAndNormalize(UpdateUserDataDTO dto) {
        if (!parserService.isValidUsername(dto.getUsername())) {
            throw new IllegalArgumentException("Invalid username format");
        }

        if (!parserService.isValidPhoneNumber(dto.getPhoneNumber())) {
            throw new IllegalArgumentException("Invalid phone number format");
        }

        if (!parserService.isValidAddress(dto.getAddress())) {
            throw new IllegalArgumentException("Invalid address format");
        }

        return new UpdateUserDataDTO(
                dto.getUsername().trim().toLowerCase(),
                parserService.parsePhoneNumber(dto.getPhoneNumber()),
                dto.getAddress()
        );
    }

}
