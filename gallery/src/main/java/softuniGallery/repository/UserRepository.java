package softuniGallery.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import softuniGallery.entity.User;

public interface UserRepository extends JpaRepository<User, Integer> {

    User findByEmail(String email);
}
