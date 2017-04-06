package softuniGallery.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import softuniGallery.entity.Tag;

/**
 * Created by Minko Vasilev on 4/6/2017.
 */
public interface TagRepository extends
        JpaRepository<Tag,Integer>{
    Tag findByName(String name);
}
