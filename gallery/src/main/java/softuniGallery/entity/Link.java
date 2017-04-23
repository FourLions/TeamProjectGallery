package softuniGallery.entity;

import javax.persistence.*;

@Entity
@Table(name = "links")
public class Link {

    private Integer id;

    private String link;

    private String content;

    private User author;

    @Transient
    private String linkSummary;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Column(columnDefinition = "text", nullable = false)
    public String getLink() {
        return this.link;
    }

    public void setLink(String link) {
        this.link = link;
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

    @Transient
    public String getLinkSummary() {
        return this.linkSummary;
    }

    public void setLinkSummary(String linkSummary) {

        this.linkSummary = linkSummary;
    }

    public Link(String link, String content, User author) {
        this.link = link;
        this.content = content;
        this.author = author;
    }

    public Link() {

    }

}
