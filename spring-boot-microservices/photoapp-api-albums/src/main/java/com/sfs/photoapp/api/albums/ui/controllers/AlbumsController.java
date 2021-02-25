package com.sfs.photoapp.api.albums.ui.controllers;

import com.sfs.photoapp.api.albums.data.AlbumEntity;
import com.sfs.photoapp.api.albums.service.AlbumsService;
import com.sfs.photoapp.api.albums.ui.model.AlbumResponseModel;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping(value = "/users/{id}/albums",
        produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE},
        consumes = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
public class AlbumsController {

    @Autowired
    private AlbumsService albumsService;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @GetMapping
    public List<AlbumResponseModel> userAlbums(@PathVariable String id) {
        List<AlbumEntity> albumsEntities = albumsService.getAlbums(id);
        if (albumsEntities == null || albumsEntities.isEmpty()) {
            return Collections.emptyList();
        }

        Type listType = new TypeToken<List<AlbumResponseModel>>() {}.getType();
        List<AlbumResponseModel> albumResponseModels = new ModelMapper().map(albumsEntities, listType);
        logger.info("Returning " + albumResponseModels.size() + " albums");
        return albumResponseModels;
    }
}
