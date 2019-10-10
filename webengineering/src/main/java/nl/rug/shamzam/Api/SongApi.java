package nl.rug.shamzam.Api;

import nl.rug.shamzam.Model.AddSong;
import nl.rug.shamzam.Model.Song;
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

    public SongApi(SongService songService){
        this.songService = songService;
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
    public void getSongById(@PathVariable("songId") String songId, @RequestHeader String accept, HttpServletResponse response) {
        System.out.println("SERVING CSV");
        Song song = songService.getSongById(songId);
        HeaderSetter.setContentType("text/csv", response);
        if(accept.toLowerCase().contains("text/csv")) {
            try {
                response.getWriter().write(song.toCsv());
                System.out.println("Song data written to response");
            } catch(IOException e) {
                System.err.println("Exception occurred while handling get song by id request:");
                e.printStackTrace();
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "The song data could not be written to the response body");
            }
        }
    }

    @GetMapping(value = "/{songId}", produces = "application/json")
    public Song getSongById(@PathVariable("songId") String songId, HttpServletResponse response) {
        System.out.println("SERVING JSON");
        response.setStatus(200);


        return songService.getSongById(songId);
    }


    @PostMapping("")
    public Song postSong(@RequestBody AddSong addSong, HttpServletResponse response, HttpServletRequest request) {
        Song song = songService.addSong(addSong.toSong());
        response.setStatus(201);
        //TODO: only json for now, add csv later
        HeaderSetter.setContentType("application/json", response);
        return song;
    }


    //TODO: implement put method
    @PutMapping("/{songId}")
    public Song putSong(@RequestHeader(required = false) String accept,
                        @PathVariable("songId") String songId, HttpServletResponse response) {
        response.setStatus(200);

        HeaderSetter.setContentType(accept, response);
        return new Song();
    }
}
