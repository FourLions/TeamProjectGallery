package softuniGallery.bindingModel;

import javax.validation.constraints.NotNull;

/**
 * Created by George-Lenovo on 3/22/2017.
 */
public class AlbumBindingModel {

    @NotNull
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String albumNameBind) {
        this.name = albumNameBind;
    }
}
