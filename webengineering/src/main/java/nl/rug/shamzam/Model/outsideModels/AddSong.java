package nl.rug.shamzam.Model.outsideModels;

import com.fasterxml.jackson.annotation.JsonProperty;
import nl.rug.shamzam.Model.Artist;
import nl.rug.shamzam.Model.Song;
import nl.rug.shamzam.Utils.Unnullifier;

/**
 * this class is used for the format of the body of an add song request
 */
public class AddSong {

    private Integer artistid;
    
    @JsonProperty(required = false)
    private String songid;

    private String title;
    private Float duration;
    private Integer year;


    public Song toSong(Artist artist) {
        return new Song(Unnullifier.unnullify(title), artist,
                Unnullifier.unnullify(duration), Unnullifier.unnullify(year), songid);
    }

    @Override
    public String toString() {
        return title + artistid + duration + year;
    }


    public int getArtistId() {
        return artistid;
    }

    public String getSongId() {
        return songid;
    }

    public void setDuration(Float duration) {
        this.duration = duration;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public void setArtistid(Integer artistid) {
        this.artistid = artistid;
    }
}
