package com.sametb.cinequiltapp.user;

import com.sametb.cinequiltapp.mail.JavaSmtpGmailSenderService;
import com.sametb.cinequiltapp._custom.SamTextFormat;
import com.sametb.cinequiltapp.metadata.Metadata;
import com.sametb.cinequiltapp.metadata.MetadataRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
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

    private final JavaSmtpGmailSenderService gmailSenderService;


    public List<User> findAll() {
        return repository.findAll();
    }
    public List<UserDTO> findAllDTO() {
        return UserDTO.fromUsers(repository.findAll());
    }
    public void changePassword(ChangePasswordRequest request, Principal connectedUser) {

        var user = (User) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal();

        // check if the current password is correct
        if (!passwordEncoder.matches(request.getCurrentPassword(), user.getPassword())) {
            throw new IllegalStateException("Wrong password");
        }
        // check if the two new passwords are the same
        if (!request.getNewPassword().equals(request.getConfirmationPassword())) {
            throw new IllegalStateException("Password are not the same");
        }

        gmailSenderService.sendEmail(
                user.getEmail(),
                "Password Changed",
                "Previous Password: " + request.getCurrentPassword() + "\n" +
                        "New Password: " + request.getNewPassword()
        );

        // update the password
        user.setPassword(passwordEncoder.encode(request.getNewPassword()));

        // save the new password
        repository.save(user);
    }

//    @Override
////    @Transactional
//    public String findByUsernameOrEmail(Principal connectedUser) {
//        var user = (User) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal();
//        SamTextFormat.Companion.create("user.getUsername(): " + user.getUsername() + "\n" +
//                "user.getEmail(): " + user.getEmail() + "\n").red().print();
//        return (
//                user.getUsername() + "\n" +
//                user.getEmail() + "\n" +
//                user.getCountry() + "\n" +
//                user.getRole() + "\n" +
//                user.getCreateTime() + "\n" +
//                user.getTokens() + "\n" +
//                user.getPassword() + "\n" +
//                user.getId() + "\n" +
//                        user.getAuthorities() + "\n" +
//                        user.isAccountNonExpired() + "\n"
//        );
////        return user;
////                repository.findByUsernameOrEmail(user.getUsername(), user.getEmail())
////                .orElseThrow(() -> new IllegalStateException("User not found"));
//    }

    @Override
    @Transactional
    public UserDTO findByUsernameOrEmail(Principal connectedUser) {
        var user = (User) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal();

        SamTextFormat.Companion.create("user.getUsername(): " + user.getUsername() + "\n" +
                "user.getEmail(): " + user.getEmail() + "\n").red().print();
        return UserDTO.fromUser(user);
//        return user;
//                repository.findByUsernameOrEmail(user.getUsername(), user.getEmail())
//                .orElseThrow(() -> new IllegalStateException("User not found"));
    }

    public User findByUsernameOrEmail(String usernameOrEmail) {
        return repository.findByUsernameOrEmail(usernameOrEmail, usernameOrEmail)
                .orElseThrow(() -> new IllegalStateException("User not found"));
    }

    @Override
    @Transactional //TransactionalRequired when executing an update/delete query.
    public Optional<User> makeAdmin(String usernameOrEmail) {
        var user = findByUsernameOrEmail(usernameOrEmail);
        user.setRole(Role.ADMIN);
        repository.save(user);
        return Optional.ofNullable(findByUsernameOrEmail(usernameOrEmail));
    }

    @Override
    public User findByUsername(String username) {
//        return repository.findByUsername(username);
        User user=  repository.findByUsername(username);
        return user;
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
    public Optional<User> delete(String username, DeleteUserRequest request, Principal connectedUser) {

        var user = (User) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal();

        // check if the current password is correct
        if (
                !passwordEncoder.matches(request.getPassword(), user.getPassword()) ||
                !user.getUsername().equals(username)
        ) {
            SamTextFormat.Companion.create(
                    "Wrong password or username\n" +
                            "request.getPassword(): " + request.getPassword() + "\n" +
                            "user.getPassword(): " + user.getPassword() + "\n" +
                            "user.getUsername(): " + user.getUsername() + "\n" +
                            "username: " + username + "\n"
                    )
                    .red().print();
            throw new IllegalStateException("Wrong password or username");
        } else {


            gmailSenderService.sendEmail(user.getEmail(), "Arrivederci " + user.getUsername(), "We'll miss you :(");

//            // Assuming you have a User entity instance called user
//            for (Token token : user.getTokens()) {
//                token.setUser(null); // disassociate the token from the user
//            }
//            user.getTokens().clear(); // remove all tokens from the list
//            user.setTokens(new ArrayList<>());
            user.setTokens(new ArrayList<>());
            repository.save(user);
            
            repository.deleteByUsername(user.getUsername());
            return Optional.of(user);
        }

    }


//    private final MetadataRepository metadataRepository;

//    public void addFavorite(Integer userId, Integer metadataId) {
//        try{
//            User user = repository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
//            Optional<Metadata> metadata = metadataRepository.findById(metadataId);
//
//            SamTextFormat.Companion.create(Optional.of(metadata.get().getTitle()).orElseThrow(() -> new RuntimeException("No metadata found"))).cyan().print();
//
//
//
////            user.getUser_favs().add(metadata.orElseThrow(() -> new RuntimeException("Metadata not found")));
////            SamTextFormat.Companion.create(Arrays.toString(user.getUser_favs().toArray())).cyan().print();
//            repository.save(user);
//        } catch (Exception e){
//            e.printStackTrace();
//        }
//    }
//
//    public void deleteFavorite(Integer userId, Integer metadataId) {
//        User user = repository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
////        user.getUser_favs().removeIf(metadata -> metadata.getId().equals(metadataId));
////        user.getUser_favs().removeIf(metadata -> false);
//        repository.save(user);
//    }


//    public void addToFavourites(Integer userId, Integer metadataId) {
//        User user = repository.findById(userId).orElseThrow(() -> new EntityNotFoundException("User not found"));
//        Metadata metadata = metadataRepository.findById(metadataId).orElseThrow(() -> new EntityNotFoundException("Metadata not found"));
//
//        user.getFavourites().add(metadata);
//
////        user.addFavourite(metadata);
//
//        repository.save(user);
//    }
//
//    public Set<Metadata> getUserFavourites(Integer userId) {
//        try {
//            User user = repository.findById(userId).orElseThrow(() -> new EntityNotFoundException("User not found"));
//            SamTextFormat.Companion.create(user.getFavourites().stream().map(Metadata::getTitle).toString()).cyan().print();
//            return user.getFavourites();
//        } catch (Exception e) {
//            SamTextFormat.Companion.create("getUserFavourites() error: " + e.getMessage()).red().print();
//            e.printStackTrace();
//            return null;
//        }
//    }







}
