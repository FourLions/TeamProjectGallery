package softuniGallery.entity;

import javax.persistence.*;
import java.util.List;

/**
 * Created by George-Lenovo on 3/22/2017.
 */

@Entity
@Table(name = "albums")
public class Album {

    private Integer id;
    private String name;
    private User author;
    // private List<Article> albumArticlesContainerList;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Album() {

    }

    public Album(User author) {
        //this.name = albumNameConstr;
        this.author = author;
    }

    /*public Album(String albumNameConstr, User author) {
        this.name = albumNameConstr;
        this.author = author;
    }*/

    @Column(nullable = false)
    public String getName() {
        return name;
    }

    public void setName(String albumNameConstr) {
        this.name = albumNameConstr;
    }

    @ManyToOne()
    @JoinColumn(nullable = false, name = "authorId")
    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }



  /*public List<Article> getAlbumArticlesContainerList() {
        return albumArticlesContainerList;
    }
    public void setAlbumArticlesContainerList(List<Article> albumArticlesContainerList) {
        this.albumArticlesContainerList = albumArticlesContainerList;
    }*/
}
