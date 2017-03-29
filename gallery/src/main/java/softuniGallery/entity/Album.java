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

    @ElementCollection //@Column(columnDefinition = "text", nullable = false)
    // tui mai trqbwa da e relaciq nqkakva - i da sochi kam drugata tablica
    // edin album kolko imagePathList-a moje da ima// ами аз гледам от нета и каквото скалъпя не знам дали е правилно. С една снимка става но с много
    // znachiii ideqta e che tuk pazish wsichki snimki kam albuma, taka li?
    // уж ..
    // I see .. ok, ami ne go znam towa ElementCollection kakwo prawi, ama mi se struwa che nishto :D
    // znachi towa koeto moga da gi predloja e da naprawish Entity "Image" koeto si ima "Path" i "Album"
    // A w Album-a da sojish HashSet<Image> images ... podobno na towa ot bloga, kadeto edin Article si ima Category,
    // a pak Category-qta si ima HashSet<Article> articles
    // ще пробвам на ново. Иначе това ElementCollection ми направи листа мисля в базата. hmmm mi dai da widim neshto
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
    public boolean userIsAuthor(Album album) {
        return Objects.equals(this.getId(), album.getAuthor().getId());
    }
}
