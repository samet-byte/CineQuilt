package com.sametb.cinequiltapp.user;

import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

public interface IUserService {

    void save(User user);

    List<User> findAll();

    List<UserResponse> findAllResponse();

    void changePassword(ChangePasswordRequest request, Principal connectedUser);

    UserResponse findByUsernameOrEmail(Principal connectedUser);

    User findByUsernameOrEmail(String usernameOrEmail);
    @Deprecated
    Optional<User> findByUsernameOrEmail(String username, String email);

    Optional<User> makeAdmin(String username);

    User findByUsername(String username);


    Optional<User> findById(Integer id);

    User findByIDNonOptional(Integer id);

    Optional<User> delete(String username, DeleteUserRequest request, Principal connectedUser);

    @Transactional
    void changeCountry(String country, Principal connectedUser);
}
