package com.c301t3.c301t3app;


/**
 * An account class to be used by the user, including username, firstName, lastName,
 * emailAddress, address, and phoneNumber.
 * When a user registers their account, this is the type of object it is
 *
 * NOTE:
 * ASSUMES SCREEN WILL REQUIRE ALL FIELDS TO BE FILLED OUT
 * BLANK ENTRIES CANNOT BE ALLOWED
 *
 * @author Luis
 * @version 3.03
 * EditLog: 2018-03-12; 3:00 PM by John
 */
public class UserAccount {
    // Maximum length for a username is currently 32
    private final static int MAX_USERNAME_LENGTH = 32;
    // Number of registered users
    public static int userCount = 0; // used for setting userID of new user to userID + 1
    // All the variables that hold data
    private String username;
    private String firstName;
    private String lastName;
    private String emailAdd;
    private String address;
    private String phoneNum;
    private String password;
    private int uniqueID;


    // default constructor
    public UserAccount() {
        this.username = "";
        this.firstName = "";
        this.lastName = "";
        this.emailAdd = "";
        this.address = "";
        this.phoneNum = "";
        this.password = "";
        this.uniqueID = -1;
    }
    /**
     * Creating a new UserAccount includes all the following parameters
     *
     * @param username; user name of the account
     * @param firstName; first name of the user
     * @param lastName; last name of the user
     * @param emailAdd; email address of the user, will have verification later on
     * @param address; address of the user
     * @param phoneNum; phone number of the user, with 10 digits long
     * @param password; password for the user.
     * @throws IllegalArgumentException; when username is longer than allowed
     */
    public UserAccount(String username, String firstName, String lastName, String emailAdd,
                       String address, String phoneNum, String password, int id)
            throws IllegalArgumentException {
        this.setUsername(username);
        this.setFirstName(firstName);
        this.setLastName(lastName);
        this.setEmailAdd(emailAdd);
        this.setAddress(address);
        this.setPhoneNum(phoneNum);
        this.setPassword(password);
        this.uniqueID = id;
    }

    /**  Set the username of the user
     *
     * @param username; username of the user
     * @throws IllegalArgumentException; when username is greater than 32
     */
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

    /** Set the firstName of the user
     *
     * @param name; name of the user
     */
    public void setFirstName(String name){
        this.firstName = name;
    }

    /** set the lastName of the user
     *
     * @param name; lastName of the user
     */
    public void setLastName(String name) {
        this.lastName = name;
    }

    /** Set the email of the user
     *
     * @param emailAdd; email of the user
     */
    public void setEmailAdd(String emailAdd) {
        this.emailAdd = emailAdd;
    }

    /** Set the address of the user
     *
     * @param address; address of the user
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /** Set the phonenumber of the user
     *
     * @param phoneNum; phone number of the user
     */
    public void setPhoneNum(String phoneNum) {
        //TODO: illegal String entry; setby who?
        this.phoneNum = phoneNum;
    }
    /** Set the password of the user
     *
     * @param password; password of the user
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /** Set the uniqueID of the user
     *
     * @param uniqueID; ID of the user
     */
    public void setID(int uniqueID) {
        this.uniqueID = uniqueID;
    }



    /**  Return the username of the user
     *
     * @return Username; the username of the user
     */
    public String getUsername() {
        return this.username;
    }

    /** Return the first name of the user
     *
     * @return FirstName; first name of the user
     */
    public String getFirstName() {
        return this.firstName;
    }

    /** Returns the last name of the user
     *
     * @return Last Name; the last name of the user
     */
    public String getLastName() {
        return this.lastName;
    }

    /** Returns the email of the user
     *
     * @return EmailAdd; email of the user
     */
    public String getEmailAdd() {
        return this.emailAdd;
    }

    /** Returns teh address of the user
     *
     * @return Address; the address of the user
     */
    public String getAddress() {
        return this.address;
    }

    /** Returns the phone number of the user
     *
     * @return PhoneNum; the phone number of the user
     */
    public String getPhoneNum() {
        return this.phoneNum;
    }

    /** Returns the password of the user
     *
     * @return password; the phone number of the user
     */
    public String getPassword() { return this.password; }

    /** Returns teh unique ID of the account
     *
     * @return uniqueID; the unique ID of the account
     */
    public int getID() {
        return this.uniqueID;
    }

}
