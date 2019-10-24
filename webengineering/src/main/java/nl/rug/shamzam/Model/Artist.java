package nl.rug.shamzam.Model;

import nl.rug.shamzam.Model.outsideModels.ArtistRequestBody;

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

    public static String columnNames = "\"artistid\", \"name\", \"terms\", \"hotness\", \"pageLink\"\n";

    @OneToMany(mappedBy="artist")
    private List<Song> songs;

    public String getName(){return this.name;}
    public String getTerms(){return this.terms;}
    public float getHotness(){return this.hotness;}
    public String getId(){return this.id;}
    public int getArtistid() {
        return artistid;
    }
    public List<Song> getSongs(){return this.songs;}

    public Artist(ArtistRequestBody arb){
        this.name = arb.getName();
        this.terms = arb.getTerms();
        this.hotness = arb.getHotness();
    }

    public void update(ArtistRequestBody arb){
        this.name = arb.getName();
        this.hotness = arb.getHotness();
        this.terms = arb.getTerms();
    }

    public Artist(){}

    public String toCsvLine(){
        return  "\"" + artistid + "\"," +
                "\"" + name + "\"," +
                "\"" + terms + "\"," +
                "\"" + hotness + "\"," +
                "\"/api/artists/" + artistid + "\"";
    }
}