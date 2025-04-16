package services;

import entities.users.Admin;
import factories.RepositoryFactory;
import repositories.AdminRepository;

import java.sql.SQLException;
import java.util.ArrayList;

public class AdminService {
    private final AdminRepository adminRepository = RepositoryFactory.getAdminRepository();

    public ArrayList<Admin> getAllAdmins() throws SQLException {
        return adminRepository.getAll();
    }

    public Admin getAdmin(String username) throws SQLException {
       return adminRepository.getByUsername(username);
    }

    public boolean login(String username, String password) throws SQLException {
        Admin admin = adminRepository.getByUsername(username);
        return admin != null && admin.getPassword().equals(password);
    }
}
