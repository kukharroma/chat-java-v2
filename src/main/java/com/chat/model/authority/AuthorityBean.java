package com.chat.model.authority;


import javax.persistence.*;
import java.io.Serializable;

/**
 * Holds one role (one Authority)
 */
@Entity
@Table
public class AuthorityBean implements Serializable{
    /**
     * id of AuthorityBean
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    /**
     * instance of enum Authority
     */
    @Enumerated
    private Authority authority;

    public Authority getAuthority() {
        return authority;
    }

    public void setAuthority(Authority authority) {
        this.authority = authority;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
