package nl.rug.shamzam.Repository;

import nl.rug.shamzam.Model.Song;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface SongRepository extends JpaRepository<Song,String> {
    //TODO: use the other params when they are in the database too
    @Query("select s from Song s where s.title LIKE %?1%")
    List<Song> getSongsByParams(String title, String artistId, String artistName, Integer year, String terms);

}
