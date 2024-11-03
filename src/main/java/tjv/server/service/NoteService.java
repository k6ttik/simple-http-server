package tjv.server.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tjv.server.dto.NoteDto;
import tjv.server.entity.Note;
import tjv.server.entity.Tag;
import tjv.server.entity.User;
import tjv.server.repository.NoteRepository;
import tjv.server.repository.TagRepository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class NoteService extends AbstractService<Note, Long, NoteRepository> {
    private final UserService userService;
    private final TagService tagService;
    private final TagRepository tagRepository;

    @Autowired
    public NoteService(NoteRepository repository, UserService userService, TagService tagService, TagRepository tagRepository) {
        super(repository);
        this.userService = userService;
        this.tagService = tagService;
        this.tagRepository = tagRepository;
    }

    @Override
    public Note create(Note note) {
        if(note == null)
            throw new IllegalArgumentException("Note cannot be null");
        if(!validateTitle(note.getTitle()))
            throw new IllegalArgumentException("Invalid title");
        return repository.save(note);
    }

    public List<Note> getNotesByUserId(Long id) {
        User user = userService.findById(id).orElseThrow(
                () -> new IllegalArgumentException("User not found")
        );
        return repository.findByUser(user);
    }

    public Note addTagToNote(Long noteId, Long tagId) {
        Note note = findById(noteId)
                .orElseThrow(() -> new IllegalArgumentException("Note not found"));
        Tag tag = tagService.findById(tagId)
                .orElseThrow(() -> new IllegalArgumentException("Tag not found"));

        note.getNoteTags().add(tag);
        tag.getNotesWithThisTag().add(note);

        tagRepository.save(tag);
        return repository.save(note);
    }

    public Note removeTagFromNote(Long noteId, Long tagId) {
        Note note = findById(noteId)
                .orElseThrow(() -> new IllegalArgumentException("Note not found"));
        Tag tag = tagService.findById(tagId)
                .orElseThrow(() -> new IllegalArgumentException("Tag not found"));
        note.getNoteTags().remove(tag);
        tag.getNotesWithThisTag().remove(note);

        tagRepository.save(tag);
        return repository.save(note);
    }

    public Note updateNote(Long noteId, NoteDto noteDto) {
        Note note = findById(noteId).orElseThrow(() -> new IllegalArgumentException("Note not found"));
        note.setTitle(noteDto.getTitle());
        note.setContent(noteDto.getContent());
        Collection<Tag> tagsColl = new ArrayList<>();
        for(Long id: noteDto.getTagsId()){
            Tag tag = tagService.findById(id)
                    .orElseThrow(() -> new IllegalStateException("Tag is not presented"));
            tagsColl.add(tag);
        }
        note.setNoteTags(tagsColl);

        return repository.save(note);
    }

    private Boolean validateTitle(String title) {
        return title != null && !title.isEmpty();
    }


}
