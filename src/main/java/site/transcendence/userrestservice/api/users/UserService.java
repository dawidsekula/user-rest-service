package site.transcendence.userrestservice.api.users;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import site.transcendence.userrestservice.api.requests.UserCreateRequest;

import java.util.List;

public interface UserService extends UserDetailsService {

    // CREATE
    UserDTO createUser(UserCreateRequest request);

    // READ
    UserDTO getUser(String username);

    UserEntity getUserEntity(String username);

    List<UserDTO> getUsers();

    // UPDATE
    UserDTO updateUser(String username, UserDTO updatedUser);

    // DELETE
    void deleteUser(String username);

    // OTHERS
    List<GrantedAuthority> getUserAuthorities(String username);
    //    List<UserDTO> getUsersByRoles(String... roleName);
    //    List<UserDTO> getUsersByAuthorities(String... authorityName);
    void verifyEmail(String token);

}
