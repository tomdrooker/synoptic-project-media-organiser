package com.synopticprojectmediaorganiser.synopticproject.services;

import com.synopticprojectmediaorganiser.synopticproject.models.Playlist;
import com.synopticprojectmediaorganiser.synopticproject.repositories.PlaylistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlaylistService {

    @Autowired
    private PlaylistRepository playlistRepository;

    public List<Playlist> listAll() {return playlistRepository.findAll();}

    public void save(Playlist playlist) {
        playlistRepository.save(playlist);
    }

    public Playlist get(Long id) {
        return playlistRepository.findById(id).get();
    }

    public void delete(Long id) {
        playlistRepository.deleteById(id);
    }

}

