package com.chat.model;


import com.chat.tools.TimeFormater;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

/**
 *
 */
@Entity
@Table
public class Message {

    /**
     * id of message
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    /**
     * user who created this message
     */
    @ManyToOne
    private User sender;

    /**
     * date of creating the message
     */
    @DateTimeFormat(pattern = "HH:mm dd/MM/yyyy")
    private Date date;

    /**
     * text of message
     */
    private String message;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getFormattedCreationTime() {
        return TimeFormater.formatDateWithSeconds(getDate());
    }
}
