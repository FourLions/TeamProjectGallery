package softuniGallery.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import softuniGallery.entity.LinkTag;

public interface LinkTagRepository extends JpaRepository<LinkTag, Integer> {
    LinkTag findByName(String name);
}
