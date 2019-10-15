package nl.rug.shamzam.Api;

import nl.rug.shamzam.ArtistService;
import nl.rug.shamzam.Model.Artist;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
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
}
