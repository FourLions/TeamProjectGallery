package softuniGallery.bindingModel;

import javax.validation.constraints.NotNull;

public class UserBindingModel {
    @NotNull
    private String email;
    @NotNull
    private String fullName;
    @NotNull
    private String password;
    @NotNull
    private String confirmPassword;

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFullName() {
        return this.fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return this.confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }
}
