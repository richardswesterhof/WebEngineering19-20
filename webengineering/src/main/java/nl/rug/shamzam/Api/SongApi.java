package nl.rug.shamzam.Api;

import nl.rug.shamzam.Model.outsideModels.AddSong;
import nl.rug.shamzam.Model.Artist;
import nl.rug.shamzam.Model.Song;
import nl.rug.shamzam.Model.outsideModels.SongPopularity;
import nl.rug.shamzam.Service.ArtistService;
import nl.rug.shamzam.Service.SongService;
import nl.rug.shamzam.Utils.Unnullifier;

import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;


import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Optional;


@CrossOrigin
@RestController
@RequestMapping("api/songs")
public class SongApi {
    SongService songService;
    ArtistService artistService;

    private static final String URI_BASE = "/api/songs/";
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

        List<Song> queryResults = getSongList(title, artistId, artistName, year, genre);
        StringBuilder fullCsv = new StringBuilder(Song.columnNames);
        fullCsv.append('\n');
        for(int i = 0; i < queryResults.size(); i++) {
            fullCsv.append(queryResults.get(i).toCsvLine());
            fullCsv.append('\n');
        }
        return fullCsv.toString();
    }


    @GetMapping("") //produces application/json, but we don't specify it so it defaults here even if another representation was requested
    public List<Song> getSongsJson(@RequestParam(required = false) String title, @RequestParam(required = false) Integer artistId,
                                   @RequestParam(required = false) String artistName, @RequestParam(required = false) Integer year,
                                   @RequestParam(required = false) String genre, HttpServletResponse response) {
        response.setStatus(HttpServletResponse.SC_OK);
        response.setHeader(HttpHeaders.CONTENT_TYPE, json);

        return getSongList(title,artistId,artistName,year,genre);
    }


    /**
     * used as a helper function for the methods that handle get songs requests
     * @param title title of the song
     * @param artistId the id of the artist
     * @param artistName the name of the artist
     * @param year the year of release
     * @param genre the genre of the artist
     * @return the list of songs matching the parameters
     */
    public List<Song> getSongList(String title, Integer artistId, String artistName, Integer year, String genre) {
        title = Unnullifier.unnullify(title);
        artistId = Unnullifier.unnullify(artistId);
        artistName = Unnullifier.unnullify(artistName);
        year = Unnullifier.unnullify(year);
        genre = Unnullifier.unnullify(genre);

        return songService.getSongsByParams(title, artistId, artistName, year, genre);
    }


    @GetMapping(value = "/{songId}", produces = "text/csv")
    public String getSongByIdCsv(@PathVariable("songId") int songId, HttpServletResponse response) {
        response.setHeader(HttpHeaders.CONTENT_TYPE, csv);
        response.setStatus(HttpServletResponse.SC_OK);

        Song song = songService.getSongById(songId);
        if(song == null) {
            return "";
        }

        return Song.columnNames + '\n' + song.toCsvLine();
    }


    @GetMapping("/{songId}")
    public Song getSongByIdJson(@PathVariable("songId") int songId, HttpServletResponse response) {
        response.setHeader(HttpHeaders.CONTENT_TYPE, json);
        response.setStatus(HttpServletResponse.SC_OK);

        return songService.getSongById(songId);
    }


    @PostMapping(value = "", consumes = "application/json", produces = "text/csv")
    public String postSongCsv(@RequestBody AddSong addSong, HttpServletResponse response) {
        response.setHeader(HttpHeaders.CONTENT_TYPE, csv);
        response.setStatus(HttpServletResponse.SC_OK);

        Song song = addSongToDb(addSong);
        if(song == null) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return "";
        }
        else {
            response.setHeader(HttpHeaders.LOCATION, URI_BASE + song.getSongid());
        }
        return Song.columnNames + '\n' + song.toCsvLine();
    }


    @PostMapping(value = "", consumes = "application/json")
    public Song postSongJson(@RequestBody AddSong addSong, HttpServletResponse response) {
        response.setHeader(HttpHeaders.CONTENT_TYPE, json);
        response.setStatus(HttpServletResponse.SC_CREATED);

        Song song = addSongToDb(addSong);
        if(song == null) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
        else {
            response.setHeader(HttpHeaders.LOCATION, URI_BASE + song.getSongid());
        }

        return song;
    }


    /**
     * used as a helper function for the methods that need to add a song
     * @param addSong the song to be added in a 'raw' state, i.e. the way it gets posted
     * @return the full Song object if it was added successfully, else null
     */
    private Song addSongToDb(AddSong addSong) {
        Optional<Artist> a = artistService.getArtistById(addSong.getArtistId());
        if(!a.isPresent()) return null;
        return songService.addSong(addSong.toSong(a.get()));
    }


    @PutMapping(value = "/{songId}", consumes = "application/json", produces = "text/csv")
    public String putSongCsv(@PathVariable("songId") String songId, @RequestBody AddSong addSong, HttpServletResponse response) {
        response.setHeader(HttpHeaders.CONTENT_TYPE, csv);
        response.setStatus(HttpServletResponse.SC_OK);
        int id;

        try {
            id = Integer.parseInt(songId);
        } catch(NumberFormatException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return "";
        }

        Song song = replaceSongInDb(id, addSong);
        if(song == null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return "";
        }

        return Song.columnNames + '\n' + song.toCsvLine();
    }

    @GetMapping(value = "/popularity")
    public List<SongPopularity> getArtistsHotness(@RequestParam(required = false) Integer year, @RequestParam(required = false) Integer pageSize, @RequestParam(required = false) Integer pageRank, HttpServletResponse response) {
        response.setHeader("Content-Type", "application/json");
        response.setStatus(HttpServletResponse.SC_OK);

        if(pageRank == null)
            pageRank = 0;

        if(pageSize == null || pageSize == 0)
            pageSize = 50;

        List<SongPopularity> songPopularities;
        if(year == null){
            songPopularities = songService.getSongPopularity (pageSize,pageRank);
        }else{
            songPopularities = songService.getSongPopularityYear (year,pageSize,pageRank);
        }
        return songPopularities;
    }

    @GetMapping(value = "/popularity", consumes = "text/csv")
    public String getSongsHotnessCsv(@RequestParam(required = false) Integer year, @RequestParam(required = false) Integer pageSize, @RequestParam(required = false) Integer pageRank, HttpServletResponse response) {
        response.setHeader("Content-Type", "text/csv");
        response.setStatus(HttpServletResponse.SC_OK);

        if(pageRank == null)
            pageRank = 0;

        if(pageSize == null || pageSize == 0)
            pageSize = 50;

        String s = SongPopularity.columnames;
        List<SongPopularity> songPopularities;
        if(year == null){
            songPopularities = songService.getSongPopularity (pageSize,pageRank);
        }else{
            songPopularities = songService.getSongPopularityYear (year,pageSize,pageRank);
        }

        for (SongPopularity sp: songPopularities) {
            s +=  sp.toCsvLine();
        }
        return s;
    }

    @PutMapping(value = "/{songId}", consumes = "application/json")
    public Song putSongJson(@PathVariable("songId") String songId, @RequestBody AddSong addSong, HttpServletResponse response) {
        response.setHeader(HttpHeaders.CONTENT_TYPE, json);
        response.setStatus(HttpServletResponse.SC_OK);
        int id;

        try {
            id = Integer.parseInt(songId);
        } catch(NumberFormatException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return null;
        }

        Song song = replaceSongInDb(id, addSong);
        if(song == null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }

        return song;
    }


    /**
     * used as a helper function for methods that need to replace songs in the database
     * @param songId the id of the song to be replaced
     * @param addSong the new version of the song in a 'raw' format, i.e. the way it is in the request
     * @return the full updated song if it was successfully updated, else null
     */
    private Song replaceSongInDb(int songId, AddSong addSong) {
        //find the artist in the database by their given id
        Optional<Artist> a = artistService.getArtistById(addSong.getArtistId());
        if(!a.isPresent()) return null;
        return songService.replaceSong(songId, addSong.toSong(a.get()));
    }
}
