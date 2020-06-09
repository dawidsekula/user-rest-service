package site.transcendence.userrestservice.api.users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import site.transcendence.userrestservice.api.requests.UserCreateRequest;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    private UserMapper mapper = UserMapper.INSTANCE;

    @Override
    public UserDTO createUser(UserCreateRequest request) {
        // Mapping UserCreateRequest to UserEntity
        UserEntity createdUser = mapper.toEntity(request);

        // Encrypting password from UserCreateRequest and setting it to UserEntity
        createdUser.setEncryptedPassword(passwordEncoder.encode(request.getPassword()));

        // Saving created UserEntity to repository
        // Saved UserEntity with id set by repository is assigned to savedUser variable
        UserEntity savedUser = userRepository.save(createdUser);

        // Mapping saved UserEntity to UserDTO
        UserDTO resultUser = mapper.toDTO(savedUser);

        // Returning UserDTO
        return resultUser;
    }

    @Override
    public UserDTO getUser(String username) {
        return mapper.toDTO(getUserEntity(username));
    }

    @Override
    public List<UserDTO> getUsers(){
        Iterable<UserEntity> userEntityIterable = userRepository.findAll();
        List<UserDTO> resultList = new ArrayList<>();

        userEntityIterable.forEach(userEntity -> {
            resultList.add(mapper.toDTO(userEntity));
        } );

        return resultList;
    }

    @Override
    public UserDTO updateUser(String username, UserDTO updatedUser) {
        return null;
    }

    @Override
    public void deleteUser(String username) {
        UserEntity userToDelete = getUserEntity(username);
        userRepository.delete(userToDelete);
    }

    @Override
    public UserEntity getUserEntity(String username) {
        Optional<UserEntity> foundUser = userRepository.findByUsername(username);

        if (foundUser.isPresent()) {
            return foundUser.get();
        } else {
            throw new RuntimeException("User does not exist, username: " + username);
        }
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity foundUser = getUserEntity(username);

        return User.withUsername(foundUser.getUsername())
                .password(foundUser.getEncryptedPassword())
                .roles("USER")
                .build();
    }
}
