package nl.rug.shamzam.Api;

import nl.rug.shamzam.Model.Song;
import nl.rug.shamzam.SongService;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping("api/songs")
public class SongApi {
    SongService songService;

    public SongApi(SongService songService){
        this.songService = songService;
    }

    @GetMapping("/http-servlet-response")
    public String usingHttpServletResponse(HttpServletResponse response) {
        response.addHeader("Baeldung-Example-Header", "Value-HttpServletResponse");
        response.setStatus(200);
        return "Response with header using HttpServletResponse";
    }

    @GetMapping("")
    public List<Song> getSongs(@RequestParam(required = false) String title, @RequestParam(required = false) String artistId,
                               @RequestParam(required = false) String artistName, @RequestParam(required = false) Integer year,
                               @RequestParam(required = false) String genre, HttpServletResponse response) {
        response.addHeader("Baeldung-Example-Header", "Value-HttpServletResponse");
        response.setStatus(200);

        if(title == null)
            title = "";

        if(artistId == null)
            artistId = "";

        if(artistName == null)
            artistName = "";

        if(year == null)
            year = 0;

        if(genre == null)
            genre = "";

        return songService.getSongsByParams(title, artistId, artistName, year, genre);
    }
}
