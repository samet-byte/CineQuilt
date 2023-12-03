package com.sametb.cinequiltapp.user;

import com.sametb.cinequiltapp.favs.Favourite;
import com.sametb.cinequiltapp.token.Token;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author Samet Bayat.
 * Date: 30.11.2023 12:06 AM
 * Project Name: CineQuiltApp
 * ©2023, NONE OF THE RIGHTS RESERVED.
 * MAYBE SOME OF 'EM. WHO KNOWS?
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

    private Integer id;
    private String username;
    private String email;
    private String country;
    private LocalDateTime createTime;
    private Role role;
//    private Collection<? extends GrantedAuthority> authorities;
    private List<String> authorities;

    // Omitting tokens collection
    private LocalDateTime currentTimestamp;

//    private List<Favourite> favourites;

    public static UserDTO fromUser(User user) {

        UserDTO userDTO = new UserDTO();
        userDTO.setCurrentTimestamp(LocalDateTime.now());
        userDTO.setId(user.getId());
        userDTO.setUsername(user.getUsername());
        userDTO.setEmail(user.getEmail());
        userDTO.setCountry(user.getCountry());
        userDTO.setCreateTime(user.getCreateTime());
        userDTO.setRole(user.getRole());
        userDTO.setAuthorities(user.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList());
        // Omitting tokens collection
//        userDTO.setFavourites(user.getFavourites());


        return userDTO;
    }

    public static List<UserDTO> fromUsers(List<User> users) {
        return users.stream().map(UserDTO::fromUser).toList();
    }
}
