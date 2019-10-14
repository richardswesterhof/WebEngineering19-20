package nl.rug.shamzam.Repository;

import nl.rug.shamzam.Model.Artist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface ArtistRepository extends JpaRepository<Artist,String> {
    @Query("select a from Artist a where a.name LIKE %?1% AND a.terms LIKE %?2%")
    List<Artist> getArtistsByNameAndGenre(String name, String terms);

    @Query("select a from Artist a where a.artistid = :id")
    Artist getArtistById(int id);

}
