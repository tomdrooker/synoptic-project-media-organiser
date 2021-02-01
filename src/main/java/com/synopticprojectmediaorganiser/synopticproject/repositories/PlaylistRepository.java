package com.synopticprojectmediaorganiser.synopticproject.repositories;


import com.synopticprojectmediaorganiser.synopticproject.models.Playlist;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlaylistRepository extends JpaRepository<Playlist, Long> {
}
