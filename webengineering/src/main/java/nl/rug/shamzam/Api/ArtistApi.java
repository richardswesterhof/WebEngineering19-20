package nl.rug.shamzam.Api;

import nl.rug.shamzam.Model.Song;
import nl.rug.shamzam.Model.outsideModels.*;
import nl.rug.shamzam.Service.ArtistService;
import nl.rug.shamzam.Model.Artist;
import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.swing.text.html.Option;
import javax.validation.constraints.NotNull;
import java.util.*;


@CrossOrigin
@RestController
@RequestMapping("api/artists")
public class ArtistApi {
    ArtistService artistService;

    public ArtistApi(ArtistService ars){
        artistService = ars;
    }

    @GetMapping(value = "/{artistId}")
    public ArtistHotness getArtist(@PathVariable int artistId,  HttpServletResponse response) {
        System.out.println("in getArtist");
        Optional<Artist> a = artistService.getArtistById(artistId);
        if(!a.isPresent()){
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return null;
        }


        response.setHeader("Content-Type", "application/json");
        response.setStatus(HttpServletResponse.SC_OK);

        return new ArtistHotness(a.get());
    }

    @GetMapping(value = "")
    public List<ArtistHotness> getArtists(@RequestParam(required = false) String artistName, @RequestParam(required = false) String genre,
                                          @RequestParam(required = false) Integer pageSize, @RequestParam(required = false) Integer pageRank, HttpServletResponse response) {
        response.setHeader("Content-Type", "application/json");
        response.setStatus(HttpServletResponse.SC_OK);

        if(artistName == null)
            artistName = "";

        if(genre == null)
            genre = "";

        if(pageRank == null)
            pageRank = 0;

        if(pageSize == null || pageSize == 0)
            pageSize = 50;

        List<Artist> artists = artistService.getArtistsByNameAndGenre(artistName,genre,pageSize,pageRank);
        List<ArtistHotness> artistHotnesses = new ArrayList<>();

        for (Artist a: artists) {
            artistHotnesses.add(new ArtistHotness(a));
        }

        return artistHotnesses;
    }

    @GetMapping(value = "/{artistId}/statistics")
    public ArtistStatistics getArtistStatistics(@PathVariable int artistId, @RequestParam(required = false) Integer year, HttpServletResponse response) {

        Optional<Artist> a = artistService.getArtistById(artistId);
        if(!a.isPresent()){
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return null;
        }

        List<Song> songs = a.get().getSongs();
        if(songs.size() == 0){
            return new ArtistStatistics(0,0,0);
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

        ArtistStatistics as = new ArtistStatistics((float) ds.getMean(), median,(float)ds.getStandardDeviation());



        response.setStatus(HttpServletResponse.SC_OK);
        response.setHeader("Content-Type", "application/json");
        return as;
    }

    @GetMapping(value = "/hotness")
    public List<ArtistHotness> getArtistsHotness(@RequestParam(required = false) Integer pageSize, @RequestParam(required = false) Integer pageRank, HttpServletResponse response) {
        response.setHeader("Content-Type", "application/json");
        response.setStatus(HttpServletResponse.SC_OK);

        if(pageRank == null)
            pageRank = 0;

        if(pageSize == null || pageSize == 0)
            pageSize = 50;

        return artistService.getArtistsByHotness(pageSize,pageRank);
    }

    @PostMapping(value = "")
    public ArtistReturnPost postArtist(@RequestBody ArtistPost addArtist, HttpServletResponse response) {

        Artist a = artistService.save(new Artist(addArtist));
        if(a == null) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return null;
        }
        else {
            response.setHeader(HttpHeaders.LOCATION, "/api/artists/" + a.getArtistid());
        }
        response.setStatus(HttpServletResponse.SC_CREATED);
        return new ArtistReturnPost(a);
    }

