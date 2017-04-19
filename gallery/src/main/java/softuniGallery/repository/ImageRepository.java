package softuniGallery.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import softuniGallery.entity.ImageAlbum;


public interface ImageRepository extends JpaRepository<ImageAlbum, Integer> {

}
