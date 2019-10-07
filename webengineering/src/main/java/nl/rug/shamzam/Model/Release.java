package nl.rug.shamzam.Model;

import javax.persistence.*;

@Entity
public class Release {
    @Id
    private int id;
    private String name;
}
