package softuniGallery.entity;


import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;


@Entity
@Table(name = "albums")
public class Album {

    private Integer id;
    private String name;
    private User author;
    //private List<String> imagePathList;
    private String albumPicture;
    private List<ImageAlbum> imageAlbums;

    public Album(String name, User author) {
        this.author = author;
        this.name = name;
        this.imageAlbums = new LinkedList<>();
    }

    public Album() {

    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @OneToMany(mappedBy = "album")
    public List<ImageAlbum> getImageAlbums() {
        return this.imageAlbums;
    }

    public void setImageAlbums(List<ImageAlbum> imageAlbums) {
        this.imageAlbums = imageAlbums;
    }

    @Column(nullable = false)
    public String getAlbumPicture() {
        return this.albumPicture;
    }

    public void setAlbumPicture(String albumPicture) {
        this.albumPicture = albumPicture;
    }

    @Column(nullable = false)
    public String getName() {
        return this.name;
    }

    public void setName(String albumNameConstr) {
        this.name = albumNameConstr;
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
    public boolean userIsAuthor(Album albumP) {
        return Objects.equals(this.getAuthor().getId(), albumP.getAuthor().getId());

    }
    @Transient
    public boolean isAuthor(Integer author_id){
        return this.author.getAlbums()
                .stream()
                .anyMatch(role->role.getId() == author_id);
    }
}
