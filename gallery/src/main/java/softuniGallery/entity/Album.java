package softuniGallery.entity;


import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import java.util.*;


@Entity
@Table(name = "albums")
public class Album {

    private Integer id;
    private String name;
    private User author;
    private AlbumCategory albumCategory;
    private String albumPicture;
    private List<ImageAlbum> imageAlbums;

    @ManyToMany()
    @JoinColumn(table = "albums_tags")
    public Set<AlbumTag> getAlbumTags() {
        return albumTags;
    }

    public void setAlbumTags(Set<AlbumTag> albumTags) {
        this.albumTags = albumTags;
    }

    private Set<AlbumTag> albumTags;

    public Album(String name, User author, AlbumCategory albumCategory, HashSet<AlbumTag> albumTags) {
        this.author = author;
        this.name = name;
        this.imageAlbums = new LinkedList<>();
        this.albumCategory = albumCategory;
        this.albumTags = albumTags;
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

    @ManyToOne()
    @JoinColumn(nullable = false, name = "categoryId")
    public AlbumCategory getAlbumCategory() {
        return albumCategory;
    }

    public void setAlbumCategory(AlbumCategory albumCategory) {
        this.albumCategory = albumCategory;
    }
}
