package softuniGallery.entity;

import org.springframework.util.StringUtils;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "roles")
public class Role {

    private Integer id;
    private String name;
    private Set<User> users;

    public Role() {
        this.users = new HashSet<>();
    }

    @ManyToMany(mappedBy = "roles")
    public Set<User> getUsers() {
        return this.users;
    }
    public void setUsers(Set<User> users) {
        this.users = users;
    }
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer getId() {
        return this.id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    @Column(name = "name", nullable = false)
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }

    @Transient
    public String getSimpleName() {
        return StringUtils.capitalize(this.getName().substring(5).toLowerCase());
    }

    @Transient
    public boolean isTwo(int id) {
        return id == 2;
    }
}
