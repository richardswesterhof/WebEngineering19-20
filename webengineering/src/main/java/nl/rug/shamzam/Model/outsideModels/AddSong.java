package nl.rug.shamzam.Model.outsideModels;

import nl.rug.shamzam.Model.Artist;
import nl.rug.shamzam.Model.Song;
import nl.rug.shamzam.Utils.Unnullifier;

import javax.validation.constraints.NotBlank;

/**
 * this class is used for the format of the body of an add or post song request
 */
public class AddSong {

    @NotBlank(message = "artistid is mandatory")
    private Integer artistid;


    private String songid;
    private String title;
    private Float duration;
    private Integer year;
    private Float hotness;


    public Song toSong(Artist artist) {
        return new Song(Unnullifier.unnullify(title), artist,
                Unnullifier.unnullify(duration), Unnullifier.unnullify(year),
                Unnullifier.unnullify(hotness), songid);
    }

    @Override
    public String toString() {
        return title + artistid + duration + year;
    }


    public Integer getArtistId() {
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

    public void setSongid(String songid) {
        this.songid = songid;
    }

    public void setHotness(Float hotness) {
        this.hotness = hotness;
    }
}
