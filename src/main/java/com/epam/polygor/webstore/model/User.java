package com.epam.polygor.webstore.model;

import java.sql.Date;

public class User extends BaseEntity {
    private String login;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private Date birth;
    private Role role;
    private String city;
    private String country;
    private String address;
    private String postcode;
    private String phone;
    private boolean banned;

    public User(Long id) {
        super(id);
    }

    public User(Long id, String address, Role role, String postcode, String phone, String password, String login, String lastName, String firstName, String email, String country, Date birth, String city, boolean banned) {
        super(id);
        this.address = address;
        this.role = role;
        this.postcode = postcode;
        this.phone = phone;
        this.password = password;
        this.login = login;
        this.lastName = lastName;
        this.firstName = firstName;
        this.email = email;
        this.country = country;
        this.birth = birth;
        this.city = city;
        this.banned = banned;
    }

    public User() {

    }

    public String getAddress() {
        return address;
    }

    public User setAddress(String address) {
        this.address = address; return this;
    }

    public boolean isBanned() {
        return banned;
    }

    public User setBanned(boolean banned) {
        this.banned = banned; return this;
    }

    public Date getBirth() {
        return birth;
    }

    public User setBirth(Date birth) {
        this.birth = birth; return this;
    }

    public String getCity() {
        return city;
    }

    public User setCity(String city) {
        this.city = city; return this;
    }

    public String getCountry() {
        return country;
    }

    public User setCountry(String country) {
        this.country = country; return this;
    }

    public String getEmail() {
        return email;
    }

    public User setEmail(String email) {
        this.email = email; return this;
    }

    public String getFirstName() {
        return firstName;
    }

    public User setFirstName(String firstName) {
        this.firstName = firstName; return this;
    }

    public String getLastName() {
        return lastName;
    }

    public User setLastName(String lastName) {
        this.lastName = lastName; return this;
    }

    public String getLogin() {
        return login;
    }

    public User setLogin(String login) {
        this.login = login; return this;
    }

    public String getPassword() {
        return password;
    }

    public User setPassword(String password) {
        this.password = password; return this;
    }

    public String getPhone() {
        return phone;
    }

    public User setPhone(String phone) {
        this.phone = phone; return this;
    }

    public String getPostcode() {
        return postcode;
    }

    public User setPostcode(String postcode) {
        this.postcode = postcode; return this;
    }

    public Role getRole() {
        return role;
    }

    public User setRole(Role role) {
        this.role = role; return this;
    }

    @Override
    public String toString() {
        return "User{" +
                "address='" + address + '\'' +
                ", login='" + login + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", birth=" + birth +
                ", role=" + role +
                ", city='" + city + '\'' +
                ", country='" + country + '\'' +
                ", postcode='" + postcode + '\'' +
                ", phone='" + phone + '\'' +
                ", banned=" + banned +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        if (!super.equals(o)) return false;

        User user = (User) o;

        if (banned != user.banned) return false;
        if (address != null ? !address.equals(user.address) : user.address != null) return false;
        if (birth != null ? !birth.equals(user.birth) : user.birth != null) return false;
        if (city != null ? !city.equals(user.city) : user.city != null) return false;
        if (country != null ? !country.equals(user.country) : user.country != null) return false;
        if (email != null ? !email.equals(user.email) : user.email != null) return false;
        if (firstName != null ? !firstName.equals(user.firstName) : user.firstName != null) return false;
        if (lastName != null ? !lastName.equals(user.lastName) : user.lastName != null) return false;
        if (login != null ? !login.equals(user.login) : user.login != null) return false;
        if (password != null ? !password.equals(user.password) : user.password != null) return false;
        if (phone != null ? !phone.equals(user.phone) : user.phone != null) return false;
        if (postcode != null ? !postcode.equals(user.postcode) : user.postcode != null) return false;
        if (role != user.role) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (login != null ? login.hashCode() : 0);
        result = 31 * result + (firstName != null ? firstName.hashCode() : 0);
        result = 31 * result + (lastName != null ? lastName.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (birth != null ? birth.hashCode() : 0);
        result = 31 * result + (role != null ? role.hashCode() : 0);
        result = 31 * result + (city != null ? city.hashCode() : 0);
        result = 31 * result + (country != null ? country.hashCode() : 0);
        result = 31 * result + (address != null ? address.hashCode() : 0);
        result = 31 * result + (postcode != null ? postcode.hashCode() : 0);
        result = 31 * result + (phone != null ? phone.hashCode() : 0);
        result = 31 * result + (banned ? 1 : 0);
        return result;
    }

    public enum Role {
        ADMINISTRATOR, USER
    }
}



