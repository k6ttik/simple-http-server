package tjv.server.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tjv.server.entity.Tag;
import tjv.server.entity.User;
import tjv.server.repository.TagRepository;

import java.util.List;

@Service
public class TagService extends AbstractService<Tag, Long, TagRepository> {
    private final UserService userService;

    @Autowired
    public TagService(TagRepository repository, UserService userService) {
        super(repository);
        this.userService = userService;
    }

    @Override
    public Tag create(Tag tag) {
        if(tag == null)
            throw new IllegalArgumentException("Tag cannot be null");
        if(!validateName(tag.getName()))
            throw new IllegalArgumentException("Invalid name");

        tag.setName(tag.getName().toLowerCase());
        if(repository.findByName(tag.getName()).isPresent())
            throw new IllegalArgumentException("Tag already exists");

        return repository.save(tag);
    }

    public List<Tag> getTagsByUserId(Long id) {
        User user = userService.findById(id).orElseThrow(
                () -> new IllegalArgumentException("User not found")
        );
        return repository.findByUser(user);
    }

    private Boolean validateName(String name) {
        return name != null && !name.isEmpty() && !name.contains(" ");
    }
}
