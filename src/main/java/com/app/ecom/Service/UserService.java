package com.app.ecom.Service;

import com.app.ecom.User;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserService  {

    Long id = 1L;
    List<User> users = new ArrayList<User>();

    public List<User> getAllUsers() {
        return users;
    }

    public void addUser(User user) {
        user.setId(id++);
        users.add(user);
    }

    public Optional<User> getUserById(Long id) {
        return users.stream()
                .filter(user -> user.getId().equals(id))
                .findFirst();
    }

    public boolean updateUser(Long id, User updatedUser) {
        return users.stream()
                .filter(user -> user.getId().equals(id))
                .findFirst()
                .map(existingUser -> {
                    existingUser.setFirstName(updatedUser.getFirstName());
                    existingUser.setLastName(updatedUser.getLastName());
                    return true;
                }).orElse(false);
    }
}
