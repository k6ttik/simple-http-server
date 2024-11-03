package tjv.server.dto;


import java.util.ArrayList;
import java.util.List;

public class UserDto {
    private Long id;
    private String firstname;
    private String lastname;
    private String email;
    private String password;
    private List<Long> notesId = new ArrayList<Long>();
    private List<Long> tagsId = new ArrayList<>();

    public UserDto() {
    }

    public UserDto(Long id, String firstname, String lastname, String email, String password) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.password = password;
    }

    public UserDto(
            Long id,
            String firstname,
            String lastname,
            String email,
            String password,
            List<Long> notesId,
            List<Long> tagsId) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.password = password;
        this.notesId = notesId;
        this.tagsId = tagsId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Long> getNotesId() {
        return notesId;
    }

    public void setNotesId(List<Long> notesId) {
        this.notesId = notesId;
    }

    public List<Long> getTagsId() {
        return tagsId;
    }

    public void setTagsId(List<Long> tagsId) {
        this.tagsId = tagsId;
    }
}
