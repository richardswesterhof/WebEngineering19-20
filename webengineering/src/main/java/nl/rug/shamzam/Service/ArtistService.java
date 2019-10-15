package nl.rug.shamzam.Service;

import nl.rug.shamzam.Model.Artist;
import nl.rug.shamzam.Model.returnTypes.ArtistHotness;
import nl.rug.shamzam.Repository.ArtistRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service("ArtistService")
public class ArtistService {
    ArtistRepository artistRepository;

    public ArtistService(ArtistRepository arp){
        artistRepository = arp;
    }

    public List<Artist> getArtistsByNameAndGenre(String name, String genre){
        return artistRepository.getArtistsByNameAndGenre(name,genre);
    }

    public Optional<Artist> getArtistById(int id){
        return artistRepository.getArtistByDbId(id);
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
