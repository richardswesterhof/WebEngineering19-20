package nl.rug.shamzam.Api;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("api/artists")
public class ArtiestApi {

    @GetMapping("/http-servlet-response")
    public String usingHttpServletResponse(HttpServletResponse response) {
        response.addHeader("Baeldung-Example-Header", "Value-HttpServletResponse");
        response.setStatus(200);
        return "Response with header using HttpServletResponse";
    }
}
