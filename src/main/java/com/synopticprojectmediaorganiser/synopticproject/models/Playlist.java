package com.synopticprojectmediaorganiser.synopticproject.models;

import javax.persistence.*;
import java.util.List;

@Entity(name = "playlist")
@Table(name = "playlist")
public class Playlist {

    public Playlist() {}

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String playlistname;

    @ManyToMany
    @JoinTable(
            name = "mediafile_playlist",
            joinColumns = @JoinColumn(name = "playlist_id"),
            inverseJoinColumns = @JoinColumn(name = "mediafile_id"))
    List<MediaFile> playlistFiles;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getplaylistname() {
        return playlistname;
    }

    public void setplaylistname(String name) {
        this.playlistname = name;
    }

    public List<MediaFile> getPlaylistFiles() {
        return playlistFiles;
    }

    public void setPlaylistFiles(List<MediaFile> playlistFiles) {
        this.playlistFiles = playlistFiles;
    }
}

