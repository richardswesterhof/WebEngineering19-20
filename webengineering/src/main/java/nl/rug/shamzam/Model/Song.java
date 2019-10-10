package nl.rug.shamzam.Model;

import javax.persistence.*;

@Entity
public class Song {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int dbId;

    private String id;

    private float artist_mbtags;
    private float artist_mbtags_count;
    private float bars_confidence;
    private float bars_start;
    private float beats_confidence;
    private float beats_start;
    private float duration;
    private float end_of_fade_in;
    private float hotttness;

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


    public Song(String title, String artistName, float duration, int year) {
        id = "MAKE CUSTOM IDS";
        artist_mbtags = 0;
        artist_mbtags_count = 0;
        bars_confidence = 0;
        bars_start = 0;
        beats_confidence = 0;
        beats_start = 0;
        this.duration = duration;
        end_of_fade_in = 0;
        hotttness = 0;
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
        this.title = title;
        this.year = year;
    }


    public Song() {
        this("", "", 0, 0);
    }


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

    public String getLink() {
        return "/" + id;
    }

    public String toCsv() {
        return "\"" + title + "\"," +
                "\"" + duration + "\"," +
                "\"" + year + "\"," +
                "\"/" + id + "\"";
    }
}
