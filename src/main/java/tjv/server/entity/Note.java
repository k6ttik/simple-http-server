package tjv.server.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;

@Entity
public class Note implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column()
    private String content;

    @ManyToOne
    private User user;

    @ManyToMany(mappedBy = "notesWithThisTag")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Collection<Tag> noteTags = new HashSet<>();

    public Note() {
    }

    public Note(Long id, String title, String content, User user) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.user = user;
    }

    public Note(Long id, String title, String content, User user, Collection<Tag> noteTags) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.user = user;
        this.noteTags = noteTags;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Collection<Tag> getNoteTags() {
        return noteTags;
    }

    public void setNoteTags(Collection<Tag> noteTags) {
        this.noteTags = noteTags;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Note note = (Note) o;
        return Objects.equals(id, note.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

}
