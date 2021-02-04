package com.synopticprojectmediaorganiser.synopticproject.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.synopticprojectmediaorganiser.synopticproject.models.MediaFile;
import com.synopticprojectmediaorganiser.synopticproject.models.Playlist;
import com.synopticprojectmediaorganiser.synopticproject.services.MediaFileService;
import com.synopticprojectmediaorganiser.synopticproject.services.PlaylistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.view.RedirectView;

import java.util.ArrayList;
import java.util.List;

@Controller
public class PlaylistsController {

    @Autowired
    private MediaFileService mediaFileService;

    @Autowired
    private PlaylistService playlistService;

    @GetMapping("/playlists")
    public String displayPlaylistsPage(Model model) {
        List<Playlist> playlists = playlistService.listAll();
        model.addAttribute("playlists", playlists);
        return "playlists";
    }

    @PostMapping("/save-playlist")
    public ResponseEntity<?> savePlaylist(@RequestBody String playlistJson) throws JsonProcessingException {

        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode node = objectMapper.readValue(playlistJson, ObjectNode.class);
        String playlistName = node.get("playlistname").asText();
        String fileIds = node.get("playlistFiles").toString();
        List<String> idList = objectMapper.readValue(fileIds, new TypeReference<List<String>>(){});
        List<MediaFile> mediaFileList = new ArrayList<>();

        for (String id : idList) {
            Long fileId = Long.parseLong(id);
            MediaFile file = mediaFileService.get(fileId);
            mediaFileList.add(file);
        }

        Playlist playlistToSave = new Playlist();

        playlistToSave.setplaylistname(playlistName);
        playlistToSave.setPlaylistFiles(mediaFileList);

        playlistService.save(playlistToSave);

        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping("/delete-playlist/{id}")
    public String deletePlaylist(@PathVariable("id") Long id) {
        playlistService.delete(id);
        return "redirect:/playlists";
    }

    @GetMapping("/view-playlist/{id}")
    public String displayViewPlaylist(@PathVariable("id") Long id, Model model) {
        Playlist playlist = playlistService.get(id);
        List<MediaFile> playlistFiles = playlistService.get(id).getPlaylistFiles();
        List<MediaFile> mediaFileList = mediaFileService.listAll();
        model.addAttribute("playlistFiles", playlistFiles);
        model.addAttribute("playlist", playlist);
        model.addAttribute("mediaFileList", mediaFileList);
        return "view-playlist";
    }

    @GetMapping("/delete-playlist-file/{fileId}/{playlistId}")
    public RedirectView deletePlaylistFile(@PathVariable Long fileId,
                                           @PathVariable Long playlistId) {

        //Get playlist
        Playlist playlist = playlistService.get(playlistId);
        //Get files in playlist
        List<MediaFile> playlistFiles = playlist.getPlaylistFiles();

        for (int i = 0; i < playlistFiles.size(); i++) {
            if (playlistFiles.get(i).getId().equals(fileId)) {
                playlistFiles.remove(playlistFiles.get(i));
            }
        }

        playlist.setPlaylistFiles(playlistFiles);
        playlistService.save(playlist);

        return new RedirectView("/view-playlist/{playlistId}");
    }

    @PostMapping("/save-changes-to-playlist")
    public ResponseEntity<?> saveChangesToPlaylist(@RequestBody String playlistJson) throws JsonProcessingException {

        // Get JSON from form
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode node = objectMapper.readValue(playlistJson, ObjectNode.class);

        //Parse JSON
        String fileIds = node.get("playlistFiles").toString();
        String playlistIdString = node.get("playlistId").toString().replaceAll("^\"+|\"+$", "");
        Long playlistId = Long.parseLong(String.valueOf(playlistIdString));
        String playlistName = playlistService.get(playlistId).getplaylistname();
        Playlist newPlaylist = new Playlist();
        List<MediaFile> existingMediaFiles = playlistService.get(playlistId).getPlaylistFiles();

        // Get list of media files to include in the playlist
        List<String> idList = objectMapper.readValue(fileIds, new TypeReference<List<String>>() {});
        List<MediaFile> mediaFileList = new ArrayList<>();

        for (String id : idList) {
            Long fileId = Long.parseLong(id);
            MediaFile file = mediaFileService.get(fileId);
            if (!existingMediaFiles.contains(file)) {
                existingMediaFiles.add(file);
            }
        }

        newPlaylist.setplaylistname(playlistName);
        newPlaylist.setPlaylistFiles(existingMediaFiles);
        playlistService.delete(playlistId);
        playlistService.save(newPlaylist);

        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping("/view-playlist/rename-playlist/{playlistId}")
    public String displayRenameCategory(@PathVariable Long playlistId,
                                        Model model) {
        Playlist playlist = playlistService.get(playlistId);
        model.addAttribute("playlist", playlist);
        return "rename-playlist";
    }

    @PostMapping("/rename-playlist/{playlistId}")
    public RedirectView renameCategory(@PathVariable Long playlistId,
                                       WebRequest request) {
        Playlist playlist = playlistService.get(playlistId);
        playlist.setplaylistname(request.getParameter("newName"));
        playlistService.save(playlist);
        return new RedirectView("/view-playlist/" + playlistId);
    }

}
