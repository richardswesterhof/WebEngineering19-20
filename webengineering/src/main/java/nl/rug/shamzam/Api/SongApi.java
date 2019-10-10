package nl.rug.shamzam.Api;

import nl.rug.shamzam.Model.Song;
import nl.rug.shamzam.Service.SongService;
import nl.rug.shamzam.Utils.HeaderSetter;
import nl.rug.shamzam.Utils.Unnullifier;

import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
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


    @GetMapping("/{songId}")
    public Song getSongById(@PathVariable("songId") String songId,
                            @RequestHeader(required = false) String accept, HttpServletResponse response) {
        response.setStatus(200);

        HeaderSetter.setContentType(accept, response);
        return songService.getSongById(songId);
    }


    //TODO: implement post method
    @PostMapping("")
    public Song postSong(@RequestHeader(required = false) String accept, HttpServletResponse response) {
        response.setStatus(200);

        HeaderSetter.setContentType(accept, response);
        return new Song();
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
