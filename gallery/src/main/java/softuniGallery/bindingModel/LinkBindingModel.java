package softuniGallery.bindingModel;


import javax.validation.constraints.NotNull;

public class LinkBindingModel {

    @NotNull
    private String link;

    @NotNull
    private String content;

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
