package softuniGallery.bindingModel;

import org.springframework.web.multipart.MultipartFile;
import softuniGallery.entity.ImageAlbum;

import javax.validation.constraints.NotNull;
import java.util.List;

public class AlbumBindingModel {

    private List<MultipartFile> pictures;

    @NotNull
    private String name;

    @NotNull
    private MultipartFile picture;


    public void setPictures(List<MultipartFile> pictures) {
        this.pictures = pictures;
    }

    public List<MultipartFile> getPictures() {
        return this.pictures;
    }

    public MultipartFile getPicture() {
        return this.picture;
    }

    public void setPicture(MultipartFile picture) {
        this.picture = picture;
    }

    //private List<ImageAlbum> imageAlbums;

    public String getName() {
        return this.name;
    }

    public void setName(String albumNameBind) {
        this.name = albumNameBind;
    }




//    //public List<ImageAlbum> getImageAlbums() {
//        return this.imageAlbums;
//    }

//    public void setImageAlbums(List<ImageAlbum> imageAlbums) {
//        this.imageAlbums = imageAlbums;
//    }
}
