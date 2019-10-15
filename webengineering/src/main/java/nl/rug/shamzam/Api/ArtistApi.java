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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/artists")
public class ArtistApi {
    ArtistService artistService;

    public ArtistApi(ArtistService ars){
        artistService = ars;
    }

    @GetMapping(value = "/{artistId}", consumes = {"application/json"})
    public Artist getArtist(@PathVariable int artistId,  HttpServletResponse response) {

        Optional<Artist> a = artistService.getArtistById(artistId);
        if(!a.isPresent()){
            response.setStatus(404);
            return null;
        }


        response.setHeader("Content-Type", "application/json");
        response.setStatus(200);

        return a.get();
    }

    @GetMapping(value = "", consumes = {"application/json"})
    public List<Artist> getArtists(@RequestParam(required = false) String artistName, @RequestParam(required = false) String genre, HttpServletResponse response) {
        response.setHeader("Content-Type", "application/json");
        response.setStatus(200);

        if(artistName == null)
            artistName = "";

        if(genre == null)
            genre = "";

        return artistService.getArtistsByNameAndGenre(artistName,genre);
    }

    @GetMapping(value = "/{artistId}/statistics", consumes = {"application/json"})
    public ArtistStatistics getArtistStatistics(@PathVariable int artistId, @RequestParam(required = false) Integer year, HttpServletResponse response) {

        Optional<Artist> a = artistService.getArtistById(artistId);
        if(!a.isPresent()){
            response.setStatus(404);
            return null;
        }

        List<Song> songs = a.get().getSongs();

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



        response.setStatus(200);
        response.setHeader("Content-Type", "application/json");
        return as;
    }

    @GetMapping(value = "/hotness", consumes = {"application/json"})
    public List<ArtistHotness> getArtistsHotness(@RequestParam(required = false) Integer pageSize, @RequestParam(required = false) Integer pageRank, HttpServletResponse response) {
        response.setHeader("Content-Type", "application/json");
        response.setStatus(200);

        if(pageRank == null)
            pageRank = 0;

        if(pageSize == null || pageSize == 0)
            pageSize = 50;

        return artistService.getArtistsByHotness(pageSize,pageRank);
    }

    @PostMapping(value = "", produces = "application/json")
    public ArtistReturnPost postArtist(@RequestBody ArtistPost addArtist, HttpServletResponse response) {

        Artist a = artistService.save(new Artist(addArtist));
        if(a == null) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return null;
        }
        else {
            response.setHeader(HttpHeaders.LOCATION, "/api/artists/" + a.getArtistid());
        }
        response.setStatus(201);
        return new ArtistReturnPost(a);
    }

    @DeleteMapping(value = "/{artistId}")
    public void deleteArtist(@PathVariable int artistId, HttpServletResponse response){
        if(!artistService.delete(artistId)){
            response.setStatus(404);
            return;
        }

        response.setStatus(204);
        return;
    }

    @PutMapping(value = "/{artistId}", produces = "application/json")
    public ArtistPut updateArtist(@PathVariable int artistId, @NotNull @RequestBody ArtistPut artistPut, HttpServletResponse response){
        Optional<Artist> a = artistService.getArtistById(artistId);

        if(!a.isPresent()){
            response.setStatus(404);
            return null;
        }

        Artist artist = a.get();
        artist.update(artistPut);
        artistService.save(artist);

        response.setStatus(200);
        response.setHeader(HttpHeaders.LOCATION, "/api/artists/" + artistId);
        return artistPut;
    }



    //ALLE CSV REQUESTS!!

}
