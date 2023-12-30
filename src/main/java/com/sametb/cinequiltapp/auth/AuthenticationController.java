package com.sametb.cinequiltapp.auth;

import com.sametb.cinequiltapp._custom.RandomPasswordGeneratorKt;
import com.sametb.cinequiltapp._custom.SamTextFormat;
import com.sametb.cinequiltapp.config.ServerProperties;
import com.sametb.cinequiltapp.mail.SmtpGmailSenderService;
import com.sametb.cinequiltapp.config.LogoutService;
import com.sametb.cinequiltapp.user.IUserService;
import com.sametb.cinequiltapp.user.User;
import com.sametb.cinequiltapp.user.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.io.IOException;

import static com.sametb.cinequiltapp._custom.CustomFunsKt.prettyJsonMaker;
import static com.sametb.cinequiltapp._custom.RandomPasswordGeneratorKt.hashPassword;


@RestController
@RequestMapping("${endpoint.auth}")
@RequiredArgsConstructor
@CrossOrigin("http://localhost:3000")
public class AuthenticationController {

    private final AuthenticationService service;
    private final LogoutService logoutService;
    private final SmtpGmailSenderService gmailSenderService;
    private final ServerProperties serverProperties;
    private final IUserService userService;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(
      @RequestBody RegisterRequest request
    ) {
    try {
      AuthenticationResponse authenticationResponse = service.register(request);
        try{
          gmailSenderService.defaultRegisterMail(request.getEmail(), request.getUsername());
        } catch (Exception e){
            SamTextFormat.Companion.errorMessage("Error sending email: " + e.getMessage());
        }
      return ResponseEntity.ok(authenticationResponse);
    } catch (Exception e) {
      SamTextFormat.Companion.create(e.getMessage()).red().bold().print();
      return ResponseEntity.badRequest().build();
    }
    }

    @PostMapping("/authenticate")
    public ResponseEntity<?> authenticate(
          @RequestBody AuthenticationRequest request,
          @NotNull HttpServletResponse response
    ) {
        AuthenticationResponse authenticationResponse = service.authenticate(request);
        Cookie cookie = new Cookie("refreshToken", authenticationResponse.getRefreshToken());
    //    cookie.setHttpOnly(true); // This makes the cookie accessible only through the HTTP protocol
        cookie.setMaxAge(7 * 24 * 60 * 60); // Set the expiration time in seconds (adjust as needed)
        cookie.setPath("/"); // Set the cookie path (adjust as needed)
        response.addCookie(cookie);

        Cookie gApiCookie = new Cookie("gpt_api_key", serverProperties.getGptApiKey()); // Set the value accordingly
        gApiCookie.setMaxAge(7 * 24 * 60 * 60); // Set the expiration time in seconds (adjust as needed)
        gApiCookie.setPath("/"); // Set the cookie path (adjust as needed)
        response.addCookie(gApiCookie);

        try {
            String prettyOutput = prettyJsonMaker(authenticationResponse);
            return ResponseEntity.ok(prettyOutput);
        }catch (Exception e){
          SamTextFormat.Companion.errorMessage("Error: " + e.getMessage());
          return ResponseEntity.ok(authenticationResponse);
        }
    }

    @GetMapping("/refresh")
    public ResponseEntity<AuthenticationResponse> refreshToken(
      HttpServletRequest request
    ) {
    AuthenticationResponse responseRefresh = service.refreshToken(request);
    return ResponseEntity.ok(responseRefresh);
    }

    @PostMapping("/logout")
    public void logout(
            HttpServletRequest request,
            HttpServletResponse response
    ) {
        logoutService.logout(request, response, null);
    }


    @PostMapping("/reset-password-request")
    public ResponseEntity<?> resetPasswordRequest(
            @RequestBody RefreshPasswordRequest request
    ) {
        try {

            User user = userService.findByUsernameOrEmail(request.getEmail());
            if (user == null) {
                return ResponseEntity.badRequest().build();
            }
            String newPassword = RandomPasswordGeneratorKt.generateRandomPassword(8);
            user.setPassword(hashPassword(newPassword));
            userService.save(user);
            gmailSenderService.sendEmail(
                    user.getEmail(),
                    "Password Reset Request",
                    "Your new password is: " + newPassword
            );
            SamTextFormat.Companion.doneMessage("New password sent to: " + user.getEmail() + " ->  " + newPassword);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            SamTextFormat.Companion.errorMessage("Error: " + e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

}
