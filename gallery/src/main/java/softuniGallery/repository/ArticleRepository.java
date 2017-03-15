package softuniGallery.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import softuniGallery.entity.Article;

public interface ArticleRepository extends JpaRepository<Article, Integer>{

}
