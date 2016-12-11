package com.llwwlql.bean;

import java.util.HashSet;
import java.util.Set;


/**
 * Vjudgeuser entity. @author MyEclipse Persistence Tools
 */

public class Vjudgeuser  implements java.io.Serializable {


    // Fields    

     private Integer id;
     private User user;
     private String vjudgeUserName;
     private Set users = new HashSet(0);


    // Constructors

    /** default constructor */
    public Vjudgeuser() {
    }

	/** minimal constructor */
    public Vjudgeuser(User user, String vjudgeUserName) {
        this.user = user;
        this.vjudgeUserName = vjudgeUserName;
    }
    
    /** full constructor */
    public Vjudgeuser(User user, String vjudgeUserName, Set users) {
        this.user = user;
        this.vjudgeUserName = vjudgeUserName;
        this.users = users;
    }

   
    // Property accessors

    public Integer getId() {
        return this.id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }

    public User getUser() {
        return this.user;
    }
    
    public void setUser(User user) {
        this.user = user;
    }

    public String getVjudgeUserName() {
        return this.vjudgeUserName;
    }
    
    public void setVjudgeUserName(String vjudgeUserName) {
        this.vjudgeUserName = vjudgeUserName;
    }

    public Set getUsers() {
        return this.users;
    }
    
    public void setUsers(Set users) {
        this.users = users;
    }
   








}