package softuniGallery.bindingModel;

import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;

public class ArticleBindingModel {
    @NotNull
    private MultipartFile picture;

    @NotNull
    private String title;

    @NotNull
    private String content;

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return this.content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public MultipartFile getPicture() {
        return this.picture;
    }

    public void setPicture(MultipartFile picture) {
        this.picture = picture;
    }

    private Integer categoryId;
    public Integer getCategoryId(){
        return this.categoryId;
    }
    public void setCategoryId(Integer categoryId){
        this.categoryId = categoryId;
    }

    private String tagString;

    public String getTagString(){return this.tagString;}
    public void setTagString(String tagString){this.tagString=tagString;}
}
