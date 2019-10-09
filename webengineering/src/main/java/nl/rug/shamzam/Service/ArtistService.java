package nl.rug.shamzam.Service;

import nl.rug.shamzam.Model.Artist;
import nl.rug.shamzam.Repository.ArtistRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("ArtistService")
public class ArtistService {
    ArtistRepository artistRepository;

    public ArtistService(ArtistRepository arp){
        artistRepository = arp;
    }

    public List<Artist> getArtistsByNameAndGenre(String name, String genre){
        return artistRepository.getArtistsByNameAndGenre(name,genre);
    }
}
