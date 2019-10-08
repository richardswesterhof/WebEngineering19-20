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
    private int title;
    private int year;
}
