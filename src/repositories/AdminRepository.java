package repositories;

import entities.users.Admin;
import utils.SqlUtils;

import java.sql.SQLException;
import java.util.ArrayList;

import static utils.Mappers.adminMapper;

public class AdminRepository {

    public ArrayList<Admin> getAll() throws SQLException {
        String query = "SELECT * FROM admins";

        return SqlUtils.executeAndMap(query, adminMapper);
    }

    public Admin getByUsername(String username) throws SQLException {
        String query = "SELECT * FROM admins WHERE username = ?";
        ArrayList<Admin> results = SqlUtils.executeAndMap(query, adminMapper, username);
        return results.isEmpty() ? null : results.get(0);
    }

}
