package tjv.server.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.io.Serializable;
import java.util.*;

@Entity
public class Tag implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @ManyToOne
    private User user;

    @ManyToMany
    @JoinTable(
            name = "note_tag",
            joinColumns = @JoinColumn(name = "tag_id"),
            inverseJoinColumns = @JoinColumn(name = "note_id")
    )
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Collection<Note> notesWithThisTag = new HashSet<>();

    public Tag() {

    }

    public Tag(Long id, String name, User user) {
        this.id = id;
        this.name = name;
        this.user = user;
    }

    public Tag(Long id, String name, User user, Collection<Note> notesWithThisTag) {
        this.id = id;
        this.name = name;
        this.user = user;
        this.notesWithThisTag = notesWithThisTag;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Collection<Note> getNotesWithThisTag() {
        return notesWithThisTag;
    }

    public void setNotesWithThisTag(Collection<Note> notesWithThisTag) {
        this.notesWithThisTag = notesWithThisTag;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tag tag = (Tag) o;
        return Objects.equals(id, tag.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
