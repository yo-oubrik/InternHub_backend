package ma.ensa.pfaproject.service;

import ma.ensa.pfaproject.entities.Admin;
import ma.ensa.pfaproject.repositories.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminServiceImpl implements AdminService{
    private final AdminRepository adminRepository;

    @Autowired
    public AdminServiceImpl(AdminRepository adminRepository) {
        this.adminRepository = adminRepository;
    }

    @Override
    public List<Admin> getAllAdmins() {
        return adminRepository.findAll();
    }

    @Override
    public Admin getAdminById(String id) {
        return adminRepository.findById(id).orElse(null);
    }

    @Override
    public Admin saveAdmin(Admin admin) {
        return adminRepository.save(admin);
    }

    @Override
    public void deleteAdmin(String id) {
        adminRepository.deleteById(id);
    }

}
