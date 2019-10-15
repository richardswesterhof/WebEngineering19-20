package nl.rug.shamzam.Model.returnTypes;

public class ArtistHotness {
    private int id;
    private String name;
    private String term;
    private String pageLink;

    public ArtistHotness(int id, String name, String term){
        this.id = id;
        this.name = name;
        this.term = term;
        this.pageLink = "/" + this.id;
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
}
