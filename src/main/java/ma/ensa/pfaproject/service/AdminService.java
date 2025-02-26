package ma.ensa.pfaproject.service;

import ma.ensa.pfaproject.entities.Admin;

import java.util.List;

public interface AdminService {

    List<Admin> getAllAdmins();
    Admin getAdminById(String id);
    Admin saveAdmin(Admin admin);
    void deleteAdmin(String id);
}
