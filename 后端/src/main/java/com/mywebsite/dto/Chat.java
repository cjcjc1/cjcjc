package com.mywebsite.dto;

import java.io.Serializable;
import java.util.Date;

public class Chat implements Serializable {

    public Chat(String user, String content, Date time){
        setUser(user);
        setContent(content);
        setTime(time);
    }

    private Integer id;

    private String user;

    private Date time;

    private String content;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + " [" + "Hash = " + hashCode() + ", id=" + id + ", user=" + user + ", time=" + time + ", content=" + content + ", serialVersionUID=" + serialVersionUID + "]";
    }
}