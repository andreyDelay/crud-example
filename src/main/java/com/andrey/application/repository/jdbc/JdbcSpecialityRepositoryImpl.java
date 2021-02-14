package com.andrey.application.repository.jdbc;

import com.andrey.application.model.Speciality;
import com.andrey.application.repository.SpecialityRepository;
import com.andrey.application.repository.connection.ConnectionUtil;
import com.andrey.application.util.DAOEntityBuilder;
import com.andrey.application.util.SQLQueries;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class JdbcSpecialityRepositoryImpl implements SpecialityRepository {


    @Override
    public Speciality save(Speciality speciality) {
        try(PreparedStatement statement =
                    ConnectionUtil.getStatement(SQLQueries.INSERT_SPECIALTY_QUERY.getValue()))
        {
            statement.setString(1, speciality.getName());
            statement.execute();
            PreparedStatement preparedStatement = ConnectionUtil.getStatement(SQLQueries.LAST_SAVED_SPECIALITY.getValue());
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                speciality = DAOEntityBuilder.buildSpeciality(resultSet);
            }
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Ошибка при сохранении speciality в БД!");
        }
        return speciality;
    }

    @Override
    public Speciality update(Speciality speciality) {
        try(PreparedStatement statement = ConnectionUtil.getStatement(SQLQueries.UPDATE_SPECIALITY_QUERY.getValue())) {
            statement.setString(1, speciality.getName());
            statement.setInt(2, speciality.getId());
            statement.execute();

            ConnectionUtil.commit();
            speciality = find(speciality.getId()).orElse(null);
        } catch (SQLException e) {
            //TODO add errorHandler
            e.printStackTrace();
        }
        return speciality;
    }

    @Override
    public void delete(Integer id) {
        try(PreparedStatement statement =
                    ConnectionUtil.getStatement(SQLQueries.DELETE_SPECIALITY_QUERY.getValue()))
        {
            deleteFromCustomerSpecialities(id);
            statement.setInt(1, id);
            statement.execute();
            ConnectionUtil.commit();
        } catch (SQLException e) {
            try {
                ConnectionUtil.rollback();
            } catch (SQLException exception) {
                System.out.println("Error during DB rollback in class " + this.getClass().getName());
            }
        }
    }

    @Override
    public Optional<Speciality> find(Integer id) {
        Optional<Speciality> required = Optional.empty();
        try(PreparedStatement statement =
                    ConnectionUtil.getStatement(SQLQueries.FIND_SPECIALITY_QUERY.getValue()))
        {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                required = Optional.of(DAOEntityBuilder.buildSpeciality(resultSet));
            }
        } catch (SQLException e) {
            //TODO add errorHandler
            System.out.println("Error find() method in class " + this.getClass().getName());
        }
        return required;
    }

    @Override
    public List<Speciality> findAll() {
        List<Speciality> specialityList = new ArrayList<>();
        try(PreparedStatement statement =
                    ConnectionUtil.getStatement(SQLQueries.FIND_ALL_SPECIALITIES.getValue()))
        {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                specialityList.add(DAOEntityBuilder.buildSpeciality(resultSet));
            }
        } catch (SQLException e) {
            System.out.println("Error findAll() method in class " + this.getClass().getName());
        }
        return specialityList;
    }

    private void deleteFromCustomerSpecialities(int specialityId) {
        try(PreparedStatement statement =
                    ConnectionUtil.getStatement(SQLQueries.DEL_FROM_CUSTOMER_SPECIALITIES_BY_SPEC_ID.getValue()))
        {
            statement.setInt(1, specialityId);
            statement.execute();
        } catch (SQLException e) {
            System.out.println("CustomerSpecialities delete was failed!");
        }
    }

}
