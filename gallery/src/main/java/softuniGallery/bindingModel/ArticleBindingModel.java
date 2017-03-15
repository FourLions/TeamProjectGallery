package softuniGallery.bindingModel;


import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;

public class ArticleBindingModel {

    //new field to upload file
    private MultipartFile picture;

    @NotNull
    private String title;

    @NotNull
    private String content;

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

    // getter and setters for the file
    public MultipartFile getPicture() {
        return picture;
    }

    public void setPicture(MultipartFile picture) {
        this.picture = picture;
    }
}
