package vn.com.hdbank.lunch_order.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import vn.com.hdbank.lunch_order.entity.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);

    boolean existsByUsername(String username);

    @Query("SELECT u FROM User u WHERE u.username = :username AND u.enabled = true")
    Optional<User> findActiveUserByUsername(String username);

    boolean existsByEmail(String email);

    List<User> findByNameContainingIgnoreCaseOrUsernameContainingIgnoreCase(
            String name, String username);
}
