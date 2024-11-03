package tjv.server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tjv.server.entity.Tag;
import tjv.server.entity.User;

import java.util.List;
import java.util.Optional;

public interface TagRepository extends JpaRepository<Tag, Long> {
    List<Tag> findByUser(User user);
    Optional<Tag> findByName(String name);
}
