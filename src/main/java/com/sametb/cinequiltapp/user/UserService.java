package com.sametb.cinequiltapp.user;

import com.sametb.cinequiltapp.exception.UserNotFoundException;
import com.sametb.cinequiltapp.mail.SmtpGmailSenderService;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.util.*;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository repository;
    private final SmtpGmailSenderService gmailSenderService;


    @Override
    public void save(User user) {
        repository.save(user);
    }

    @Override
    public List<User> findAll() {
        return repository.findAll();
    }

    @Override
    public List<UserResponse> findAllResponse() {
        return UserResponse.fromUsers(repository.findAll());
    }

    @Override
    public void changePassword(@NotNull ChangePasswordRequest request, Principal connectedUser) {
        var user = (User) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal();

        if (!passwordEncoder.matches(request.getCurrentPassword(), user.getPassword())) {
            throw new IllegalStateException("Wrong password");
        }
        if (!request.getNewPassword().equals(request.getConfirmationPassword())) {
            throw new IllegalStateException("Password are not the same");
        }

        informPasswordChanged(request, user);
        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        repository.save(user);
    }

    private void informPasswordChanged(@NotNull ChangePasswordRequest request, @NotNull User user) {
        gmailSenderService.defaultPasswordChanged(user.getEmail(), request.getNewPassword());
    }

    @Override
    @Transactional
    public UserResponse findByUsernameOrEmail(Principal connectedUser) {
        var user = (User) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal();
        return UserResponse.fromUser(user);
    }

    @Override
    public User findByUsernameOrEmail(String usernameOrEmail) {
        return repository.findByUsernameOrEmail(usernameOrEmail, usernameOrEmail)
                .orElseThrow(() -> new IllegalStateException("User not found"));
    }

    @Override
    @Transactional //Transactional required when executing an update/delete query.
    public Optional<User> makeAdmin(String usernameOrEmail) {
        var user = findByUsernameOrEmail(usernameOrEmail);
        if (user.getRole() == Role.ADMIN) user.setRole(Role.USER);
        else user.setRole(Role.ADMIN);
        repository.save(user);
        return Optional.ofNullable(findByUsernameOrEmail(usernameOrEmail));
    }

    @Override
    public User findByUsername(String username) {
        return repository.findByUsername(username);
    }

    @Override
    public Optional<User> findByUsernameOrEmail(String username, String email) {
        return repository.findByUsernameOrEmail(username, email);
    }

    @Override
    public Optional<User> findById(Integer id) {
        return repository.findById(id);
    }

    @Override
    public User findByIDNonOptional(Integer id) {
        return repository.findById(id).orElseThrow(() -> new IllegalStateException("User not found"));
    }

    @Override
    @Transactional
    public Optional<User> delete(String username, @NotNull DeleteUserRequest request, Principal connectedUser) {
        var user = (User) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal();
        if (
                !passwordEncoder.matches(request.getPassword(), user.getPassword()) ||
                        !user.getUsername().equals(username)
        ) {
            throw new IllegalStateException(new UserNotFoundException("User not found"));
        } else {
            gmailSenderService.defaultFarewell(user.getEmail(), user.getUsername());
            user.setTokens(new ArrayList<>());
            repository.save(user);
            repository.deleteByUsername(user.getUsername());
            return Optional.of(user);
        }
    }

    @Transactional
    @Override
    public void changeCountry(String country, Principal connectedUser) {
        var user = (User) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal();
        user.setCountry(country);
        repository.save(user);
    }
}