package com.c301t3.c301t3app;



public class UserAccount {
    private final static int MAX_USERNAME_LENGTH = 8; //subject to change

    private String username;
    private String fullName;
    private String emailAdd;
    private String address;
    private String phoneNum;

    public UserAccount() { // empty constructor

    }

    public UserAccount(String username, String fullName, String emailAdd, String address, String phoneNum) throws IllegalArgumentException {
        this.setUsername(username);
        this.setFullName(fullName);
        this.setEmailAdd(emailAdd);
        this.setAddress(address);
        this.setPhoneNum(phoneNum);
    }

    public void setUsername(String username) throws IllegalArgumentException {
        if (username.length()==0) {
            throw new IllegalArgumentException("Error: username cannot be left blank");
        }

        else if (username.length() > MAX_USERNAME_LENGTH) {
            throw new IllegalArgumentException("Error: username must be "
                    + String.valueOf(MAX_USERNAME_LENGTH) + " characters or less.");
        }

        else { // legal entry
            this.username = username;
        }

    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setEmailAdd(String emailAdd) {
        this.emailAdd = emailAdd;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setPhoneNum(String phoneNum) {
        //TODO: illegal String entry
        this.phoneNum = phoneNum;
    }

    public String getUsername() {
        return this.username;
    }

    public String getFullName() {
        return this.fullName;
    }

    public String getEmailAdd() {
        return this.emailAdd;
    }

    public String getAddress() {
        return this.address;
    }

    public String getPhoneNum() {
        return this.phoneNum;
    }

}
