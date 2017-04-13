package softuniGallery.entity;


import javax.persistence.*;
import java.util.List;
import java.util.Objects;


@Entity
@Table(name = "albums")
public class Album {

    private Integer id;
    private String name;
    private User author;
    private List<String> imagePathList;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Album(String name, User author) {
        this.author = author;
        this.name = name;
    }

    public Album() {

    }

    @ElementCollection
    public List<String> getImagePathList() {
        return imagePathList;
    }

    public void setImagePathList(List<String> imagePathList) {
        this.imagePathList = imagePathList;
    }

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

    @Transient
    public boolean userIsAuthor(Album albumP) {
        return Objects.equals(this.getAuthor().getId(), albumP.getAuthor().getId());

    }
    @Transient
    public boolean isAuthor(Integer author_id){
        return this.author.getAlbums()
                .stream()
                .anyMatch(role->role.getId() == author_id);//.getName().equals("ROLE_ADMIN"));
    }
}
