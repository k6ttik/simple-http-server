package tjv.server.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import tjv.server.dto.UserDto;
import tjv.server.entity.Note;
import tjv.server.entity.Tag;
import tjv.server.entity.User;
import tjv.server.service.NoteService;
import tjv.server.service.TagService;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Component
public class UserConverter {
    private final NoteService noteService;
    private final TagService tagService;

    @Autowired
    public UserConverter(NoteService noteService, TagService tagService) {
        this.noteService = noteService;
        this.tagService = tagService;
    }

    public User toEntity(UserDto userDto) {
        Collection<Note> notes = new ArrayList<>();
        Collection<Tag> tags = new ArrayList<>();

        for(Long id : userDto.getNotesId()){
            notes.add(noteService.findById(id)
                    .orElseThrow(() -> new IllegalStateException("Note is not presented")));
        }

        for(Long id : userDto.getTagsId()){
            tags.add(tagService.findById(id)
                    .orElseThrow(() -> new IllegalStateException("Tag is not presented")));
        }

        return new User(userDto.getId(), userDto.getFirstname(), userDto.getLastname(),
                userDto.getEmail(), userDto.getPassword(), notes, tags);
    }

    public UserDto toDto(User user) {
        return new UserDto(
                user.getId(),
                user.getFirstname(),
                user.getLastname(),
                user.getEmail(),
                user.getPassword(),
                user.getNotes().stream().map(Note::getId).toList(),
                user.getTags().stream().map(Tag::getId).toList()
        );
    }

    public List<User> toEntityList(List<UserDto> userDtoList) {
        List<User> userList = new ArrayList<>();
        for (UserDto userDto : userDtoList) {
            userList.add(toEntity(userDto));
        }
        return userList;
    }

    public List<UserDto> toDtoList(List<User> userList) {
        List<UserDto> userDtoList = new ArrayList<>();
        for (User user : userList) {
            userDtoList.add(toDto(user));
        }
        return userDtoList;
    }
}
