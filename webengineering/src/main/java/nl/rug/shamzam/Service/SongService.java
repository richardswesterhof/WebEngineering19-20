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

    public List<Song> getSongsByParams(String title, String aId, String aName, int year, String genre){
        return songRepository.getSongsByParams(title, aId, aName, year,genre);
    }

    public Song getSongById(String id) {
        return songRepository.getSongById(id);
    }
}
