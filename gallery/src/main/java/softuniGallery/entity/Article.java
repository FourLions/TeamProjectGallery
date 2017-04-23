package softuniGallery.entity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "articles")
public class Article {

    private Integer id;
    private String title;
    private String content;
    private User author;
    private Category category;
    private String imagePath;
    private Set<Tag> tags;

    public Article(String title, String content, User author, Category category, HashSet<Tag> tags) {
        this.title = title;
        this.content = content;
        this.author = author;
        this.category= category;
        this.tags = tags;
    }

    public Article() {

    }
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Column(nullable = false)
    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Column(columnDefinition = "text", nullable = false)
    public String getContent() {
        return this.content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @ManyToOne()
    @JoinColumn(nullable = false, name = "authorId")
    public User getAuthor() {
        return this.author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    @Column(columnDefinition = "text", nullable = false)
    public String getImagePath() {
        return this.imagePath;
    }


    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    @Transient
    public String getSummary() {
        return this.getContent().substring(0, this.getContent().length() / 2) + "...";
    }

    @ManyToOne()
    @JoinColumn(nullable = false, name ="categoryId")
    public Category getCategory(){return this.category;}
    public void setCategory(Category category){this.category=category;}

    @ManyToMany()
    @JoinColumn(table="articles_tags")
    public Set<Tag> getTags(){return this.tags;}
    public void setTags(Set<Tag> tags){this.tags=tags;}
}
