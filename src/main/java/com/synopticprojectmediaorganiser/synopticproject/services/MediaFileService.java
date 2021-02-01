package com.synopticprojectmediaorganiser.synopticproject.services;

import com.synopticprojectmediaorganiser.synopticproject.models.MediaFile;
import com.synopticprojectmediaorganiser.synopticproject.repositories.MediaFileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MediaFileService {

    @Autowired
    private MediaFileRepository mediaFileRepository;

    public List<MediaFile> listAll() {return mediaFileRepository.findAll();}

    public void save(MediaFile mediaFile) {
        mediaFileRepository.save(mediaFile);
    }

    public MediaFile get(Long id) {
        return mediaFileRepository.findById(id).get();
    }

    public void delete(Long id) {
        mediaFileRepository.deleteById(id);
    }

}

