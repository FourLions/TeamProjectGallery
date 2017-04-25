package softuniGallery.entity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "linkCategories")
public class LinkCategory {
    private Integer id;
    private String name;
    private Set<Link> links;


    public LinkCategory() {

    }

    public LinkCategory(String name) {
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

    @Column(nullable = false, unique = true)
    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @OneToMany(mappedBy = "linkCategory")
    public Set<Link> getLinks() {
        return this.links;
    }

    public void setLinks(Set<Link> links) {
        this.links = links;
    }
}
