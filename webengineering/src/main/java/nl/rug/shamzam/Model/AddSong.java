package nl.rug.shamzam.Model;

import nl.rug.shamzam.Utils.Unnullifier;

/**
 * this class is used for the format of the body of an add song request
 */
public class AddSong {

    private String title;
    private Float duration;
    private String artistName;
    private Integer year;


    public Song toSong() {
        return new Song(Unnullifier.unnullify(title), Unnullifier.unnullify(artistName),
                Unnullifier.unnullify(duration), Unnullifier.unnullify(year));
    }

    @Override
    public String toString() {
        return title + artistName + duration + year;
    }

    public void setArtistName(String artistName) {
        this.artistName = artistName;
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

}
