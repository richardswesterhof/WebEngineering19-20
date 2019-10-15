package nl.rug.shamzam.Api;

import nl.rug.shamzam.Service.ArtistService;
import nl.rug.shamzam.Model.Artist;
import nl.rug.shamzam.Model.returnTypes.ArtistHotness;
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

    //ALLE CSV REQUESTS!!

}
