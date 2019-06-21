package com.example.joy.beyond;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

@Entity
public class UserEvent {

    private static SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm'Z'");

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    
    @Lob 
    private String token;
    private String userId;
    @Temporal(TemporalType.TIMESTAMP)
    private Date loginAt;
    @Temporal(TemporalType.TIMESTAMP)
    private Date tokenIssuedAt;
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastViewedAt;
    
    
    public UserEvent() {
    }

    public UserEvent(String userId, String name,String token,Date loginAt,Date issueAt) {
        this.userId=userId;
        this.name = name;
        this.token = token;
        this.loginAt = loginAt;
        this.lastViewedAt=loginAt;
        this.tokenIssuedAt=issueAt;
    }

    @Override
    public String toString() {
        return "UserEvent{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", token='" + token + '\'' +
                ", loginAt='" + loginAt + '\'' +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Date getLoginAt() {
        return loginAt;
    }

    public void setLoginAt(Date loginAt) {
        this.loginAt = loginAt;
    }

    public Date getLastViewedAt() {
        return lastViewedAt;
    }

    public void setLastViewedAt(Date lastViewedAt) {
        this.lastViewedAt = lastViewedAt;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Date getTokenIssuedAt() {
        return tokenIssuedAt;
    }

    public void setTokenIssuedAt(Date tokenIssuedAt) {
        this.tokenIssuedAt = tokenIssuedAt;
    }

    @Transient
    public String getTokenIssuedAtString() {
        return formatter.format(tokenIssuedAt);
    }
    
    @Transient
    public String getLastViewedAtString() {
        return formatter.format(lastViewedAt);
    }
    
    @Transient
    public String getLoginAtString() {
        return formatter.format(loginAt);
    }

}
