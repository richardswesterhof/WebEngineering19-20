package nl.rug.shamzam.Service;

import nl.rug.shamzam.Model.Artist;
import nl.rug.shamzam.Model.Song;
import nl.rug.shamzam.Model.outsideModels.ArtistHotness;
import nl.rug.shamzam.Model.outsideModels.SongPopularity;
import nl.rug.shamzam.Repository.SongRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service("SongService")
public class SongService {
    SongRepository songRepository;

    public SongService(SongRepository arp){
        songRepository = arp;
    }

    public List<Song> getSongsByParams(String title, Integer aId, String aName, Integer year, String genre, Integer pageSize, Integer pageRank){
        return songRepository.getSongsByParams(title, intToString(aId), aName, intToString(year),genre, PageRequest.of(pageRank,pageSize));
    }

    public Song getSongById(int id) {
        return songRepository.getSongById(id);
    }


    public Song addSong(Song song) {
        //Find the song if it exists
        List<Song> queryResults = songRepository.getSongsByParamsFullMatch(
                song.getTitle(), intToString(song.getArtistId()), song.getArtistName(),
                intToString(song.getYear()), song.getArtist().getTerms(), song.getId(), PageRequest.of(0,2));
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
        if(oldSong == null) {
            return null;
        }
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

    public List<SongPopularity> getSongPopularity(int limit, int pagerank){
        List<Song> songs = songRepository.getSongsPopularity(PageRequest.of(pagerank,limit));

        ArrayList<SongPopularity> popularities = new ArrayList<>();
        for (Song s: songs) {
            popularities.add(new SongPopularity(s));
        }

        return popularities;
    }

    public List<SongPopularity> getSongPopularityYear(int year, int limit, int pagerank){
        List<Song> songs = songRepository.getSongsPopularityByYear(year, PageRequest.of(pagerank,limit));


        ArrayList<SongPopularity> popularities = new ArrayList<>();
        for (Song s: songs) {
            popularities.add(new SongPopularity(s));
        }

        return popularities;
    }
}
