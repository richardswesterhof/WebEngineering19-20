package nl.rug.shamzam.Service;

import nl.rug.shamzam.Model.Artist;
import nl.rug.shamzam.Model.Song;
import nl.rug.shamzam.Repository.SongRepository;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.springframework.data.domain.ExampleMatcher.GenericPropertyMatchers.ignoreCase;

@Service("SongService")
public class SongService {
    SongRepository songRepository;

    public SongService(SongRepository arp){
        songRepository = arp;
    }

    public List<Song> getSongsByParams(String title, String aId, String aName, int year, String genre){
        return songRepository.getSongsByParams(title, aId, aName, year,genre);
    }

    public Song getSongById(int id) {
        return songRepository.getSongById(id);
    }


    public Song addSong(Song song) {
        //Find the song if it exists
        boolean exists = songRepository.existsSongByTitle(song.getTitle());
        if(exists) {
            System.out.println("SONG ALREADY EXISTS, RETURNING EXISTING COPY");
            return getSongsByParams(song.getTitle(), null, null, 0, null).get(0);
        }
        else {
            return songRepository.saveAndFlush(song);
        }
    }


    public Song replaceSong(int songId, Song replacement) {
        Song oldSong = songRepository.getSongById(songId);
        oldSong.update(replacement);
        return songRepository.saveAndFlush(oldSong);
    }
}
