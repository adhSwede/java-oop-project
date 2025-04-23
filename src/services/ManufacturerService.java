package services;

import factories.RepositoryFactory;
import repositories.ManufacturerRepository;

import java.sql.SQLException;

public class ManufacturerService {
    ManufacturerRepository manufacturerRepository = RepositoryFactory.getManufacturerRepository();

    public String getManufacturerNameById(int id) throws SQLException {
        return manufacturerRepository.getManufacturerNameById(id);
    }
}
