package nl.rug.shamzam.Model.outsideModels;

public class ArtistPost {
    private String name;
    private String terms;

    public String getName() {
        return name;
    }
    public String getTerms() {
        return terms;
    }

    public ArtistPost(String n, String t){
        this.name = n;
        this.terms = t;
    }
}
