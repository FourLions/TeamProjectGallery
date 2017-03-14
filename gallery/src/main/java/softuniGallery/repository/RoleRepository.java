package softuniGallery.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import softuniGallery.entity.Role;

public interface RoleRepository extends JpaRepository<Role, Integer> {

    Role findByName(String name);
}
