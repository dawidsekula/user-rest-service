package site.transcendence.userrestservice.api.users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import site.transcendence.userrestservice.api.requests.UserCreateRequest;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
public class UserRestController {

    @Autowired
    private UserService userService;

    @PostMapping
    public UserDTO createUser(@Valid @RequestBody UserCreateRequest request){
        return userService.createUser(request);
    }

    @PreAuthorize("#username == authentication.name or hasRole('ROLE_ADMIN')")
    @GetMapping("/{username}")
    public UserDTO getUser(@PathVariable("username") String username){
        return userService.getUser(username);
    }

    @GetMapping
    public List<UserDTO> getUsers(){
        return userService.getUsers();
    }

    @Secured({"ROLE_ADMIN"})
    @DeleteMapping("/{username}")
    public String deleteUser(@PathVariable("username") String username){
        userService.deleteUser(username);
        return "Deleted";
    }

}
