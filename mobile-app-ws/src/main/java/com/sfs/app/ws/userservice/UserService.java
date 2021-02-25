package com.sfs.app.ws.userservice;

import com.sfs.app.ws.ui.model.request.UserDetailsRequestModel;
import com.sfs.app.ws.ui.model.response.UserRest;
import org.springframework.lang.Nullable;

import java.util.List;

public interface UserService {

    UserRest createUser(UserDetailsRequestModel userDetails);

    List<UserRest> getUsers();

    @Nullable
    UserRest findUser(String userId);

    void updateUser(String userId, UserRest userRest);

    UserRest deleteUser(String userId);
}
