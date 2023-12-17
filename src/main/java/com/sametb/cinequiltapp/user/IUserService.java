package com.sametb.cinequiltapp.user;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

public interface IUserService {

    List<User> findAll();

    List<UserResponse> findAllResponse();

    void changePassword(ChangePasswordRequest request, Principal connectedUser);

    UserResponse findByUsernameOrEmail(Principal connectedUser);

    User findByUsernameOrEmail(String usernameOrEmail);

    Optional<User> makeAdmin(String username);

    User findByUsername(String username);

    Optional<User> findByUsernameOrEmail(String username, String email);

    Optional<User> findById(Integer id);

    User findByIDNonOptional(Integer id);

    Optional<User> delete(String username, DeleteUserRequest request, Principal connectedUser);

}
