package nl.rug.shamzam.Model.outsideModels;

public class ArtistStatistics {
    private float mean;
    private float median;
    private float std;

    public static String columnames = "\"mean\", \"median\", \"standardeviation\"\n";

    public float getMean(){return this.mean;}
    public float getMedian(){return this.median;}
    public float getStandardDeveation(){return this.std;}

    public ArtistStatistics(float mean, float median, float std){
        this.mean = mean;
        this.median = median;
        this.std = std;
    }

    public String toCsvLine(){
        return  mean + "," +
                median + "," +
                std + "\n";
    }
}
