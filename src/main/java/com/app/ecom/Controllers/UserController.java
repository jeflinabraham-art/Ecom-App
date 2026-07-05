package com.app.ecom.Controllers;

import com.app.ecom.Service.UserService;
import com.app.ecom.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;

    //    ResponseEntity gives you control over the HTTP response.
    //    Without ResponseEntity, Spring automatically returns 200 OK. Not ideal since the endpoint will have multiple outcomes not just success case.
    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
    //        return ResponseEntity.ok(userService.getAllUsers());
        return ResponseEntity.status(HttpStatus.OK).body(userService.getAllUsers());
    }

    @PostMapping
    public ResponseEntity<String> createUser(@RequestBody User user) {
        // @RequestBody tells spring: Take the JSON from the HTTP request body and convert it into a Java object.
        userService.addUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body("User created");
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUser(@PathVariable Long id) {
        // @PathVariable tells Spring:
        // Take a value from the URL path and pass it as a method parameter.
          return userService.getUserById(id)
                  .map(ResponseEntity::ok)
                  .orElseGet(() -> ResponseEntity.notFound().build());
    }
//    Instead of writing a lambda like this:
//      x -> someMethod(x)
//     .map(user -> ResponseEntity.ok(user))
//    you can write:
//      className::someMethod
//     .map(ResponseEntity::ok)

    @PutMapping("/{id}")
    public ResponseEntity<String> updateUser(@PathVariable Long id, @RequestBody User user) {
        boolean updated = userService.updateUser(id, user);
        if (updated) {
            return ResponseEntity.status(HttpStatus.OK).body("User updated");
        }
        return ResponseEntity.notFound().build();
    }
}

