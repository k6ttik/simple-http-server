package tjv.server.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import tjv.server.dto.TagDto;
import tjv.server.entity.Tag;
import tjv.server.entity.User;
import tjv.server.service.UserService;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Component
public class TagConverter {
    private final UserService userService;

    @Autowired
    public TagConverter(UserService userService) {
        this.userService = userService;
    }

    public Tag toEntity(TagDto tagDto) {
        User user = userService.findById(tagDto.getUserId())
                .orElseThrow(() -> new IllegalStateException("User is not presented"));
        return new Tag(tagDto.getId(), tagDto.getName(), user);
    }

    public TagDto toDto(Tag tag) {
        return new TagDto(tag.getId(), tag.getName(), tag.getUser().getId());
    }

    public List<Tag> toEntityList(List<TagDto> tagDtoList) {
        List<Tag> tags = new ArrayList<>();
        for (TagDto tagDto : tagDtoList) {
            tags.add(toEntity(tagDto));
        }
        return tags;
    }

    public List<TagDto> toDtoList(List<Tag> tags) {
        List<TagDto> tagDtoList = new ArrayList<>();
        for (Tag tag : tags) {
            tagDtoList.add(toDto(tag));
        }
        return tagDtoList;
    }

}
