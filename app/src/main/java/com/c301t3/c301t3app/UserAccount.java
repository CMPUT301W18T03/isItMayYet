package com.c301t3.c301t3app;


/**
 * An account class to be used by the user
 *
 * @author Luis
 * @version 3.03
 * LastEdit: 2018-03-12; 3:00 PM by John
 */
public class UserAccount {
    private final static int MAX_USERNAME_LENGTH = 32; //subject to change

    private String username;
    private String firstName;
    private String lastName;
    private String emailAdd;
    private String address;
    private String phoneNum;



    public UserAccount(String username, String firstName, String lastName, String emailAdd,
                       String address, String phoneNum) throws IllegalArgumentException {
        this.setUsername(username);
        this.setFirstName(firstName);
        this.setLastName(lastName);
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

    public void setFirstName(String name) {
        this.firstName = name;
    }

    public void setLastName(String name) {
        this.lastName = name;
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

    public String getFirstName() {
        return this.firstName;
    }

    public String getLastName() {
        return this.lastName;
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
