package nl.rug.shamzam.Repository;

import nl.rug.shamzam.Model.Song;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SongRepository extends JpaRepository<Song,String> {
    @Query("select s from Song s where s.title LIKE %:title% AND cast(s.artist.artistid as string) LIKE CASE WHEN (:artistId = '') THEN concat('%', '', '%') ELSE :artistId END " +
            "AND s.artist.name LIKE %:artistName% AND cast(s.year as string) LIKE CASE WHEN (:year = '') THEN concat('%','','%') ELSE :year END " +
            "AND s.artist.terms LIKE %:terms%")
    List<Song> getSongsByParams(String title, String artistId, String artistName, String year, String terms);

    @Query("select s from Song s where s.title LIKE :title AND cast(s.artist.artistid as string) LIKE :artistId AND s.artist.name LIKE :artistName " +
            "AND cast(s.year as string) LIKE :year AND s.artist.terms LIKE :terms AND s.id LIKE :datasetId")
    List<Song> getSongsByParamsFullMatch(String title, String artistId, String artistName, String year, String terms, String datasetId);

    @Query("select s from Song s where s.songid = :id")
    Song getSongById(int id);

    //gets automatically generated
    boolean existsSongByTitle(String title);

    @Query("select s from Song s order by s.hotness")
    List<Song> getSongsPopularity(Pageable page);

    @Query("select s from Song s where s.year = :year order by s.hotness DESC")
    List<Song> getSongsPopularityByYear(int year);


}
