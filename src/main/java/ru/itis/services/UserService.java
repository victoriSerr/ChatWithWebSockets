package ru.itis.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import ru.itis.dto.SignInDto;
import ru.itis.dto.SignUpDto;
import ru.itis.models.AppUser;
import ru.itis.repositories.UserRepository;

import java.util.*;

@Component
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder encoder;

    public List<AppUser> findAll() {
        return userRepository.findAll();
    }

    public void signUp(SignUpDto form) {

        String rawPassword = form.getPassword();
        String hashPassword = encoder.encode(rawPassword);


        AppUser appUser = AppUser.builder()
                .login(form.getLogin())
                .hashPassword(hashPassword)
                .build();

        userRepository.save(appUser);
    }

    public AppUser signIn(SignInDto signInDto) {

        Optional<AppUser> userCandidate = userRepository.findByLogin(signInDto.getLogin());

        if (userCandidate.isPresent()) {
            AppUser appUser = userCandidate.get();
            if (encoder.matches(signInDto.getPassword(), appUser.getHashPassword())) {
                return appUser;
            }
        }

        throw new AccessDeniedException("User not found");

    }


    public AppUser findUserByLogin(String login) {
        Optional<AppUser> userCandidate = userRepository.findByLogin(login);

        if (userCandidate.isPresent()) {
            return userCandidate.get();
        }
        throw new AccessDeniedException("User not found");
    }

    public AppUser findUser(Long id) {
        Optional<AppUser> userCandidate = userRepository.findOne(id);
        if (userCandidate.isPresent()) {
            return userCandidate.get();
        }
        throw new AccessDeniedException("User not found");
    }

    public void update(AppUser appUser) {
        userRepository.update(appUser);
    }



}
