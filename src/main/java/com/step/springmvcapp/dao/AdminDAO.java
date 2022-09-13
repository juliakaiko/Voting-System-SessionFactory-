package com.step.springmvcapp.dao;

import com.step.springmvcapp.entity.Admin;

public interface AdminDAO {
    Admin getAdmin ();
    void save (Admin obj);
    void delete (Long id);
}
