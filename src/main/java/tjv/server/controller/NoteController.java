package tjv.server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import tjv.server.converter.NoteConverter;
import tjv.server.dto.NoteDto;
import tjv.server.entity.Note;
import tjv.server.service.NoteService;

import java.util.List;

@RestController
@RequestMapping("note")
public class NoteController {
    private final NoteService noteService;
    private final NoteConverter noteConverter;

    @Autowired
    public NoteController(NoteService noteService, NoteConverter noteConverter) {
        this.noteService = noteService;
        this.noteConverter = noteConverter;
    }

    @PostMapping
    NoteDto create(@RequestBody NoteDto noteDto) {
        try {
            return noteConverter.toDto(
                    noteService.create(noteConverter.toEntity(noteDto))
            );
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        }
    }

    @PutMapping("{id}")
    NoteDto update(@PathVariable Long id, @RequestBody NoteDto noteDto) {
        try {
            return noteConverter.toDto(noteService.updateNote(id, noteDto));
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        }
    }

    @DeleteMapping("{id}")
    void delete(@PathVariable Long id) {
        noteService.deleteById(id);
    }

    @PostMapping("{noteId}/tag/{tagId}")
    NoteDto addTagToNote(@PathVariable Long noteId, @PathVariable Long tagId) {
        try {
            return noteConverter.toDto(noteService.addTagToNote(noteId, tagId));
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        }
    }

    @DeleteMapping("{noteId}/tag/{tagId}")
    NoteDto removeTagFromNote(@PathVariable Long noteId, @PathVariable Long tagId) {
        try {
            return noteConverter.toDto(noteService.removeTagFromNote(noteId, tagId));
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        }
    }


    @GetMapping("{id}")
    NoteDto getNote(@PathVariable Long id) {
        Note note = noteService.findById(id).
                orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        return noteConverter.toDto(note);
    }

    @GetMapping("userList/{userId}")
    List<NoteDto> getUserNotes(@PathVariable Long userId) {
        try {
            return noteConverter.toDtoList(noteService.getNotesByUserId(userId));
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        }
    }

}
