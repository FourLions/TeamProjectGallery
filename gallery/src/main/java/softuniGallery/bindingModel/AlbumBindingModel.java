package softuniGallery.bindingModel;

import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import java.util.List;

public class AlbumBindingModel {

    private List<MultipartFile> pictures;

    @NotNull
    private MultipartFile picture;

    public MultipartFile getPicture() {
        return picture;
    }

    public void setPicture(MultipartFile picture) {
        this.picture = picture;
    }

    @NotNull
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String albumNameBind) {
        this.name = albumNameBind;
    }

    public List<MultipartFile> getPictures() {
        return pictures;
    }

    public void setPictures(List<MultipartFile> pictures) {
        this.pictures = pictures;
    }
}
