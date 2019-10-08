package nl.rug.shamzam.Model;

import javax.persistence.*;

@Entity
@Table(name = "releases")
public class Release {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int dbId;

    private int id;
    private String name;
}
