package nl.rug.shamzam.Model.outsideModels;

public class ArtistRequestBody {
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

}
