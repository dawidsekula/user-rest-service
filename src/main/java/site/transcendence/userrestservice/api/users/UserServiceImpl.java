package site.transcendence.userrestservice.api.users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.transcendence.userrestservice.api.requests.PasswordResetRequest;
import site.transcendence.userrestservice.api.requests.UserCreateRequest;
import site.transcendence.userrestservice.configuration.PropertiesConstant;
import site.transcendence.userrestservice.exception.PasswordResetException;
import site.transcendence.userrestservice.exception.UserNotFoundException;
import site.transcendence.userrestservice.utils.JWTUtil;
import site.transcendence.userrestservice.utils.mail.MailService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private MailService mailService;

    private UserMapper mapper = UserMapper.INSTANCE;

    @Override
    public UserDTO createUser(UserCreateRequest request) {
        // Mapping UserCreateRequest to UserEntity
        UserEntity createdUser = mapper.toEntity(request);

        // Encrypting password from UserCreateRequest and setting it to UserEntity
        createdUser.setEncryptedPassword(passwordEncoder.encode(request.getPassword()));

        // Setting email verification token
        createdUser.setEmailVerificationToken(JWTUtil.generateEmailVerificationToken(createdUser.getUsername()));
        createdUser.setEmailVerified(false);

        // Saving created UserEntity to repository
        // Saved UserEntity with id set by repository is assigned to savedUser variable
        UserEntity savedUser = userRepository.save(createdUser);

        // Sending email with confirmation link
        // Depending on application properties, this service can be either enabled or disabled
        if (PropertiesConstant.REGISTRATION_CONFIRMATION_ENABLED) {
            mailService.sendRegistrationConfirmation(
                    savedUser.getEmail(),
                    savedUser.getEmailVerificationToken());
        }else{
            savedUser.setEmailVerificationToken(null);
            savedUser.setEmailVerified(true);
            savedUser = userRepository.save(savedUser);
        }

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
    public List<UserDTO> getUsers() {
        Iterable<UserEntity> userEntityIterable = userRepository.findAll();
        List<UserDTO> resultList = new ArrayList<>();

        userEntityIterable.forEach(userEntity -> {
            resultList.add(mapper.toDTO(userEntity));
        });

        return resultList;
    }

    @Override
    @Transactional
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
            throw new UserNotFoundException("User has not been found. Requested user: " + username);
        }
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity foundUser = getUserEntity(username);
        List<GrantedAuthority> authorities = getUserAuthorities(username);

        return User.withUsername(foundUser.getUsername())
                .password(foundUser.getEncryptedPassword())
                .disabled(!foundUser.isEmailVerified())
                .authorities(authorities)
                .build();
    }

    @Override
    public List<GrantedAuthority> getUserAuthorities(String username) {
        return jdbcTemplate.queryForList(
                "SELECT r.name " +
                        "FROM roles r " +
                        "INNER JOIN users_roles ur ON ur.role_id=r.role_id " +
                        "INNER JOIN users u ON u.user_id=ur.user_id " +
                        "WHERE u.username = ?" +
                        "AND ur.user_id = u.user_id", String.class, username)
                .stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void verifyEmail(String token) {
        Optional<UserEntity> foundUser = userRepository.findByEmailVerificationToken(token);

        if (foundUser.isPresent() && !JWTUtil.hasTokenExpired(token)) {
            UserEntity user = foundUser.get();
            user.setEmailVerified(true);
            user.setEmailVerificationToken(null);

            userRepository.save(user);
        }

    }

    @Override
    public void requestPasswordReset(String email) {
        if (!PropertiesConstant.PASSWORD_RESET_ENABLED){
            throw new PasswordResetException("Password reset is disabled");
        }

        Optional<UserEntity> foundUser = userRepository.findByEmail(email);

        if (foundUser.isPresent()) {
            UserEntity user = foundUser.get();
            String token = JWTUtil.generatePasswordResetToken(user.getUsername());
            user.setPasswordResetToken(token);
            userRepository.save(user);

            mailService.sendPasswordResetRequest(
                    user.getUsername(),
                    user.getEmail(),
                    user.getPasswordResetToken()
            );
        }
    }

    @Override
    @Transactional
    public void resetPassword(String token, PasswordResetRequest request) {
        if (!PropertiesConstant.PASSWORD_RESET_ENABLED){
            throw new PasswordResetException("Password reset is disabled");
        }

        Optional<UserEntity> foundUser = userRepository.findByPasswordResetToken(token);

        if (foundUser.isPresent() && !JWTUtil.hasTokenExpired(token)){
            UserEntity user = foundUser.get();
            user.setEncryptedPassword(
                    passwordEncoder.encode(request.getPassword())
            );
            user.setPasswordResetToken(null);
            userRepository.save(user);
        }else{
            throw new PasswordResetException("Password reset has failed, try again");
        }

    }
}
