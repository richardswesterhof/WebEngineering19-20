package nl.rug.shamzam.Api;

import nl.rug.shamzam.Model.outsideModels.SongRequestBody;
import nl.rug.shamzam.Model.Artist;
import nl.rug.shamzam.Model.Song;
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


    @GetMapping(value = "", produces = csv)
    public String getSongsCsv(@RequestParam(required = false) String title, @RequestParam(required = false) Integer artistId,
                              @RequestParam(required = false) String artistName, @RequestParam(required = false) Integer year,
                              @RequestParam(required = false) String genre,
                              @RequestParam(required = false) Integer pageSize, @RequestParam(required = false) Integer pageRank, HttpServletResponse response) {
        response.setStatus(HttpServletResponse.SC_OK);
        response.setHeader(HttpHeaders.CONTENT_TYPE, csv);

        List<Song> queryResults = getSongList(title, artistId, artistName, year, genre,pageSize,pageRank);
        StringBuilder fullCsv = new StringBuilder(Song.columnNames);
        for(int i = 0; i < queryResults.size(); i++) {
            fullCsv.append('\n');
            fullCsv.append(queryResults.get(i).toCsvLine());
        }
        return fullCsv.toString();
    }


    @GetMapping("") //produces application/json, but we don't specify it so it defaults here even if another representation was requested
    public List<Song> getSongsJson(@RequestParam(required = false) String title, @RequestParam(required = false) Integer artistId,
                                   @RequestParam(required = false) String artistName, @RequestParam(required = false) Integer year,
                                   @RequestParam(required = false) String genre,
                                   @RequestParam(required = false) Integer pageSize, @RequestParam(required = false) Integer pageRank, HttpServletResponse response) {
        response.setStatus(HttpServletResponse.SC_OK);
        response.setHeader(HttpHeaders.CONTENT_TYPE, json);

        return getSongList(title,artistId,artistName,year,genre,pageSize,pageRank);
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
    public List<Song> getSongList(String title, Integer artistId, String artistName, Integer year, String genre, Integer pageSize, Integer pageRank) {
        title = Unnullifier.unnullify(title);
        artistId = Unnullifier.unnullify(artistId);
        artistName = Unnullifier.unnullify(artistName);
        year = Unnullifier.unnullify(year);
        genre = Unnullifier.unnullify(genre);
        pageRank = Unnullifier.unnullify(pageRank);
        
        if(pageSize == null || pageSize == 0)
            pageSize = 50;

        return songService.getSongsByParams(title, artistId, artistName, year, genre, pageSize,pageRank);
    }


    @GetMapping(value = "/{songId}", produces = csv)
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


    @PostMapping(value = "", consumes = json, produces = csv)
    public String postSongCsv(@RequestBody SongRequestBody songRequestBody, HttpServletResponse response) {
        response.setHeader(HttpHeaders.CONTENT_TYPE, csv);
        response.setStatus(HttpServletResponse.SC_CREATED);

        Song song = addSongToDb(songRequestBody);
        if(song == null) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return "";
        }
        else {
            response.setHeader(HttpHeaders.LOCATION, URI_BASE + song.getSongid());
        }
        return Song.columnNames + '\n' + song.toCsvLine();
    }


    @PostMapping(value = "", consumes = json)
    public Song postSongJson(@RequestBody SongRequestBody songRequestBody, HttpServletResponse response) {
        response.setHeader(HttpHeaders.CONTENT_TYPE, json);
        response.setStatus(HttpServletResponse.SC_CREATED);

        if(songRequestBody.getArtistId() == null) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return null;
        }
        Song song = addSongToDb(songRequestBody);
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
     * @param songRequestBody the song to be added in a 'raw' state, i.e. the way it gets posted
     * @return the full Song object if it was added successfully, else null
     */
    private Song addSongToDb(SongRequestBody songRequestBody) {
        Optional<Artist> a = artistService.getArtistById(songRequestBody.getArtistId());
        if(!a.isPresent()) return null;
        return songService.addSong(songRequestBody.toSong(a.get()));
    }


    @PutMapping(value = "/{songId}", consumes = json, produces = csv)
    public String putSongCsv(@PathVariable("songId") String songId, @RequestBody SongRequestBody songRequestBody, HttpServletResponse response) {
        response.setHeader(HttpHeaders.CONTENT_TYPE, csv);
        response.setStatus(HttpServletResponse.SC_OK);
        int id;

        try {
            id = Integer.parseInt(songId);
        } catch(NumberFormatException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return "";
        }

        Song song = replaceSongInDb(id, songRequestBody);
        if(song == null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return "";
        }

        return Song.columnNames + '\n' + song.toCsvLine();
    }

    @PutMapping(value = "/{songId}", consumes = json)
    public Song putSongJson(@PathVariable("songId") String songId, @RequestBody SongRequestBody songRequestBody, HttpServletResponse response) {
        response.setHeader(HttpHeaders.CONTENT_TYPE, json);
        response.setStatus(HttpServletResponse.SC_OK);
        int id;

        try {
            id = Integer.parseInt(songId);
        } catch(NumberFormatException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return null;
        }

        Song song = replaceSongInDb(id, songRequestBody);
        if(song == null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }

        return song;
    }


    @GetMapping(value = "/popularity", produces = csv)
    public String getSongsPopularityCsv(@RequestParam(required = false) Integer year, @RequestParam(required = false) Integer pageSize,
                                     @RequestParam(required = false) Integer pageRank, HttpServletResponse response) {
        response.setHeader("Content-Type", csv);
        response.setStatus(HttpServletResponse.SC_OK);

        if(pageRank == null)
            pageRank = 0;

        if(pageSize == null || pageSize == 0)
            pageSize = 50;

        StringBuilder sb = new StringBuilder(Song.columnNames);
        List<Song> songPopularities = songService.getSongsByPopularityYear(year,pageSize,pageRank);

        for(Song sp: songPopularities) {
            sb.append('\n');
            sb.append(sp.toCsvLine());
        }
        return sb.toString();
    }

    @GetMapping(value = "/popularity")
    public List<Song> getSongsPopularityJson(@RequestParam(required = false) Integer year, @RequestParam(required = false) Integer pageSize,
                                                      @RequestParam(required = false) Integer pageRank, HttpServletResponse response) {
        response.setHeader("Content-Type", json);
        response.setStatus(HttpServletResponse.SC_OK);

        pageRank = Unnullifier.unnullify(pageRank);

        if(pageSize == null || pageSize == 0)
            pageSize = 50;

        return songService.getSongsByPopularityYear(year,pageSize,pageRank);
    }

    /**
     * used as a helper function for methods that need to replace songs in the database
     * @param songId the id of the song to be replaced
     * @param songRequestBody the new version of the song in a 'raw' format, i.e. the way it is in the request
     * @return the full updated song if it was successfully updated, else null
     */
    private Song replaceSongInDb(int songId, SongRequestBody songRequestBody) {
        //find the artist in the database by their given id
        Optional<Artist> a = artistService.getArtistById(songRequestBody.getArtistId());
        if(!a.isPresent()) return null;
        return songService.replaceSong(songId, songRequestBody.toSong(a.get()));
    }
}
