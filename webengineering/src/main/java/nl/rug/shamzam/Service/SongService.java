package nl.rug.shamzam.Service;

import nl.rug.shamzam.Model.Song;
import nl.rug.shamzam.Repository.SongRepository;
import org.springframework.stereotype.Service;

import java.util.List;


@Service("SongService")
public class SongService {
    SongRepository songRepository;

    public SongService(SongRepository arp){
        songRepository = arp;
    }

    public List<Song> getSongsByParams(String title, Integer aId, String aName, Integer year, String genre){
        return songRepository.getSongsByParams(title, intToString(aId), aName, intToString(year),genre);
    }

    public Song getSongById(int id) {
        return songRepository.getSongById(id);
    }


    public Song addSong(Song song) {
        //Find the song if it exists
        List<Song> queryResults = songRepository.getSongsByParamsFullMatch(
                song.getTitle(), intToString(song.getArtistId()), song.getArtistName(),
                intToString(song.getYear()), song.getArtist().getTerms());
        if(!queryResults.isEmpty()) {
            System.out.println("SONG ALREADY EXISTS, RETURNING EXISTING COPY");
            return queryResults.get(0);
        }
        else {
            return songRepository.save(song);
        }
    }


    public Song replaceSong(int songId, Song replacement) {
        Song oldSong = songRepository.getSongById(songId);
        oldSong.update(replacement);
        return songRepository.save(oldSong);
    }


    /**
     * a custom to string method for integers,
     * that returns an empty string if i == 0
     * @param i the integer
     * @return a string containing i, or empty string if i == 0
     */
    private static String intToString(Integer i) {
        if(i == 0) return "";
        return i.toString();
    }
}
