package nl.rug.shamzam.Repository;

import nl.rug.shamzam.Model.Artist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;
import java.util.Optional;

public interface ArtistRepository extends JpaRepository<Artist,Integer> {
    @Query("select a from Artist a where a.name LIKE %?1% AND a.terms LIKE %?2%")
    List<Artist> getArtistsByNameAndGenre(String name, String terms);

    Optional<Artist> getArtistByArtistid(int id);

    @Query("SELECT a FROM Artist a ORDER BY a.hotness DESC")
    List<Artist> getArtistOrOrderByHotness();

    long deleteByArtistid(int id);
}
