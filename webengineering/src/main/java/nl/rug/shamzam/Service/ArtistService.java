package nl.rug.shamzam.Service;

import nl.rug.shamzam.Model.Artist;
import nl.rug.shamzam.Repository.ArtistRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service("ArtistService")
@Transactional
public class ArtistService {
    ArtistRepository artistRepository;

    public ArtistService(ArtistRepository arp){
        artistRepository = arp;
    }

    public Artist save(Artist a){

        //Find the artist if it exists
        List<Artist> queryResults = this.getArtistsByNameAndGenre(a.getName(), a.getTerms(), 1,0);
        if(!queryResults.isEmpty()) {
            System.out.println("ARTIST ALREADY EXISTS, RETURNING EXISTING COPY");
            return queryResults.get(0);
        }
        else {
            return artistRepository.saveAndFlush(a);
        }
    }

    public boolean delete(int i){
        return artistRepository.deleteByArtistid(i) > 0;
    }

    public List<Artist> getArtistsByNameAndGenre(String name, String genre, int limit, int pagerank){
        return artistRepository.getArtistsByNameAndGenre(name,genre, PageRequest.of(pagerank,limit));
    }

    public Optional<Artist> getArtistById(int id){
        return artistRepository.getArtistByArtistid(id);
    }

    public List<Artist> getArtistsByHotness(int limit, int pagerank){
        return artistRepository.getArtistOrOrderByHotness(PageRequest.of (pagerank, limit));
    }

}
