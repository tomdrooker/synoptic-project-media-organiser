package com.synopticprojectmediaorganiser.synopticproject.models;

import javax.persistence.*;


@Entity(name = "category")
public class Category {

    public Category() {};

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String categoryname;

    @ManyToOne
    @JoinColumn(name="mediafile_id")
    private MediaFile mediaFile;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCategoryName() {
        return categoryname;
    }

    public void setCategoryName(String name) {
        this.categoryname = name;
    }

    public MediaFile getMediaFile() {
        return mediaFile;
    }

    public void setMediaFile(MediaFile mediaFile) {
        this.mediaFile = mediaFile;
    }
}
