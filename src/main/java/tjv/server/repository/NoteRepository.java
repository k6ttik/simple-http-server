package tjv.server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tjv.server.entity.Note;
import tjv.server.entity.Tag;
import tjv.server.entity.User;

import java.util.List;

public interface NoteRepository extends JpaRepository<Note, Long> {
    List<Note> findByUser(User user);
}
