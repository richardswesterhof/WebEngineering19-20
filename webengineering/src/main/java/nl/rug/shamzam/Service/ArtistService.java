package nl.rug.shamzam.Service;

import nl.rug.shamzam.Model.Artist;
import nl.rug.shamzam.Model.outsideModels.ArtistHotness;
import nl.rug.shamzam.Model.outsideModels.ArtistPut;
import nl.rug.shamzam.Repository.ArtistRepository;
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
        List<Artist> queryResults = this.getArtistsByNameAndGenre(a.getName(), a.getTerms());
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

    public List<Artist> getArtistsByNameAndGenre(String name, String genre){
        return artistRepository.getArtistsByNameAndGenre(name,genre);
    }

    public Optional<Artist> getArtistById(int id){
        return artistRepository.getArtistByArtistid(id);
    }

    public List<ArtistHotness> getArtistsByHotness(int limit, int pagerank){
        List<Artist> artists = artistRepository.getArtistOrOrderByHotness();
        System.out.println("pageSize=="+limit+"&pageRank=="+pagerank);
        System.err.println("size of artists=="+artists.size());
        if(artists.size() < limit*pagerank){
            System.err.println("in limiter 1a");
            return new ArrayList<ArtistHotness>();
        }else{
            System.err.println("in limiter 1b");
            artists = artists.subList(limit*pagerank,artists.size());
        }

        System.err.println("size of artists=="+artists.size());
        System.err.println("upper limit == " + (limit*(pagerank+1)));
        if(artists.size() > limit*(pagerank+1)){
            System.err.println("in limiter 2");
            artists = artists.subList(0,limit*(pagerank+1));
        }

        System.err.println("size of artists=="+artists.size());
        ArrayList<ArtistHotness> hotnesses = new ArrayList<>();
        for (Artist a: artists) {
            hotnesses.add(new ArtistHotness(a.getArtistid(),a.getName(),a.getTerms()));
        }

        return hotnesses;
    }

}
