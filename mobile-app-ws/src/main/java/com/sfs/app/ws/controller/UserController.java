package com.sfs.app.ws.controller;

import com.sfs.app.ws.ui.model.request.UpdateUserDetailsRequestModel;
import com.sfs.app.ws.ui.model.request.UserDetailsRequestModel;
import com.sfs.app.ws.ui.model.response.UserRest;
import com.sfs.app.ws.userservice.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(
        value = "users",
        produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE},
        consumes = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<List<UserRest>> getUsers(
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "limit", defaultValue = "10") int limit,
            @RequestParam(value = "sort", defaultValue = "desc") String sort) {
        List<UserRest> users = userService.getUsers();
        if (users.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping(path = "/{userId}")
    public ResponseEntity<UserRest> getUser(@PathVariable String userId) {
        // TODO test exceptions
//        String firstName = null;
//        int length = firstName.length();

        UserRest userRest;
        if ((userRest = userService.findUser(userId)) != null) {
            return new ResponseEntity<>(userRest, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


    @PostMapping
    public ResponseEntity<UserRest> createUser(@Valid @RequestBody UserDetailsRequestModel userDetails) {
        UserRest userRest = userService.createUser(userDetails);

        return new ResponseEntity<>(userRest, HttpStatus.OK);
    }


    @PutMapping(path = "/{userId}")
    public ResponseEntity<UserRest> updateUser(
            @PathVariable String userId,
            @Valid @RequestBody UpdateUserDetailsRequestModel userDetails
    ) {
        UserRest storedUserRest = userService.findUser(userId);
        if (storedUserRest == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        storedUserRest.setFirstName(userDetails.getFirstName());
        storedUserRest.setLastName(userDetails.getLastName());
        userService.updateUser(storedUserRest.getUserId(), storedUserRest);

        return new ResponseEntity<>(storedUserRest, HttpStatus.OK);
    }


    @DeleteMapping(path = "/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable String userId) {
        UserRest storedUserRest = userService.deleteUser(userId);
        if (storedUserRest != null) {
            return new ResponseEntity<>(HttpStatus.OK);

        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
