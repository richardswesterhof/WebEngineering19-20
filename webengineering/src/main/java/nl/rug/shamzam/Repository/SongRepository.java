package nl.rug.shamzam.Repository;

import nl.rug.shamzam.Model.Song;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SongRepository extends JpaRepository<Song,String> {
    //TODO: use the other params when they are in the database too
    @Query("select s from Song s where s.title LIKE %?1%")
    List<Song> getSongsByParams(String title, String artistId, String artistName, Integer year, String terms);

    @Query("select s from Song s where s.id = :id")
    Song getSongById(String id);

//    @Query("INSERT INTO person (id, first_name, last_name) VALUES (:title, :artistName, :duration, :year)")
//    Song insertSong(String title, String artistName, Float duration, Integer year);
}
