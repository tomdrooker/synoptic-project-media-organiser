package com.synopticprojectmediaorganiser.synopticproject.models;

import javax.persistence.*;

@Entity(name = "images")
public class Image {

    public Image() {
    }

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    private String image_path;

    @OneToOne(mappedBy = "image")
//    @JoinColumn(name = "id", referencedColumnName = "id")
    private MediaFile mediaFile;

    public Long getId() {
        return id;
    }

    public void setId(Long image_id) {
        this.id = image_id;
    }

    public String getImage_path() {
        return image_path;
    }

    public void setImage_path(String image_path) {
        this.image_path = image_path;
    }

    public MediaFile getMediaFile() {
        return mediaFile;
    }

    public void setMediaFile(MediaFile mediaFile) {
        this.mediaFile = mediaFile;
    }
}
