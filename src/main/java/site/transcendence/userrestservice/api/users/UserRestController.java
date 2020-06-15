package site.transcendence.userrestservice.api.users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import site.transcendence.userrestservice.api.requests.PasswordResetRequest;
import site.transcendence.userrestservice.api.requests.UserCreateRequest;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
public class UserRestController {

    private UserService userService;

    public UserRestController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<UserDTO> createUser(@Valid @RequestBody UserCreateRequest request){
        return new ResponseEntity<>(userService.createUser(request), HttpStatus.CREATED);
    }

    @PreAuthorize("#username == authentication.name or hasRole('ROLE_ADMIN')")
    @GetMapping("/{username}")
    public ResponseEntity<UserDTO> getUser(@PathVariable("username") String username){
        return new ResponseEntity<>(userService.getUser(username), HttpStatus.FOUND);
    }

    @GetMapping
    public ResponseEntity<List<UserDTO>> getUsers(){
        return new ResponseEntity<>(userService.getUsers(), HttpStatus.OK);
    }

    @Secured({"ROLE_ADMIN"})
    @DeleteMapping("/{username}")
    public ResponseEntity deleteUser(@PathVariable("username") String username){
        userService.deleteUser(username);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/verify-email")
    public ResponseEntity verifyEmail(@RequestParam("token") String token){
        userService.verifyEmail(token);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/password-reset-request")
    public ResponseEntity requestPasswordReset(@RequestParam("email") String email){
        userService.requestPasswordReset(email);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/reset-password")
    public ResponseEntity resetPassword(@RequestParam("token") String token,
                                @RequestBody @Valid PasswordResetRequest request){
        userService.resetPassword(token, request);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


}
