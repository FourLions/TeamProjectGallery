package softuniGallery.entity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "links")
public class Link {

    private Integer id;

    private String link;

    private String title;

    private User author;

    private LinkCategory linkCategory;

    private Set<LinkTag> linkTags;

    @Transient
    private String linkSummary;

    public Link(String link, String title, User author, LinkCategory linkCategory, HashSet<LinkTag> linkTags) {
        this.link = link;
        this.title = title;
        this.author = author;
        this.linkCategory = linkCategory;
        this.linkTags = linkTags;
    }

    public Link() {

    }

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

    @Column(nullable = false)
    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @ManyToOne()
    @JoinColumn(nullable = false, name = "authorId")
    public User getAuthor() {
        return this.author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    @ManyToOne()
    @JoinColumn(nullable = false, name = "categoryId")
    public LinkCategory getLinkCategory() {
        return this.linkCategory;
    }

    public void setLinkCategory(LinkCategory linkCategory) {
        this.linkCategory = linkCategory;
    }

    @ManyToMany()
    @JoinColumn(table = "links_tags")
    public Set<LinkTag> getLinkTags() {
        return this.linkTags;
    }

    public void setLinkTags(Set<LinkTag> linkTags) {
        this.linkTags = linkTags;
    }

    @Transient
    public String getLinkSummary() {
        return this.linkSummary;
    }

    public void setLinkSummary(String linkSummary) {

        this.linkSummary = linkSummary;
    }
}
