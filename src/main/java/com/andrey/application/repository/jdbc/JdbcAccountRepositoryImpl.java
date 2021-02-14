package com.andrey.application.repository.jdbc;

import com.andrey.application.model.Account;
import com.andrey.application.repository.AccountRepository;
import com.andrey.application.repository.connection.ConnectionUtil;
import com.andrey.application.util.DAOEntityBuilder;
import com.andrey.application.util.SQLQueries;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class JdbcAccountRepositoryImpl implements AccountRepository {

    @Override
    public Account save(Account newAccount) {
        try(PreparedStatement statement =
                    ConnectionUtil.getStatement(SQLQueries.INSERT_ACCOUNT_QUERY.getValue()))
        {
            statement.setString(1, newAccount.getName());
            statement.execute();
            PreparedStatement preparedStatement = ConnectionUtil.getStatement(SQLQueries.LAST_SAVED_ACCOUNT.getValue());
            ResultSet resultSet =  preparedStatement.executeQuery();
            if (resultSet.next()) {
                newAccount = DAOEntityBuilder.buildAccount(resultSet);
            }
            //TODO если буду работать напрямую с Account save, то будут проблемы с commit/rollback
        } catch (SQLException e) {
            try {
                ConnectionUtil.rollback();
            } catch (SQLException exception) {
                exception.printStackTrace();
            }
            e.printStackTrace();
            System.out.println("Аккаунт не сохранён!");
        }
        return newAccount;
    }

    @Override
    public Account update(Account account) {
        try {
            PreparedStatement statement;
            if (account.getName() == null) {
                statement = ConnectionUtil.getStatement(SQLQueries.UPDATE_ACCOUNT_STATUS.getValue());
                statement.setString(1, account.getStatus().name());

            } else {
                statement = ConnectionUtil.getStatement(SQLQueries.UPDATE_ACCOUNT_NAME.getValue());
                statement.setString(1, account.getName());
            }
            statement.setInt(2, account.getId());
            statement.execute();

            ConnectionUtil.commit();
            account = find(account.getId()).orElse(null);
            statement.close();
        } catch (SQLException e) {
            try {
                ConnectionUtil.rollback();
            } catch (SQLException exception) {
                System.out.println("Error during DB rollback in class " + this.getClass().getName());
            }
            System.out.println("Error. During updating status for account!");
        }
        return account;
    }

    @Override
    public void delete(Integer id) {
        try(PreparedStatement statement =
                    ConnectionUtil.getStatement(SQLQueries.DELETE_ACCOUNT.getValue()))
        {

            statement.setInt(1, id);
            statement.execute();
        } catch (SQLException e) {
            System.out.println("Couldn't find account name for the customer!");
        }
    }

    @Override
    public Optional<Account> find(Integer accountId) {
        Optional<Account> required = Optional.empty();
        try (PreparedStatement statement =
                     ConnectionUtil.getStatement(SQLQueries.FIND_ACCOUNT.getValue()))
        {
            statement.setInt(1, accountId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                required = Optional.of(DAOEntityBuilder.buildAccount(resultSet));
            }
        } catch (SQLException e) {
            System.out.println("Error find() method in class " + this.getClass().getName());
        }
        return required;
    }

    @Override
    public List<Account> findAll() {
        List<Account> accountList = new ArrayList<>();
        try(PreparedStatement statement =
                    ConnectionUtil.getStatement(SQLQueries.FIND_ALL_ACCOUNTS.getValue()))
        {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                accountList.add(DAOEntityBuilder.buildAccount(resultSet));
            }
        } catch (SQLException e) {
            System.out.println("Error findAll() method in class " + this.getClass().getName());
        }
        return accountList;
    }

}
