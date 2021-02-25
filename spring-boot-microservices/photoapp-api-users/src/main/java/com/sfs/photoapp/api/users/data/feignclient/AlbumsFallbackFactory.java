package com.sfs.photoapp.api.users.data.feignclient;

import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

@Component
public class AlbumsFallbackFactory implements FallbackFactory<AlbumsServiceClient> {

    @Override
    public AlbumsServiceClient create(Throwable cause) {
        return new AlbumsServiceClientFallBack(cause);
    }
}
