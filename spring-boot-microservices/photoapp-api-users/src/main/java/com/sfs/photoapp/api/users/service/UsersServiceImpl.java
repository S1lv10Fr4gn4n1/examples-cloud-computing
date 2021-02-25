package com.sfs.photoapp.api.users.service;

import com.sfs.photoapp.api.users.data.feignclient.AlbumsServiceClient;
import com.sfs.photoapp.api.users.data.model.UserEntity;
import com.sfs.photoapp.api.users.data.repository.UsersRepository;
import com.sfs.photoapp.api.users.shared.UserDto;
import com.sfs.photoapp.api.users.ui.model.AlbumResponseModel;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class UsersServiceImpl implements UsersService {

    private final UsersRepository usersRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final AlbumsServiceClient albumsServiceClient;
    private final Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());

    @Autowired
    public UsersServiceImpl(
            UsersRepository usersRepository,
            BCryptPasswordEncoder bCryptPasswordEncoder,
            AlbumsServiceClient albumsServiceClient
    ) {
        this.usersRepository = usersRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.albumsServiceClient = albumsServiceClient;
    }

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        UserEntity userEntity = usersRepository.findByEmail(userName);

        if (userEntity == null) throw new UsernameNotFoundException(userName);

        return new User(userEntity.getEmail(), userEntity.getEncryptedPassword(), true, true, true, true, new ArrayList<>());
    }

    @Override
    public UserDto createUser(UserDto userDetails) {
        userDetails.setUserId(UUID.randomUUID().toString());
        userDetails.setEncryptedPassword(bCryptPasswordEncoder.encode(userDetails.getPassword()));

        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        UserEntity userEntity = modelMapper.map(userDetails, UserEntity.class);

        UserEntity savedUserEntity = usersRepository.save(userEntity);
        return getUserDto(savedUserEntity);
    }

    @Override
    public UserDto getUserDetailsByEmail(String email) {
        UserEntity userEntity = usersRepository.findByEmail(email);

        if (userEntity == null) {
            throw new UsernameNotFoundException(email);
        }

        return getUserDto(userEntity);
    }

    @Override
    public UserDto getUserByUserId(String userId) {
        UserEntity userEntity = usersRepository.findByUserId(userId);

        if (userEntity == null) {
            throw new UsernameNotFoundException("User not found");
        }

        UserDto userDto = getUserDto(userEntity);
        logger.info("Before calling albums microservice");
        List<AlbumResponseModel> albums = getAlbumsWithFeign(userId);
        logger.info("After calling albums microservice");
        userDto.setAlbums(albums);
        return userDto;
    }

    private UserDto getUserDto(UserEntity userEntity) {
        return new ModelMapper().map(userEntity, UserDto.class);
    }

    private List<AlbumResponseModel> getAlbumsWithFeign(String userId) {
        return albumsServiceClient.getAlbums(userId);
    }
}
