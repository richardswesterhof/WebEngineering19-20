package nl.rug.shamzam.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import nl.rug.shamzam.Utils.Unnullifier;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
public class Song {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int songid;

    private String id;

    @ManyToOne
    @JoinColumn(name="artistid", nullable=false)
    @OnDelete(action= OnDeleteAction.CASCADE)
    @JsonIgnore
    private Artist artist;

    private float artist_mbtags;
    private float artist_mbtags_count;
    private float bars_confidence;
    private float bars_start;
    private float beats_confidence;
    private float beats_start;
    private float duration;
    private float end_of_fade_in;
    private float hotness;

    @Column(name = "music_key")
    private float key;
    private float key_confidence;
    private float loudness;
    private int mode;
    private float mode_confidence;
    private float start_of_fade_out;
    private float tatums_confidence;
    private float tatums_start;
    private float tempo;
    private float time_signature;
    private float time_signature_confidence;
    private String title;
    private int year;

    public static final String columnNames = "\"title\", \"artistName\", \"duration\", \"year\", \"id\", \"songid\", \"artistid\", \"link\", \"artistLink\"";


    public Song(String title, @NotNull Artist artist, Float duration, Integer year, Float hotness, String ...songid) {
        if(songid.length > 0) {
            if(songid[0] == null || songid[0].equals("null") || songid[0].equals("")) {
                id = "";
            }
            else id = songid[0];
        }
        else {
            id = "";
        }
        //setting the variables given as parameters
        this.artist = artist;
        this.duration = Unnullifier.unnullify(duration);
        this.hotness = Unnullifier.unnullify(hotness);
        this.title = Unnullifier.unnullify(title);
        this.year = Unnullifier.unnullify(year);

        //setting the rest to default values
        artist_mbtags = 0;
        artist_mbtags_count = 0;
        bars_confidence = 0;
        bars_start = 0;
        beats_confidence = 0;
        beats_start = 0;
        end_of_fade_in = 0;
        key = 0;
        key_confidence = 0;
        loudness = 0;
        mode = 0;
        mode_confidence = 0;
        start_of_fade_out = 0;
        tatums_confidence = 0;
        tatums_start = 0;
        tempo = 0;
        time_signature = 0;
        time_signature_confidence = 0;
    }

    //default constructor is needed by spring
    public Song() {}


    public String getTitle() {
        return title;
    }

    public int getYear() {
        return year;
    }

    public float getDuration() {
        return duration;
    }

    public String getId() {
        return id;
    }

    public int getSongid() {
        return songid;
    }

    public String getLink() {
        return "/" + songid;
    }

    public int getArtistId() {
        return artist.getArtistid();
    }

    public String getArtistName() {
        return artist.getName();
    }

    public String getArtistLink() {
        return "/api/artists/" + getArtistId();
    }

    public float getHotness(){return this.hotness;}

    public Artist getArtist() {
        return artist;
    }

    public String toCsvLine() {
        return "\"" + title + "\"," +
               "\"" + getArtistName() + "\"," +
               "\"" + duration + "\"," +
               "\"" + year + "\"," +
               "\"" + id + "\"," +
               "\"" + songid + "\"," +
               "\"" + getArtistId() + "\"," +
               "\"" + getLink() + "\"," +
               "\"" + getArtistLink() + "\"";
    }


    @Override
    public String toString() {
        return title + ", " + duration + ", " + year + ", " + id;
    }


    public void setTitle(String title) {
        this.title = title;
    }


    public void update(Song s) {
        this.title = s.getTitle();
        this.duration = s.getDuration();
        this.year = s.getYear();
        this.artist = s.getArtist();
        this.hotness = s.getHotness();
    }

}
