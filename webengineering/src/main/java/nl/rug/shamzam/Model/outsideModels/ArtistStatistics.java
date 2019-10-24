package nl.rug.shamzam.Model.outsideModels;

public class ArtistStatistics {
    private int artistid;
    private float mean;
    private float median;
    private float std;

    public static String columnNames = "\"artistid\", \"mean\", \"median\", \"standarddeviation\"";

    public int getArtistid() {
        return this.artistid;
    }

    public float getMean() {
        return this.mean;
    }

    public float getMedian() {
        return this.median;
    }

    public float getStandardDeviation() {
        return this.std;
    }

    public ArtistStatistics(int artistid, float mean, float median, float std) {
        this.artistid = artistid;
        this.mean = mean;
        this.median = median;
        this.std = std;
    }

    public String toCsvLine() {
        return  "\"" + artistid + "\"," +
                "\"" + mean + "\"," +
                "\"" + median + "\"," +
                "\"" + std + "\"";
    }
}
