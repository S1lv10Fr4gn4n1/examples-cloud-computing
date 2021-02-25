package com.sfs.photoapp.api.users.data.feignclient;

import com.sfs.photoapp.api.users.ui.model.AlbumResponseModel;
import feign.FeignException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.List;

class AlbumsServiceClientFallBack implements AlbumsServiceClient {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final Throwable cause;

    public AlbumsServiceClientFallBack(Throwable cause) {
        this.cause = cause;
    }

    @Override
    public List<AlbumResponseModel> getAlbums(String userId) {
        if (cause instanceof FeignException && ((FeignException) cause).status() == 404) {
            logger.error("404 error took place when getAlbums was called with userId: " + userId
                    + "\n" + cause.getLocalizedMessage());
        } else {
            logger.error("Other error took place: " + cause.getLocalizedMessage());
        }

        return Collections.emptyList();
    }
}