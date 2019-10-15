package nl.rug.shamzam.Model;

import javax.persistence.*;
import java.util.List;

@Entity
public class Artist {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int artistid;

    private String id;
    private float familiarity;
    private float hotness;
    private float latitude;
    private int location;
    private float longitude;
    private String name;
    private float similar;
    private String terms;

    @OneToMany(mappedBy="artist")
    private List<Song> songs;

    public String getName(){return this.name;}
    public String getTerms(){return this.terms;}
    public float getHotness(){return this.hotness;}
    public String getId(){return this.id;}
    public int getArtistid() {
        return artistid;
    }
}
