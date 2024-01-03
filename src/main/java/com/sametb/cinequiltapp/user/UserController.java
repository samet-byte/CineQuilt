package com.sametb.cinequiltapp.user;


import com.sametb.cinequiltapp._custom.SamTextFormat;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("${endpoint.users}")
@RequiredArgsConstructor
//@CrossOrigin("*")
@CrossOrigin("http://localhost:3000")
public class UserController {

    private final UserService service;

    @GetMapping
    @PreAuthorize("hasAuthority('admin:read')")
    public ResponseEntity<?> findByUsernameOrEmail(
            Principal connectedUser
    ) {
        Optional<UserResponse> user = (Optional.ofNullable(service.findByUsernameOrEmail(connectedUser)));
        return ResponseEntity.ok().body(user);
    }

    @GetMapping("/{username}")
    @PreAuthorize("hasAuthority('admin:read')")
    public ResponseEntity<?> findByUsername(
            @PathVariable String username
    ) {
        UserResponse user = UserResponse.fromUser(service.findByUsername(username));
        return ResponseEntity.ok().body(user);
    }

    @GetMapping("/id/{id}")
    @PreAuthorize("hasAuthority('admin:read')")
    public ResponseEntity<?> findById(@PathVariable Integer id) {
        UserResponse user = UserResponse.fromUser(service.findByIDNonOptional(id));
        return ResponseEntity.ok().body(user);
    }


   @GetMapping("/all")
   @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<UserResponse>> findAll(
    ) {
        try {
            return ResponseEntity.ok().body(service.findAllResponse());
        } catch (Exception e) {
            SamTextFormat.Companion.create(e.getMessage()).red().print();
        }
        return null;
   }

    @PatchMapping("/make-admin/{username}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> makeAdmin(
            @PathVariable String username
    ) {
        Optional<User> user = service.makeAdmin(username);
        return ResponseEntity.ok().body(user);
    }

    @DeleteMapping("/{username}")
    public ResponseEntity<?> delete(
            @PathVariable String username,
            @RequestBody DeleteUserRequest request,
            Principal connectedUser
    ) {
        Optional<User> dUser = service.delete(username, request, connectedUser);
        return ResponseEntity.ok().body(dUser);
    }

    // Ayarlar
    // Settings
    // Şifre, ülke değiştir
    @PatchMapping("change/password")
    public ResponseEntity<?> changePassword(
          @RequestBody ChangePasswordRequest request,
          Principal connectedUser
    ) {
        service.changePassword(request, connectedUser);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("change/{country}")
    public ResponseEntity<?> changePassword(
            @PathVariable String country,
            Principal connectedUser
    ) {
        service.changeCountry(country, connectedUser);
        return ResponseEntity.ok().build();
    }
}
