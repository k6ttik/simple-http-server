package tjv.server.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tjv.server.dto.UserDto;
import tjv.server.entity.Note;
import tjv.server.entity.Tag;
import tjv.server.entity.User;
import tjv.server.repository.UserRepository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

@Service
public class UserService extends AbstractService<User, Long, UserRepository>{
    private final NoteService noteService;
    private final TagService tagService;

    @Autowired
    public UserService(UserRepository repository, NoteService noteService, TagService tagService) {
        super(repository);
        this.noteService = noteService;
        this.tagService = tagService;
    }

    public Optional<User> findByEmail(String email) {
        return repository.findByEmail(email.trim().toLowerCase());
    }

    @Override
    public User create(User user) {
        if(user == null)
            throw new IllegalArgumentException("User cannot be null");
        if(!validateName(user.getFirstname()) || !validateName(user.getLastname()))
            throw new IllegalArgumentException("Invalid name");
        if(!validateEmail(user.getEmail()))
            throw new IllegalArgumentException("Invalid email");
        if(!validatePassword(user.getPassword()))
            throw new IllegalArgumentException("Invalid password");

        user.setEmail(user.getEmail().trim().toLowerCase());
        if(findByEmail(user.getEmail()).isPresent() )
            throw new IllegalArgumentException("Email already exists");

        return repository.save(user);
    }

    public User update(Long id, UserDto userDto) {
        User user = findById(id).orElseThrow(() -> new IllegalStateException("User not found"));
        if(!validateName(userDto.getFirstname()) || !validateName(user.getLastname()))
            throw new IllegalArgumentException("Invalid name");
        if(!validateEmail(userDto.getEmail()))
            throw new IllegalArgumentException("Invalid email");
        if(!validatePassword(userDto.getPassword()))
            throw new IllegalArgumentException("Invalid password");
        if(findByEmail(userDto.getEmail().trim().toLowerCase()).isPresent() )
            throw new IllegalArgumentException("Email already exists");

        user.setFirstname(userDto.getFirstname());
        user.setLastname(userDto.getLastname());
        user.setEmail(userDto.getEmail().trim().toLowerCase());
        user.setPassword(userDto.getPassword());

        Collection<Note> notes = new ArrayList<>();
        Collection<Tag> tags = new ArrayList<>();
        for(Long nid : userDto.getNotesId()){
            notes.add(noteService.findById(nid)
                    .orElseThrow(() -> new IllegalStateException("Note is not presented")));
        }
        for(Long tid : userDto.getTagsId()){
            tags.add(tagService.findById(tid)
                    .orElseThrow(() -> new IllegalStateException("Tag is not presented")));
        }

        user.setNotes(notes);
        user.setTags(tags);

        return repository.save(user);
    }

    private Boolean validateName(String name) {
        return name != null && !name.isEmpty();
    }

    private Boolean validateEmail(String email) {
        return email != null && !email.trim().isEmpty();
    }

    private Boolean validatePassword(String password) {
        return password != null && !password.isEmpty();
    }
}
