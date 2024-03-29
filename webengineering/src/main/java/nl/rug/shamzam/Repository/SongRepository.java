package nl.rug.shamzam.Repository;

import nl.rug.shamzam.Model.Song;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SongRepository extends JpaRepository<Song,String> {
    @Query("select s from Song s where s.title LIKE %:title% " +
            "AND cast(s.artist.artistid as string) LIKE CASE WHEN (:artistId = '') THEN concat('%', '', '%') ELSE :artistId END " +
            "AND s.artist.name LIKE %:artistName% AND cast(s.year as string) LIKE CASE WHEN (:year = '') THEN concat('%','','%') ELSE :year END " +
            "AND s.artist.terms LIKE %:terms%")
    List<Song> getSongsByParams(String title, String artistId, String artistName, String year, String terms, Pageable page);

    @Query("select s from Song s where s.title LIKE CASE WHEN (:title = '') THEN concat('%', '', '%') ELSE :title END " +
            "AND cast(s.artist.artistid as string) LIKE :artistId " +
            "AND s.artist.name LIKE :artistName " +
            "AND cast(s.year as string) LIKE :year " +
            "AND s.artist.terms LIKE :terms " +
            "AND s.id LIKE CASE WHEN (:datasetId = '') THEN concat('%', '', '%') ELSE :datasetId END")
    List<Song> getSongsByParamsFullMatch(String title, String artistId, String artistName, String year, String terms, String datasetId, Pageable page);

    @Query("select s from Song s where s.songid = :id")
    Song getSongById(int id);

    @Query("select s from Song s where cast(s.year as string) LIKE CASE WHEN (:year = '') THEN concat('%', '', '%') ELSE :year END order by s.hotness DESC")
    List<Song> getSongsByPopularityYear(String year, Pageable page);


}
