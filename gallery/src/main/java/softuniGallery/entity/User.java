package softuniGallery.entity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "user")
public class User {

    private Integer id;
    private String email;
    private String fullName;
    private String password;
    private Set<Role> roles;
    private Set<Article> articles;
    private Set<Album> albums;
    private Set<Link> links;
    private String town;
    private String country;
    private String telephoneNumber;
    private String information;
    private String profilePicture;

    public User(String email, String fullName, String password) {
        this.email = email;
        this.password = password;
        this.fullName = fullName;
        this.roles = new HashSet<>();
        this.articles = new HashSet<>();
        this.albums = new HashSet<>();
        this.links = new HashSet<>();
    }

    public User() {

    }

    @Column(name = "town")
    public String getTown() {
        return this.town;
    }

    public void setTown(String town) {
        this.town = town;
    }

    @Column(name = "country")
    public String getCountry() {
        return this.country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    @Column(name = "phoneNumber")
    public String getTelephoneNumber() {
        return this.telephoneNumber;
    }

    public void setTelephoneNumber(String telephoneNumber) {
        this.telephoneNumber = telephoneNumber;
    }

    @Column(columnDefinition = "text", name = "information")
    public String getInformation() {
        return this.information;
    }

    public void setInformation(String information) {
        this.information = information;
    }

    @Column(columnDefinition = "text", name = "profilePicture")
    public String getProfilePicture() {
        return this.profilePicture;
    }

    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }

    @OneToMany(mappedBy = "author")
    public Set<Link> getLinks() {
        return this.links;
    }

    public void setLinks(Set<Link> links) {
        this.links = links;
    }

    @OneToMany(mappedBy = "author")
    public Set<Album> getAlbums() {
        return this.albums;
    }

    public void setAlbums(Set<Album> albums) {
        this.albums = albums;
    }

    @OneToMany(mappedBy = "author")
    public Set<Article> getArticles() {
        return this.articles;
    }

    public void setArticles(Set<Article> articles) {
        this.articles = articles;
    }

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "users_roles")
    public Set<Role> getRoles() {
        return this.roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Column(name = "email", unique = true, nullable = false)
    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Column(name = "fullName", nullable = false)
    public String getFullName() {
        return this.fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    @Column(name = "password", length = 60, nullable = false)
    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void addRole(Role role) {
        this.roles.add(role);
    }

    @Transient
    public boolean isAdmin(){
        return this.getRoles()
                .stream()
                .anyMatch(role->role.getName().equals("ROLE_ADMIN"));
    }

    @Transient
    public boolean isAuthor(Article article){
        return Objects.equals(this.getId(),article.getAuthor().getId());
    }

    @Transient
    public boolean isAuthor(Link link) {
        return Objects.equals(this.getId(), link.getAuthor().getId());
    }

    @Transient
    public boolean isAuthor(Album album) {
        return Objects.equals(this.getId(), album.getAuthor().getId());
    }
}
