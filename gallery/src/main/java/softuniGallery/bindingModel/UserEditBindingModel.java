package softuniGallery.bindingModel;

import java.util.ArrayList;
import java.util.List;

public class UserEditBindingModel extends UserBindingModel {
    private List<Integer> roles;

    public UserEditBindingModel() {
        this.roles = new ArrayList<>();
    }

    public List<Integer> getRoles() {
        return this.roles;
    }

    public void setRoles(List<Integer> roles) {
        this.roles = roles;
    }
}