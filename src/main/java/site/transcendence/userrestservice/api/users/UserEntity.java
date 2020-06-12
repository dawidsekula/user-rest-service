package site.transcendence.userrestservice.api.users;

import lombok.*;
import org.hibernate.annotations.NaturalId;
import site.transcendence.userrestservice.api.roles.RoleEntity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor

@Entity(name = "users")
public class UserEntity implements Serializable {

    @Getter(AccessLevel.PRIVATE)
    private static final long serialVersionUID = -4507352732146561464L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @NaturalId(mutable = true)
    @Column(unique = true, length = 20)
    private String username;

    private String encryptedPassword;

    @Column(unique = true, length = 100)
    private String email;

    @ManyToMany
    @JoinTable(name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<RoleEntity> roles;

}
