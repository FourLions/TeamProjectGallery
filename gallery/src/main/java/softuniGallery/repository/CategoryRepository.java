package softuniGallery.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import softuniGallery.entity.Category;

/**
 * Created by Minko Vasilev on 4/4/2017.
 */
public interface CategoryRepository
    extends JpaRepository<Category, Integer>{

}
