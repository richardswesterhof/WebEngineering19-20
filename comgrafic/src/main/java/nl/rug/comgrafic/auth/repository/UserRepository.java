package nl.rug.comgrafic.auth.repository;

import nl.rug.comgrafic.auth.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface UserRepository extends JpaRepository<User, Integer> {
    User findByUsername (String username);
}
