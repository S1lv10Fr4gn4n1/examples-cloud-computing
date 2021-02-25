package com.sfs.photoapp.api.users.ui.controllers;

import com.sfs.photoapp.api.users.service.UsersService;
import com.sfs.photoapp.api.users.shared.UserDto;
import com.sfs.photoapp.api.users.ui.model.CreateUserRequestModel;
import com.sfs.photoapp.api.users.ui.model.CreateUserResponseModel;
import com.sfs.photoapp.api.users.ui.model.UserResposeModel;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(
        value = "/users",
        produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE},
        consumes = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
public class UsersController {

    @Autowired
    private Environment environment;
    @Autowired
    private UsersService usersService;

    @GetMapping(value = "/status/check", produces = MediaType.TEXT_PLAIN_VALUE)
    public String status() {
        return "users-ws working on port = " + environment.getProperty("local.server.port") +
                "\ntoken.secret = " + environment.getProperty("token.secret") +
                "\nmyApplication.environment = " + environment.getProperty("myApplication.environment");
    }

    @PostMapping
    public ResponseEntity<CreateUserResponseModel> createUser(@Valid @RequestBody CreateUserRequestModel userDetails) {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        UserDto userDto = modelMapper.map(userDetails, UserDto.class);

        UserDto createUser = usersService.createUser(userDto);

        CreateUserResponseModel returnValue = modelMapper.map(createUser, CreateUserResponseModel.class);
        return ResponseEntity.status(HttpStatus.CREATED).body(returnValue);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserResposeModel> getUser(@PathVariable("userId") String userId) {
        UserDto userDto = usersService.getUserByUserId(userId);
        UserResposeModel userResposeModel = new ModelMapper().map(userDto, UserResposeModel.class);
        return ResponseEntity.status(HttpStatus.OK).body(userResposeModel);
    }
}
