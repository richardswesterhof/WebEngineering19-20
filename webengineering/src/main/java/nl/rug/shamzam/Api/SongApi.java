package nl.rug.shamzam.Api;

import nl.rug.shamzam.Model.AddSong;
import nl.rug.shamzam.Model.Artist;
import nl.rug.shamzam.Model.Song;
import nl.rug.shamzam.Service.ArtistService;
import nl.rug.shamzam.Service.SongService;
import nl.rug.shamzam.Utils.Unnullifier;

import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;


@RestController
@RequestMapping("api/songs")
public class SongApi {
    SongService songService;
    ArtistService artistService;

    private static final String json = "application/json";
    private static final String csv = "text/csv";

    public SongApi(SongService songService, ArtistService artistService){
        this.songService = songService;
        this.artistService = artistService;
    }

    @GetMapping(value = "", produces = "text/csv")
    public String getSongsCsv(@RequestParam(required = false) String title, @RequestParam(required = false) Integer artistId,
                              @RequestParam(required = false) String artistName, @RequestParam(required = false) Integer year,
                              @RequestParam(required = false) String genre, HttpServletResponse response) {
        response.setStatus(HttpServletResponse.SC_OK);
        response.setHeader(HttpHeaders.CONTENT_TYPE, csv);

        return "CSV NOT SUPPORTED YET";
    }

    @GetMapping("") //produces application/json, but we don't specify it so it defaults here even if another representation was requested
    public List<Song> getSongsJson(@RequestParam(required = false) String title, @RequestParam(required = false) Integer artistId,
                                   @RequestParam(required = false) String artistName, @RequestParam(required = false) Integer year,
                                   @RequestParam(required = false) String genre, HttpServletResponse response) {
        response.setStatus(HttpServletResponse.SC_OK);
        response.setHeader(HttpHeaders.CONTENT_TYPE, json);

        title = Unnullifier.unnullify(title);
        artistId = Unnullifier.unnullify(artistId);
        artistName = Unnullifier.unnullify(artistName);
        year = Unnullifier.unnullify(year);
        genre = Unnullifier.unnullify(genre);

        List<Song> queryResults = songService.getSongsByParams(title, artistId, artistName, year, genre);
        if(queryResults == null || queryResults.isEmpty()) {
            response.setStatus(HttpServletResponse.SC_NO_CONTENT);
        }

        return queryResults;
    }


    @GetMapping(value = "/{songId}", produces = "text/csv")
    public String getSongByIdCsv(@PathVariable("songId") String songId, HttpServletResponse response) {
        response.setHeader(HttpHeaders.CONTENT_TYPE, csv);
        response.setStatus(HttpServletResponse.SC_OK);

        Song song = songService.getSongById(Integer.parseInt(songId));
        if(song == null) {
            response.setStatus(HttpServletResponse.SC_NO_CONTENT);
            return "";
        }

        return song.toCsv();
    }


    @GetMapping("/{songId}")
    public Song getSongByIdJson(@PathVariable("songId") String songId, HttpServletResponse response) {
        response.setHeader(HttpHeaders.CONTENT_TYPE, json);
        response.setStatus(HttpServletResponse.SC_OK);

        Song queryResult = songService.getSongById(Integer.parseInt(songId));
        if(queryResult == null) {
            response.setStatus(HttpServletResponse.SC_NO_CONTENT);
        }

        return queryResult;
    }


    @PostMapping(value = "", consumes = "application/json", produces = "text/csv")
    public String postSongCsv(@RequestBody AddSong addSong, HttpServletResponse response, HttpServletRequest request) {
        response.setHeader(HttpHeaders.CONTENT_TYPE, csv);
        response.setHeader(HttpHeaders.LOCATION, "NOWHERE");
        response.setStatus(HttpServletResponse.SC_OK);

        return "CSV NOT SUPPORTED YET, NO CHANGES MADE";
    }


    @PostMapping(value = "", consumes = "application/json")
    public Song postSongJson(@RequestBody AddSong addSong, HttpServletResponse response, HttpServletRequest request) {
        response.setHeader(HttpHeaders.CONTENT_TYPE, json);
        response.setStatus(HttpServletResponse.SC_CREATED);

        Artist artist = artistService.getArtistById(addSong.getArtistId());
        if(artist == null) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return null;
        }

        Song song = songService.addSong(addSong.toSong(artist));
        if(song == null) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
        else {
            response.setHeader(HttpHeaders.LOCATION, "/api/songs/" + song.getSongid());
        }

        return song;
    }


    @PutMapping(value = "/{songId}", consumes = "application/json", produces = "text/csv")
    public String putSongCsv(@PathVariable("songId") String songId, @RequestBody AddSong addSong, HttpServletResponse response) {
        response.setHeader(HttpHeaders.CONTENT_TYPE, csv);
        response.setStatus(HttpServletResponse.SC_OK);

        return "CSV NOT SUPPORTED YET, NO CHANGES MADE";
    }


    @PutMapping(value = "/{songId}", consumes = "application/json")
    public Song putSongJson(@PathVariable("songId") String songId, @RequestBody AddSong addSong, HttpServletResponse response) {
        response.setHeader(HttpHeaders.CONTENT_TYPE, json);
        response.setStatus(HttpServletResponse.SC_OK);

        //find the artist in the database by their given id
        Artist artist = artistService.getArtistById(addSong.getArtistId());
        if(artist == null) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            try {
                response.getWriter().write("{\n\t\"error\": \"Internal Server Error: No artist was found for id: " + addSong.getArtistId() +
                        ". Therefore no changes were made to the database\"\n}");
            } catch(IOException e) {
                System.err.println("IOException occurred when failed to get artist while putting song");
            }
            return null;
        }

        Song song = songService.replaceSong(Integer.parseInt(songId), addSong.toSong(artist));
        if(song == null) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }

        return song;
    }
}
