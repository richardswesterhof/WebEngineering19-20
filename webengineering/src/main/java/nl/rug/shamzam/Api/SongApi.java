package nl.rug.shamzam.Api;

import nl.rug.shamzam.Model.AddSong;
import nl.rug.shamzam.Model.Artist;
import nl.rug.shamzam.Model.Song;
import nl.rug.shamzam.Service.ArtistService;
import nl.rug.shamzam.Service.SongService;
import nl.rug.shamzam.Utils.HeaderSetter;
import nl.rug.shamzam.Utils.Unnullifier;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;


@RestController
@RequestMapping("api/songs")
public class SongApi {
    SongService songService;
    ArtistService artistService;

    public SongApi(SongService songService, ArtistService artistService){
        this.songService = songService;
        this.artistService = artistService;
    }

    @GetMapping("")
    public List<Song> getSongs(@RequestHeader(required = false) String accept,
                               @RequestParam(required = false) String title, @RequestParam(required = false) String artistId,
                               @RequestParam(required = false) String artistName, @RequestParam(required = false) Integer year,
                               @RequestParam(required = false) String genre, HttpServletResponse response) {
        response.setStatus(200);

        HeaderSetter.setContentType(accept, response);

        title = Unnullifier.unnullify(title);
        artistId = Unnullifier.unnullify(artistId);
        artistName = Unnullifier.unnullify(artistName);
        year = Unnullifier.unnullify(year);
        genre = Unnullifier.unnullify(genre);

        return songService.getSongsByParams(title, artistId, artistName, year, genre);
    }


    @GetMapping(value = "/{songId}", consumes = "text/csv", produces = "text/csv")
    public String getSongById(@PathVariable("songId") String songId, @RequestHeader String accept, HttpServletResponse response) {
        System.out.println("SERVING CSV");
        Song song = songService.getSongById(Integer.parseInt(songId));
        HeaderSetter.setContentType("text/csv", response);
        return song.toCsv();
    }

    @GetMapping(value = "/{songId}", produces = "application/json")
    public Song getSongById(@PathVariable("songId") String songId, HttpServletResponse response) {
        System.out.println("SERVING JSON");
        response.setStatus(200);


        return songService.getSongById(Integer.parseInt(songId));
    }


    @PostMapping("")
    public Song postSong(@RequestBody AddSong addSong, HttpServletResponse response, HttpServletRequest request) {
        //TODO: only json for now, add csv later
        HeaderSetter.setContentType("application/json", response);


        Artist artist = artistService.getArtistById(addSong.getArtistId());
        if(artist == null) {
            response.setStatus(500);
            return null;
        }
        Song song = songService.addSong(addSong.toSong(artist));
        response.setStatus(201);

        return song;
    }


    //TODO: implement put method
    @PutMapping("/{songId}")
    public Song putSong(@PathVariable("songId") String songId,
                        @RequestHeader(required = false) String accept, @RequestBody AddSong addSong,
                        HttpServletResponse response) {
        response.setStatus(200);

        Artist artist = artistService.getArtistById(addSong.getArtistId());

        Song song = songService.replaceSong(Integer.parseInt(songId), addSong.toSong(artist));

        HeaderSetter.setContentType("application/json", response);
        return song;
    }
}