    @DeleteMapping(value = "/{artistId}")
    public void deleteArtist(@PathVariable int artistId, HttpServletResponse response){
        if(!artistService.delete(artistId)){
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        response.setStatus(HttpServletResponse.SC_NO_CONTENT);
        return;
    }

    @PutMapping(value = "/{artistId}")
    public ArtistPut updateArtist(@PathVariable int artistId, @NotNull @RequestBody ArtistPut artistPut, HttpServletResponse response){
        Optional<Artist> a = artistService.getArtistById(artistId);

        if(!a.isPresent()){
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return null;
        }

        Artist artist = a.get();
        artist.update(artistPut);
        artistService.save(artist);

        response.setStatus(HttpServletResponse.SC_OK);
        response.setHeader(HttpHeaders.LOCATION, "/api/artists/" + artistId);
        return artistPut;
    }



    //ALL CSV REQUESTS!!
    @GetMapping(value = "/{artistId}", produces = {"text/csv"})
    public String getArtistCsv(@PathVariable int artistId,  HttpServletResponse response) {

        System.out.println("in getArtistCsv");

        Optional<Artist> a = artistService.getArtistById(artistId);
        if(!a.isPresent()){
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return null;
        }


        response.setHeader("Content-Type", "text/csv");
        response.setStatus(HttpServletResponse.SC_OK);

        return ArtistHotness.columnames + new ArtistHotness(a.get()).toCsvLine();
    }

    @GetMapping(value = "", produces = "text/csv")
    public String getArtistsCsv(@RequestParam(required = false) String artistName, @RequestParam(required = false) String genre,
                                @RequestParam(required = false) Integer pageSize, @RequestParam(required = false) Integer pageRank, HttpServletResponse response) {
        response.setHeader("Content-Type", "text/csv");
        response.setStatus(HttpServletResponse.SC_OK);

        if(artistName == null)
            artistName = "";

        if(genre == null)
            genre = "";

        if(pageRank == null)
            pageRank = 0;

        if(pageSize == null || pageSize == 0)
            pageSize = 50;

        String s = ArtistHotness.columnames;

        List<Artist> artists = artistService.getArtistsByNameAndGenre(artistName,genre, pageSize,pageRank);

        for (Artist a: artists) {
            s +=  new ArtistHotness(a).toCsvLine();
        }

        return s;
    }

    @GetMapping(value = "/{artistId}/statistics", produces = "text/csv")
    public String getArtistStatisticsCsv(@PathVariable int artistId, @RequestParam(required = false) Integer year, HttpServletResponse response) {

        Optional<Artist> a = artistService.getArtistById(artistId);
        if(!a.isPresent()){
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return null;
        }

        List<Song> songs = a.get().getSongs();

        if(songs.size() == 0){
            return ArtistStatistics.columnames + new ArtistStatistics(0,0,0).toCsvLine();
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

        ArtistStatistics as = new ArtistStatistics((float) ds.getMean(), median,(float)ds.getStandardDeviation());



        response.setStatus(HttpServletResponse.SC_OK);
        response.setHeader("Content-Type", "text/csv");
        return ArtistStatistics.columnames +  as.toCsvLine();
    }

    @GetMapping(value = "/hotness", consumes = "text/csv")
    public String getArtistsHotnessCsv(@RequestParam(required = false) Integer pageSize, @RequestParam(required = false) Integer pageRank, HttpServletResponse response) {
        response.setHeader("Content-Type", "text/csv");
        response.setStatus(HttpServletResponse.SC_OK);

        if(pageRank == null)
            pageRank = 0;

        if(pageSize == null || pageSize == 0)
            pageSize = 50;

        String s = ArtistHotness.columnames;
        List<ArtistHotness> artistHotnesses = artistService.getArtistsByHotness(pageSize,pageRank);

        for (ArtistHotness a: artistHotnesses) {
            s +=  a.toCsvLine();
        }
        return s;
    }

    @PostMapping(value = "", produces = "text/csv")
    public String postArtistCsv(@RequestBody ArtistPost addArtist, HttpServletResponse response) {

        Artist a = artistService.save(new Artist(addArtist));
        if(a == null) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return null;
        }
        else {
            response.setHeader(HttpHeaders.LOCATION, "/api/artists/" + a.getArtistid());
        }
        response.setStatus(201);
        return ArtistReturnPost.columnames + new ArtistReturnPost(a).toCsv();
    }

    @PutMapping(value = "/{artistId}", produces = "text/csv")
    public String updateArtistCsv(@PathVariable int artistId, @NotNull @RequestBody ArtistPut artistPut, HttpServletResponse response){
        Optional<Artist> a = artistService.getArtistById(artistId);

        if(!a.isPresent()){
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return null;
        }

        Artist artist = a.get();
        artist.update(artistPut);
        artistService.save(artist);

        response.setStatus(HttpServletResponse.SC_OK);
        response.setHeader(HttpHeaders.LOCATION, "/api/artists/" + artistId);
        return ArtistPut.columnames + artistPut.toCsv();
    }
}
