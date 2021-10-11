package mk.ukim.finki.emt.authentication.domain.repository;

import mk.ukim.finki.emt.authentication.domain.model.User;
import mk.ukim.finki.emt.authentication.domain.model.UserId;
import mk.ukim.finki.emt.authentication.domain.valueObjects.Password;
import org.apache.kafka.common.protocol.types.Field;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, UserId> {
    Optional<User> findByUsername(String username);

    Optional<User> findByUsernameAndPassword(String username, Password password);

    Boolean existsByUsername(String username);

}
