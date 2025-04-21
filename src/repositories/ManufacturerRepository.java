package repositories;

import utils.SqlUtils;

import java.sql.SQLException;
import java.util.List;

public class ManufacturerRepository {
    public String getManufacturerNameById(int id) throws SQLException {
        String query = "SELECT name FROM manufacturers WHERE manufacturer_id = ?";
        List<String> result = SqlUtils.executeAndMap(query, rs ->
                rs.getString("name"), String.valueOf(id));
        return result.isEmpty() ? "Unknown" : result.get(0);
    }
}
