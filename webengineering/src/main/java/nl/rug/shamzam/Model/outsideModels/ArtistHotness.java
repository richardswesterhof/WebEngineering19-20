package nl.rug.shamzam.Model.outsideModels;

import nl.rug.shamzam.Model.Artist;

public class ArtistHotness {
    private int id;
    private String name;
    private String term;
    private String pageLink;

    public static String columnames = "\"artistid\", \"name\", \"term\", \"pageLink\"\n";

    public ArtistHotness(int id, String name, String term){
        this.id = id;
        this.name = name;
        this.term = term;
        this.pageLink = "/api/artists/" + this.id;
    }
    public ArtistHotness(Artist a){
        this.id = a.getArtistid();
        this.name = a.getName();
        this.term = a.getTerms();
        this.pageLink = "/api/artists/"+this.id;
    }

    public int getId() {
        return id;
    }
    public String getName(){
        return name;
    }
    public String getTerm(){
        return term;
    }
    public String getPageLink(){
        return pageLink;
    }

    public String toCsvLine(){
        return id + "," +
                "\"" + name + "\"," +
                "\"" + term + "\"," +
                "\"" + pageLink + "\n";
    }
}
