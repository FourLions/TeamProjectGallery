package softuniGallery.entity;

import org.hibernate.annotations.Generated;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;


@Entity
@Table(name = "tags")
public class Tag {
    private Integer id;
    private String name;
    private Set<Article> articles;

    public Tag(){    }

    public Tag(String name){
        this.name=name;
        this.articles=new HashSet<>();
    }
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer getId(){return this.id;}

    public void setId(Integer id){this.id=id;}
    @Column(unique = true, nullable = false)
    public String getName(){return this.name;}

    public void setName(String name){this.name = name;}
    @ManyToMany(mappedBy = "tags")
    public Set<Article> getArticles(){return this.articles;}

    public void setArticles(Set<Article> articles){this.articles=articles;}
}
