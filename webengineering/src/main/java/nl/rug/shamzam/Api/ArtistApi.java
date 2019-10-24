package nl.rug.shamzam.Api;

import nl.rug.shamzam.Model.Song;
import nl.rug.shamzam.Model.outsideModels.*;
import nl.rug.shamzam.Service.ArtistService;
import nl.rug.shamzam.Model.Artist;
import nl.rug.shamzam.Utils.Unnullifier;
import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotNull;
import java.util.*;

import static org.springframework.http.HttpHeaders.CONTENT_TYPE;


@CrossOrigin
@RestController
@RequestMapping("api/artists")
public class ArtistApi {
    ArtistService artistService;

    public ArtistApi(ArtistService ars){
        artistService = ars;
    }

    private static final String URI_BASE = "/api/artists/";
    private static final String json = "application/json";
    private static final String csv = "text/csv";


    @GetMapping(value = "/{artistId}")
    public Artist getArtist(@PathVariable int artistId,  HttpServletResponse response) {
        response.setHeader(CONTENT_TYPE, json);
        response.setStatus(HttpServletResponse.SC_OK);


        Optional<Artist> a = artistService.getArtistById(artistId);
        if(!a.isPresent()){
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return null;
        }
        return a.get();
    }


    @GetMapping(value = "")
    public List<Artist> getArtists(@RequestParam(required = false) String artistName, @RequestParam(required = false) String genre,
                                   @RequestParam(required = false) Integer pageSize, @RequestParam(required = false) Integer pageRank,
                                   HttpServletResponse response) {
        response.setHeader(CONTENT_TYPE, json);
        response.setStatus(HttpServletResponse.SC_OK);

        artistName = Unnullifier.unnullify(artistName);
        genre = Unnullifier.unnullify(genre);
        pageRank = Unnullifier.unnullify(pageRank);

        if(pageSize == null || pageSize == 0)
            pageSize = 50;

        return artistService.getArtistsByNameAndGenre(artistName,genre,pageSize,pageRank);
    }


    @GetMapping(value = "/{artistId}/statistics")
    public ArtistStatistics getArtistStatistics(@PathVariable int artistId,
                                                @RequestParam(required = false) Integer year, HttpServletResponse response) {

        Optional<Artist> a = artistService.getArtistById(artistId);
        if(!a.isPresent()){
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return null;
        }

        List<Song> songs = a.get().getSongs();
        if(songs.size() == 0){
            return new ArtistStatistics(artistId, 0, 0, 0);
        }
        if(year != null) {
            //removing the songs this way prevents a ConcurrentModificationException
            songs.removeIf((Song song) -> song.getYear() != year);
        }

        DescriptiveStatistics ds = new DescriptiveStatistics();
        ArrayList<Float> floats = new ArrayList<>();
        for (Song s: songs) {
            ds.addValue(s.getHotness());
            floats.add(s.getHotness());
        }

        Arrays.sort(floats.toArray());
        float median;
        if (floats.size() % 2 == 0)
            median = (floats.get(floats.size()/2) + floats.get(floats.size()/2 - 1))/2;
        else
            median = floats.get(floats.size()/2);

        ArtistStatistics as = new ArtistStatistics(artistId, (float) ds.getMean(), median,(float)ds.getStandardDeviation());



        response.setStatus(HttpServletResponse.SC_OK);
        response.setHeader(CONTENT_TYPE, json);
        return as;
    }


    @GetMapping(value = "/popularity")
    public List<Artist> getArtistsByHotness(@RequestParam(required = false) Integer pageSize,
                                            @RequestParam(required = false) Integer pageRank, HttpServletResponse response) {
        response.setHeader(CONTENT_TYPE, json);
        response.setStatus(HttpServletResponse.SC_OK);

        pageRank = Unnullifier.unnullify(pageRank);

        if(pageSize == null || pageSize == 0)
            pageSize = 50;

        return artistService.getArtistsByHotness(pageSize,pageRank);
    }


