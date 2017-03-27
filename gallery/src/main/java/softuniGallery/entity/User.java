package softuniGallery.entity;

import javax.persistence.*;
import java.util.HashSet;
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

    public User(String email, String fullName, String password) {
        this.email = email;
        this.password = password;
        this.fullName = fullName;
        this.roles = new HashSet<>();
        this.articles = new HashSet<>();
        this.albums = new HashSet<>();
    }

    public User() {

    }

    @OneToMany(mappedBy = "author")
    public Set<Album> getAlbums() {
        return albums;
    }

    public void setAlbums(Set<Album> albums) {
        this.albums = albums;
    }

    @OneToMany(mappedBy = "author")
    public Set<Article> getArticles() {
        return articles;
    }

    public void setArticles(Set<Article> articles) {
        this.articles = articles;
    }

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "users_roles")
    public Set<Role> getRoles() {
        return roles;
    }
    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    @Column(name = "email", unique = true, nullable = false)
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    @Column(name = "fullName", nullable = false)
    public String getFullName() {
        return fullName;
    }
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
    @Column(name = "password", length = 60, nullable = false)
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public void addRole(Role role) {
        this.roles.add(role);
    }
}
