package nl.rug.shamzam.Model.outsideModels;

import nl.rug.shamzam.Model.Artist;

public class ArtistPut {
    private String name;
    private String terms;
    private float hotness;

    public String getName() {
        return name;
    }
    public String getTerms() {
        return terms;
    }
    public float getHotness(){return this.hotness; }

    public ArtistPut(Artist a){
        this.name = a.getName();
        this.terms = a.getTerms();
        this.hotness = a.getHotness();
    }
}
