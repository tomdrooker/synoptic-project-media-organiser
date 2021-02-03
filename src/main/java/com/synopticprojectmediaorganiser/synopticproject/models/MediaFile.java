package com.synopticprojectmediaorganiser.synopticproject.models;

import javax.persistence.*;
import java.util.List;

@Entity(name = "mediafile")
public class MediaFile {

    public MediaFile() {}

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String path;
    private String type;
    private String comment;

    @ManyToMany(mappedBy = "playlistFiles")
    private List<Playlist> playlists;

    @OneToMany(mappedBy="mediaFile")
    private List<Category> categories;

    @OneToOne(cascade = CascadeType.ALL)
    private Image image;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<Playlist> getPlaylists() {
        return playlists;
    }

    public void setPlaylists(List<Playlist> playlists) {
        this.playlists = playlists;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    public String formatCategories() {
        StringBuilder sb = new StringBuilder();

        for (Category cat : getCategories()) {
            sb.append(cat.getCategoryName());
        }

        return sb.toString();

    }
}
