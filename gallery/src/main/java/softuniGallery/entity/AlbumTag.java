package softuniGallery.entity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by George-Lenovo on 4/27/2017.
 */
@Entity
@Table(name = "albumTags")
public class AlbumTag {
    private Integer id;
    private String name;
    private Set<Album> albums;

    public AlbumTag() {
    }

    public AlbumTag(String name) {
        this.name = name;
        this.albums =  new HashSet<>();
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer getId() {

        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Column(unique = true, nullable = false)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @ManyToMany(mappedBy = "albumTags")
    public Set<Album> getAlbums() {
        return albums;
    }

    public void setAlbums(Set<Album> albums) {
        this.albums = albums;
    }


}
