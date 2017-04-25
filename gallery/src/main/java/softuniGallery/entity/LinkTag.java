package softuniGallery.entity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "linkTags")
public class LinkTag {

    private Integer id;
    private String name;
    private Set<Link> links;

    public LinkTag() {

    }

    public LinkTag(String name) {
        this.name = name;
        this.links = new HashSet<>();
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Column(unique = true, nullable = false)
    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @ManyToMany(mappedBy = "linkTags")
    public Set<Link> getLinks() {
        return this.links;
    }

    public void setLinks(Set<Link> links) {
        this.links = links;
    }
}
