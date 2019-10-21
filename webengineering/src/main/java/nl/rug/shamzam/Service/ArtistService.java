package nl.rug.shamzam.Service;

import nl.rug.shamzam.Model.Artist;
import nl.rug.shamzam.Model.outsideModels.ArtistHotness;
import nl.rug.shamzam.Model.outsideModels.ArtistPut;
import nl.rug.shamzam.Repository.ArtistRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
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
        List<Artist> queryResults = this.getArtistsByNameAndGenre(a.getName(), a.getTerms(), 0,2);
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

    public List<ArtistHotness> getArtistsByHotness(int limit, int pagerank){
        List<Artist> artists = artistRepository.getArtistOrOrderByHotness(PageRequest.of (pagerank, limit));

        ArrayList<ArtistHotness> hotnesses = new ArrayList<>();
        for (Artist a: artists) {
            hotnesses.add(new ArtistHotness(a.getArtistid(),a.getName(),a.getTerms()));
        }

        return hotnesses;
    }

}
