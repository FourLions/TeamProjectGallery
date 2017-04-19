package softuniGallery.entity;

import javax.persistence.*;

@Entity
@Table(name = "imageAlbum")
public class ImageAlbum {

    private Integer id;
    private String path;
    private Album album;

    public ImageAlbum(String path, Album album) {
        this.path = path;
        this.album = album;
    }

    public ImageAlbum() {
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
    public String getPath() {
        return this.path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    @ManyToOne
    public Album getAlbum() {
        return this.album;
    }

    public void setAlbum(Album album) {
        this.album = album;
    }
}
