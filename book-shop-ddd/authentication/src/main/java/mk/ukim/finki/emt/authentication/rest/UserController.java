package mk.ukim.finki.emt.authentication.rest;

import lombok.AllArgsConstructor;
import mk.ukim.finki.emt.authentication.domain.model.User;
import mk.ukim.finki.emt.authentication.domain.model.UserId;
import mk.ukim.finki.emt.authentication.service.UserService;
import mk.ukim.finki.emt.sharedkernel.domain.user.Address;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/user")
@AllArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/{id}")
    public User getUserById(@PathVariable UserId id) {
        return this.userService.findById(id);
    }

    @GetMapping("/username")
    public User getUserByUsername(@RequestParam String username) {
        return this.userService.findByUsername(username);
    }

    @GetMapping("/user-address/{id}")
    public Address getUserAddress(@PathVariable UserId id) {
        return this.userService.findById(id).getAddress();
    }
}
