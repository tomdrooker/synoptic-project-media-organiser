package com.synopticprojectmediaorganiser.synopticproject.repositories;

import com.synopticprojectmediaorganiser.synopticproject.models.MediaFile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MediaFileRepository extends JpaRepository<MediaFile, Long> {
}
