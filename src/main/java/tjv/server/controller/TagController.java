package tjv.server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import tjv.server.converter.NoteConverter;
import tjv.server.converter.TagConverter;
import tjv.server.dto.NoteDto;
import tjv.server.dto.TagDto;
import tjv.server.service.TagService;
import tjv.server.service.UserService;

import java.util.List;

@RestController
@RequestMapping("tag")
public class TagController {
    private final TagService tagService;
    private final TagConverter tagConverter;

    @Autowired
    public TagController(TagService tagService, TagConverter tagConverter) {
        this.tagService = tagService;
        this.tagConverter = tagConverter;
    }

    @PostMapping()
    public TagDto create(@RequestBody TagDto tagDto) {
        try{
            return tagConverter.toDto(
                    tagService.create(tagConverter.toEntity(tagDto))
            );
        }
        catch(Exception e){
            System.out.println(e.getMessage());
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        }
    }

    @DeleteMapping("{id}")
    void delete(@PathVariable Long id) {
        tagService.deleteById(id);
    }

    @GetMapping("user/{id}")
    public List<TagDto> getUserTags(@PathVariable Long id) {
        return tagConverter.toDtoList(tagService.getTagsByUserId(id));
    }
}