    @PostMapping(value = "")
    public Artist postArtist(@RequestBody ArtistRequestBody addArtist, HttpServletResponse response) {

        Artist a = artistService.save(new Artist(addArtist));
        if(a == null) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return null;
        }
        else {
            response.setHeader(HttpHeaders.LOCATION, URI_BASE + a.getArtistid());
        }
        response.setStatus(HttpServletResponse.SC_CREATED);
        return a;
    }


    @DeleteMapping(value = "/{artistId}")
    public void deleteArtist(@PathVariable int artistId, HttpServletResponse response){
        if(!artistService.delete(artistId)){
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        response.setStatus(HttpServletResponse.SC_NO_CONTENT);
    }


    @PutMapping(value = "/{artistId}")
    public Artist updateArtist(@PathVariable int artistId, @NotNull @RequestBody ArtistRequestBody artistRequestBody, HttpServletResponse response){
        response.setStatus(HttpServletResponse.SC_OK);
        response.setHeader(HttpHeaders.LOCATION, URI_BASE + artistId);

        Optional<Artist> a = artistService.getArtistById(artistId);

        if(!a.isPresent()){
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return null;
        }

        Artist artist = a.get();
        artist.update(artistRequestBody);
        return artistService.save(artist);
    }


    //CSV methods
    @GetMapping(value = "/{artistId}", produces = csv)
    public String getArtistCsv(@PathVariable int artistId,  HttpServletResponse response) {
        response.setHeader(CONTENT_TYPE, csv);
        response.setStatus(HttpServletResponse.SC_OK);

        Optional<Artist> a = artistService.getArtistById(artistId);
        if(!a.isPresent()){
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return null;
        }

        return Artist.columnNames + '\n' + a.get().toCsvLine();
    }


    @GetMapping(value = "", produces = csv)
    public String getArtistsCsv(@RequestParam(required = false) String artistName, @RequestParam(required = false) String genre,
                                @RequestParam(required = false) Integer pageSize, @RequestParam(required = false) Integer pageRank, HttpServletResponse response) {
        response.setHeader(CONTENT_TYPE, csv);
        response.setStatus(HttpServletResponse.SC_OK);

        artistName = Unnullifier.unnullify(artistName);
        genre = Unnullifier.unnullify(genre);
        pageRank = Unnullifier.unnullify(pageRank);

        if(pageSize == null || pageSize == 0)
            pageSize = 50;

        StringBuilder sb = new StringBuilder(Artist.columnNames);

        List<Artist> artists = artistService.getArtistsByNameAndGenre(artistName,genre, pageSize,pageRank);

        for (Artist a: artists) {
            sb.append('\n');
            sb.append(a.toCsvLine());
        }

        return sb.toString();
    }


    @GetMapping(value = "/{artistId}/statistics", produces = csv)
    public String getArtistStatisticsCsv(@PathVariable int artistId, @RequestParam(required = false) Integer year, HttpServletResponse response) {
        response.setStatus(HttpServletResponse.SC_OK);
        response.setHeader(CONTENT_TYPE, csv);

        Optional<Artist> a = artistService.getArtistById(artistId);
        if(!a.isPresent()){
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return null;
        }

        List<Song> songs = a.get().getSongs();

        if(songs.isEmpty()) {
            return ArtistStatistics.columnNames + new ArtistStatistics(artistId, 0, 0, 0).toCsvLine();
        }

        if(year != null) {
            for (Song s: songs) {
                if(s.getYear() != year)
                    songs.remove(s);
            }
        }

        DescriptiveStatistics ds = new DescriptiveStatistics();
        ArrayList<Float> floats = new ArrayList<>();
        for (Song s: songs) {
            ds.addValue(s.getHotness());
            floats.add(s.getHotness());
        }

        Arrays.sort(floats.toArray());
        float median;
        if (floats.size() % 2 == 0)
            median = (floats.get(floats.size()/2) + floats.get(floats.size()/2 - 1))/2;
        else
            median = floats.get(floats.size()/2);

        ArtistStatistics as = new ArtistStatistics(artistId, (float) ds.getMean(), median, (float) ds.getStandardDeviation());


        return ArtistStatistics.columnNames + '\n' + as.toCsvLine();
    }


    @GetMapping(value = "/popularity", produces = csv)
    public String getArtistsPopularityCsv(@RequestParam(required = false) Integer pageSize, @RequestParam(required = false) Integer pageRank, HttpServletResponse response) {
        response.setHeader(CONTENT_TYPE, csv);
        response.setStatus(HttpServletResponse.SC_OK);

        if(pageRank == null)
            pageRank = 0;

        if(pageSize == null || pageSize == 0)
            pageSize = 50;

        StringBuilder sb = new StringBuilder(Artist.columnNames);
        List<Artist> artists = artistService.getArtistsByHotness(pageSize,pageRank);

        for (Artist a : artists) {
            sb.append('\n');
            sb.append(a.toCsvLine());
        }
        return sb.toString();
    }


    @PostMapping(value = "", produces = csv)
    public String postArtistCsv(@RequestBody ArtistRequestBody addArtist, HttpServletResponse response) {
        response.setHeader(CONTENT_TYPE, csv);
        response.setStatus(HttpServletResponse.SC_CREATED);

        Artist a = artistService.save(new Artist(addArtist));
        if(a == null) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return null;
        }
        else {
            response.setHeader(HttpHeaders.LOCATION, URI_BASE + a.getArtistid());
        }
        return Artist.columnNames + '\n' + a.toCsvLine();
    }


    @PutMapping(value = "/{artistId}", produces = csv)
    public String updateArtistCsv(@PathVariable int artistId, @NotNull @RequestBody ArtistRequestBody artistRequestBody, HttpServletResponse response){
        response.setHeader(HttpHeaders.LOCATION, URI_BASE + artistId);
        response.setStatus(HttpServletResponse.SC_OK);

        Optional<Artist> a = artistService.getArtistById(artistId);

        if(!a.isPresent()){
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return null;
        }

        Artist artist = a.get();
        artist.update(artistRequestBody);
        Artist updated = artistService.save(artist);

        return Artist.columnNames + '\n' + updated.toCsvLine();
    }
}
