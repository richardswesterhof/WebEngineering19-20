package nl.rug.shamzam.Model;

/**
 * this class is used for the format of the body of an add song request
 */
public class AddSong {

    private String title;
    private String artistName;
    private Float duration;
    private Integer year;


    public Song toSong() {
        return new Song(title, artistName, duration, year);
    }
}
