package nl.rug.shamzam.Model.outsideModels;

import nl.rug.shamzam.Model.Song;

public class SongPopularity {
    private String title;
    private String artistName;
    private float duration;
    private int year;
    private String linkToSong;
    private String linkToArtist;

    public static String columnames = "\"title\", \"artistName\", \"duration\", \"year\", \"linkToSong\", \"linkToArtist\"\n";

    public SongPopularity(Song s){
        this.title = s.getTitle();
        this.artistName = s.getArtistName();
        this.duration = s.getDuration();
        this.year = s.getYear();
        this.linkToSong = s.getLink();
        this.linkToArtist = s.getArtistLink();
    }

    public String getTitle(){return title;}
    public String getArtistName() {
        return artistName;
    }
    public float getDuration() {
        return duration;
    }
    public int getYear() {
        return year;
    }
    public String getLinkToSong() {
        return linkToSong;
    }
    public String getLinkToArtist() {
        return linkToArtist;
    }

    public SongPopularity(){}

    public String toCsvLine(){
        return "\"" + title + "\"," +
                "\"" + artistName + "\"," +
                duration + "," +
                year + "," +
                "\"" + linkToSong + "\"," +
                "\"" + linkToArtist + "\"\n";
    }
}