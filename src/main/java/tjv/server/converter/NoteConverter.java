package tjv.server.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import tjv.server.dto.NoteDto;
import tjv.server.entity.Note;
import tjv.server.entity.Tag;
import tjv.server.entity.User;
import tjv.server.service.UserService;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Component
public class NoteConverter {
    private final UserService userService;

    @Autowired
    public NoteConverter(UserService userService) {
        this.userService = userService;
    }

    public Note toEntity(NoteDto noteDto) {
        User user = userService.findById(noteDto.getUserId())
                .orElseThrow(() -> new IllegalStateException("User is not presented"));
        return new Note(noteDto.getId(), noteDto.getTitle(), noteDto.getContent(), user);
    }

    public NoteDto toDto(Note note) {
        return new NoteDto(
                note.getId(),
                note.getTitle(),
                note.getContent(),
                note.getUser().getId(),
                note.getNoteTags().stream().map(Tag::getId).toList());
    }

    public List<Note> toEntityList(List<NoteDto> noteDtoList) {
        List<Note> notes = new ArrayList<>();
        for (NoteDto noteDto : noteDtoList) {
            notes.add(toEntity(noteDto));
        }
        return notes;
    }

    public List<NoteDto> toDtoList(List<Note> notes) {
        List<NoteDto> noteDtoList = new ArrayList<>();
        for (Note note : notes) {
            noteDtoList.add(toDto(note));
        }
        return noteDtoList;
    }
}
