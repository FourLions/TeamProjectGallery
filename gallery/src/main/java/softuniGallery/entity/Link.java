package softuniGallery.entity;

import javax.persistence.*;

@Entity
@Table(name = "links")
public class Link {

    private Integer id;

    private String link;

    private String content;

    private User author;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Column(columnDefinition = "text", nullable = false)
    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    @Column(columnDefinition = "text", nullable = false)
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @ManyToOne()
    @JoinColumn(nullable = false, name = "authorId")
    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public Link(String link, String content, User author) {
        this.link = link;
        this.content = content;
        this.author = author;
    }

    public Link() {

    }
}
