package site.transcendence.userrestservice.api.users;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode

public class UserDTO implements Serializable {

    @Getter(AccessLevel.PRIVATE)
    private static final long serialVersionUID = -1723457029548057477L;

    private Long id;
    private String username;
    private String email;

}
