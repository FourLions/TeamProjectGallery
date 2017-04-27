package softuniGallery.bindingModel;


import javax.validation.constraints.NotNull;

public class LinkBindingModel {

    @NotNull
    private String link;

    @NotNull
    private String title;

    private Integer categoryId;

    private String tagString;

    public String getTagString() {
        return this.tagString;
    }

    public void setTagString(String tagString) {
        this.tagString = tagString;
    }

    public Integer getCategoryId() {
        return this.categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public String getLink() {
        return this.link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
