package com.igor.z.entity;

import javax.persistence.*;
import java.io.Serializable;


@Entity
@Table(name="groups")
public class Group implements Serializable {

    private static final long serialVersionUID = 1528447384986169065L;

    public static final String USERS_GROUP = "users";

    @Id
    @Column(name="email", nullable=false)
    private String email;

    @Column(name="groupname", nullable=false, length=32)
    private String groupname;

    public Group() {}
    public Group(String email, String groupname) {
        this.email = email;
        this.groupname = groupname;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getGroupname() {
        return groupname;
    }
    public void setGroupname(String groupname) {
        this.groupname = groupname;
    }
}