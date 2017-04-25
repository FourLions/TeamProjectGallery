package softuniGallery.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import softuniGallery.entity.Tag;


public interface TagRepository extends
        JpaRepository<Tag,Integer>{
    Tag findByName(String name);
}
