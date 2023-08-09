package model;

public class WelcomeForm {
    private String txtPassword;

    public WelcomeForm(String txtPassword) {
        this.txtPassword = txtPassword;
    }

    public String getTxtPassword() {
        return txtPassword;
    }

    public void setTxtPassword(String txtPassword) {
        this.txtPassword = txtPassword;
    }
}
