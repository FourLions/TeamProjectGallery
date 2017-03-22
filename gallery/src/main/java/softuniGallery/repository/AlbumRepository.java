package softuniGallery.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import softuniGallery.entity.Album;

/**
 * Created by George-Lenovo on 3/22/2017.
 */
public interface AlbumRepository extends JpaRepository<Album, Integer> {

}
