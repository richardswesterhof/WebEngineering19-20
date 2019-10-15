package nl.rug.shamzam.Api;

import nl.rug.shamzam.Service.ArtistService;
import nl.rug.shamzam.Model.Artist;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;


@CrossOrigin
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
    public List<Artist> getArtists(@RequestParam(required = false) String name, @RequestParam(required = false) String genre, HttpServletResponse response) {
        response.addHeader("Baeldung-Example-Header", "Value-HttpServletResponse");
        response.setStatus(200);

        if(name == null)
            name = "";

        if(genre == null)
            genre = "";

        return artistService.getArtistsByNameAndGenre(name,genre);
    }
}
