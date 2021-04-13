package com.pozharsky.dmitri.model.entity;

/**
 * The type User.
 *
 * @author Dmitri Pozharsky
 */
public class User extends Entity {

    /**
     * The enum Role type.
     */
    public enum RoleType {
        /**
         * Admin role type.
         */
        ADMIN,
        /**
         * User role type.
         */
        USER,
        /**
         * Guest role type.
         */
        GUEST
    }

    /**
     * The enum Status type.
     */
    public enum StatusType {
        /**
         * Active status type.
         */
        ACTIVE,
        /**
         * Blocked status type.
         */
        BLOCKED,
        /**
         * Wait active status type.
         */
        WAIT_ACTIVE
    }

    private long id;
    private String firstName;
    private String lastName;
    private String username;
    private String email;
    private RoleType roleType;
    private StatusType statusType;

    /**
     * Instantiates a new User.
     */
    public User() {
    }

    /**
     * Instantiates a new User.
     *
     * @param id         the id
     * @param firstName  the first name
     * @param lastName   the last name
     * @param username   the username
     * @param email      the email
     * @param roleType   the role type
     * @param statusType the status type
     */
    public User(long id, String firstName, String lastName, String username, String email, RoleType roleType, StatusType statusType) {
        this(firstName, lastName, username, email, roleType, statusType);
        this.id = id;
    }

    /**
     * Instantiates a new User.
     *
     * @param firstName  the first name
     * @param lastName   the last name
     * @param username   the username
     * @param email      the email
     * @param roleType   the role type
     * @param statusType the status type
     */
    public User(String firstName, String lastName, String username, String email, RoleType roleType, StatusType statusType) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.email = email;
        this.roleType = roleType;
        this.statusType = statusType;
    }

    /**
     * Gets id.
     *
     * @return the id
     */
    public long getId() {
        return id;
    }

    /**
     * Sets id.
     *
     * @param id the id
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * Gets first name.
     *
     * @return the first name
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Sets first name.
     *
     * @param firstName the first name
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Gets last name.
     *
     * @return the last name
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Sets last name.
     *
     * @param lastName the last name
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Gets username.
     *
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets username.
     *
     * @param username the username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Gets email.
     *
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets email.
     *
     * @param email the email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Gets role type.
     *
     * @return the role type
     */
    public RoleType getRoleType() {
        return roleType;
    }

    /**
     * Sets role type.
     *
     * @param roleType the role type
     */
    public void setRoleType(RoleType roleType) {
        this.roleType = roleType;
    }

    /**
     * Gets status type.
     *
     * @return the status type
     */
    public StatusType getStatusType() {
        return statusType;
    }

    /**
     * Sets status type.
     *
     * @param statusType the status type
     */
    public void setStatusType(StatusType statusType) {
        this.statusType = statusType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (id != user.id) return false;
        if (firstName != null ? !firstName.equals(user.firstName) : user.firstName != null) return false;
        if (lastName != null ? !lastName.equals(user.lastName) : user.lastName != null) return false;
        if (username != null ? !username.equals(user.username) : user.username != null) return false;
        if (email != null ? !email.equals(user.email) : user.email != null) return false;
        if (roleType != user.roleType) return false;
        return statusType == user.statusType;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (firstName != null ? firstName.hashCode() : 0);
        result = 31 * result + (lastName != null ? lastName.hashCode() : 0);
        result = 31 * result + (username != null ? username.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (roleType != null ? roleType.hashCode() : 0);
        result = 31 * result + (statusType != null ? statusType.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("User{");
        sb.append("id=").append(id);
        sb.append(", firstName='").append(firstName).append('\'');
        sb.append(", lastName='").append(lastName).append('\'');
        sb.append(", username='").append(username).append('\'');
        sb.append(", email='").append(email).append('\'');
        sb.append(", roleType=").append(roleType);
        sb.append(", statusType=").append(statusType);
        sb.append('}');
        return sb.toString();
    }
}
