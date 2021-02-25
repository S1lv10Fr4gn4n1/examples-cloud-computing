package com.sfs.photoapp.api.users.shared;

import feign.Response;
import feign.codec.ErrorDecoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

@Component
public class FeignErrorDecoder implements ErrorDecoder {

    private final Environment environment;

    @Autowired
    public FeignErrorDecoder(Environment environment) {
        this.environment = environment;
    }

    @Override
    public Exception decode(String methodKey, Response response) {
        switch (response.status()) {
            case 400:
                return new ResponseStatusException(HttpStatus.valueOf(response.status()), response.reason());
            case 404:
                if (methodKey.contains("getAlbums")) {
                    String reason = environment.getProperty("albums.exceptions.albums-not-found");
                    return new ResponseStatusException(HttpStatus.valueOf(response.status()), reason);
                }
            default:
                return new Exception(response.reason());
        }
    }
}
