package softuniGallery.bindingModel;

import org.springframework.web.multipart.MultipartFile;
import softuniGallery.entity.ImageAlbum;

import javax.validation.constraints.NotNull;
import java.util.List;

public class AlbumBindingModel {

    @NotNull
    private String name;

    @NotNull
    private MultipartFile picture;

    private List<ImageAlbum> imageAlbums;

    private List<MultipartFile> pictures;

    public MultipartFile getPicture() {
        return this.picture;
    }

    public void setPicture(MultipartFile picture) {
        this.picture = picture;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String albumNameBind) {
        this.name = albumNameBind;
    }

    public List<MultipartFile> getPictures() {
        return this.pictures;
    }

    public void setPictures(List<MultipartFile> pictures) {
        this.pictures = pictures;
    }

    public List<ImageAlbum> getImageAlbums() {
        return this.imageAlbums;
    }

    public void setImageAlbums(List<ImageAlbum> imageAlbums) {
        this.imageAlbums = imageAlbums;
    }
}
