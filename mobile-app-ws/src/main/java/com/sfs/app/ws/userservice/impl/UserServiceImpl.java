package com.sfs.app.ws.userservice.impl;

import com.sfs.app.ws.ui.model.request.UserDetailsRequestModel;
import com.sfs.app.ws.ui.model.response.UserRest;
import com.sfs.app.ws.userservice.UserService;
import com.sfs.app.ws.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {

    private Map<String, UserRest> users = new HashMap<>();
    private Utils utils;


    public UserServiceImpl() {
        // NO-OP
    }

    @Autowired
    public UserServiceImpl(Utils utils) {
        this.utils = utils;
    }

    @Override
    public UserRest createUser(UserDetailsRequestModel userDetails) {
        UserRest userRest = new UserRest();
        userRest.setEmail(userDetails.getEmail());
        userRest.setFirstName(userDetails.getFirstName());
        userRest.setLastName(userDetails.getLastName());
        userRest.setUserId(utils.generateUserId());
        users.put(userRest.getUserId(), userRest);
        return userRest;
    }

    @Override
    public List<UserRest> getUsers() {
        return new ArrayList<>(users.values());
    }

    @Override
    public UserRest findUser(String userId) {
        return users.get(userId);
    }

    @Override
    public void updateUser(String userId, UserRest userRest) {
        users.put(userId, userRest);
    }

    @Override
    public UserRest deleteUser(String userId) {
        return users.remove(userId);
    }
}
