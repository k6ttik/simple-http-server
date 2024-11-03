package tjv.server.dto;

import java.util.ArrayList;
import java.util.Collection;

public class NoteDto {

    private Long id = 0L;
    private String title;
    private String content;
    private Long userId;
    private Collection<Long> tagsId = new ArrayList<Long>();

    public NoteDto() {
    }

    public NoteDto(Long id, String title, String content, Long userId) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.userId = userId;
    }

    public NoteDto(Long id, String title, String content, Long userId, Collection<Long> tagsId) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.userId = userId;
        this.tagsId = tagsId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Collection<Long> getTagsId() {
        return tagsId;
    }

    public void setTagsId(Collection<Long> tagsId) {
        this.tagsId = tagsId;
    }
}
