package com.step.springmvcapp.entity;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity (name = "Admin")
//@Table (name = "admin")
public class Admin extends User implements Serializable {
    
    public Admin() {}
    
}
