package com.isa.projekcije.model.dto;

public class ChangePasswordDTO {
    private String currentPassword;
    private String password;
    private String passwordConfirm;

    public ChangePasswordDTO() {
    }

    public ChangePasswordDTO(String currentPassword, String password, String passwordConfirm) {
        this.currentPassword = currentPassword;
        this.password = password;
        this.passwordConfirm = passwordConfirm;
    }

    public String getCurrentPassword() {
        return currentPassword;
    }

    public void setCurrentPassword(String currentPassword) {
        this.currentPassword = currentPassword;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPasswordConfirm() {
        return passwordConfirm;
    }

    public void setPasswordConfirm(String passwordConfirm) {
        this.passwordConfirm = passwordConfirm;
    }
}
