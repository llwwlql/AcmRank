package com.llwwlql.bean;

import java.util.HashSet;
import java.util.Set;


/**
 * Hduuser entity. @author MyEclipse Persistence Tools
 */

public class Hduuser  implements java.io.Serializable {


    // Fields    

     private Integer id;
     private User user;
     private String hduUserName;
     private Integer hduSolve;
     private Integer hduSubmission;
     private Set users = new HashSet(0);


    // Constructors

    /** default constructor */
    public Hduuser() {
    }

	/** minimal constructor */
    public Hduuser(User user, String hduUserName) {
        this.user = user;
        this.hduUserName = hduUserName;
    }
    
    /** full constructor */
    public Hduuser(User user, String hduUserName, Integer hduSolve, Integer hduSubmission, Set users) {
        this.user = user;
        this.hduUserName = hduUserName;
        this.hduSolve = hduSolve;
        this.hduSubmission = hduSubmission;
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

    public String getHduUserName() {
        return this.hduUserName;
    }
    
    public void setHduUserName(String hduUserName) {
        this.hduUserName = hduUserName;
    }

    public Integer getHduSolve() {
        return this.hduSolve;
    }
    
    public void setHduSolve(Integer hduSolve) {
        this.hduSolve = hduSolve;
    }

    public Integer getHduSubmission() {
        return this.hduSubmission;
    }
    
    public void setHduSubmission(Integer hduSubmission) {
        this.hduSubmission = hduSubmission;
    }

    public Set getUsers() {
        return this.users;
    }
    
    public void setUsers(Set users) {
        this.users = users;
    }
    
    @Override
    public String toString() {
    	return "HduUserName:"+hduUserName +"\tSolve:" + this.hduSolve +"\tSubmission:"+this.hduSubmission;
    }
}