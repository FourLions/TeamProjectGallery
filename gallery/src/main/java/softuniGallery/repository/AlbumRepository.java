package softuniGallery.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import softuniGallery.entity.Album;


public interface AlbumRepository extends JpaRepository<Album, Integer> {

}
