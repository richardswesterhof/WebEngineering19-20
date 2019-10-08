package nl.rug.shamzam.Repository;

import nl.rug.shamzam.Model.Release;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReleaseRepository extends JpaRepository<Release, Integer> {
}
