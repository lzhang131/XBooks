package com.example.books.data;

import java.io.Serializable;

/**
 * UserModel
 */
public class UserModel implements Serializable {

    // id
    private String id;

    // username
    private String username;

    // password
    private String password;

    // topic
    private String topic;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    @Override
    public String toString() {
        return "UserModel{" +
                "id='" + id + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", topic='" + topic + '\'' +
                '}';
    }
}
