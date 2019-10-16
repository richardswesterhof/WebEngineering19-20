package nl.rug.shamzam.Model.outsideModels;

import nl.rug.shamzam.Model.Artist;

public class ArtistReturnPost {
    private int artistId;
    private String id;
    private String name;
    private String terms;

    public static String columnames = "\"artistId\", \"id\", \"name\", \"terms\"\n";

    public String getName() {
        return name;
    }
    public String getTerms() {
        return terms;
    }
    public int getArtistId(){return this.artistId; }
    public String getId(){return this.id;}

    public ArtistReturnPost(Artist a){
        this.artistId = a.getArtistid();
        this.id = a.getId();
        this.name = a.getName();
        this.terms = a.getTerms();
    }

    public String toCsv(){
        return artistId + ",\"" +
                id + "\",\"" +
                name + ",\"" +
                terms + "\"\n";
    }

}
