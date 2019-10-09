package nl.rug.shamzam.Api;

import nl.rug.shamzam.ArtistService;
import nl.rug.shamzam.Model.Artist;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping("api/artists")
public class ArtistApi {
    ArtistService artistService;

    public ArtistApi(ArtistService ars){
        artistService = ars;
    }

    @GetMapping("/http-servlet-response")
    public String usingHttpServletResponse(HttpServletResponse response) {
        response.addHeader("Baeldung-Example-Header", "Value-HttpServletResponse");
        response.setStatus(200);
        return "Response with header using HttpServletResponse";
    }

    @GetMapping("")
    public List<Artist> getArtists(@RequestParam String artistName, @RequestParam String genre, HttpServletResponse response) {
        response.addHeader("Baeldung-Example-Header", "Value-HttpServletResponse");
        response.setStatus(200);
        return artistService.getArtistsByNameAndGenre(artistName,genre);
    }

    @GetMapping("")
    public List<Artist> getAllArtists(){
        return artistService.getArtistsByNameAndGenre("","");
    }
}
