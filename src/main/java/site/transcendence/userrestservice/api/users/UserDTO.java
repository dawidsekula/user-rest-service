package site.transcendence.userrestservice.api.users;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import site.transcendence.userrestservice.api.roles.RoleDTO;

import java.io.Serializable;
import java.util.Set;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class UserDTO implements Serializable {

    @Getter(AccessLevel.PRIVATE)
    private static final long serialVersionUID = -1723457029548057477L;

    private Long id;
    private String username;

    private String email;
    private String emailVerificationToken;
    private boolean isEmailVerified;

    private String passwordResetToken;

    private Set<RoleDTO> roles;

}
