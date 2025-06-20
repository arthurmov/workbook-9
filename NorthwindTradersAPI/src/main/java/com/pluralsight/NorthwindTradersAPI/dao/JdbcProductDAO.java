package com.pluralsight.NorthwindTradersAPI.dao;

import com.pluralsight.NorthwindTradersAPI.models.Product;
import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcProductDAO implements ProductDAO{

    private DatabaseConfig databaseConfig;

    public JdbcProductDAO(DatabaseConfig databaseConfig) {
        this.databaseConfig = databaseConfig;
    }

    @Override
    public List<Product> getAllProducts() {

        ArrayList<Product> products = new ArrayList<>();
        String query = """
                select
                productId,
                productName,
                CategoryId,
                UnitPrice
                from
                products
                """;

        BasicDataSource basicDataSource = new BasicDataSource();
        basicDataSource.setUrl(databaseConfig.getUrl());
        basicDataSource.setUsername(databaseConfig.getUsername());
        basicDataSource.setPassword(databaseConfig.getPassword());

        try (
                Connection connection = basicDataSource.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                ResultSet resultSet = preparedStatement.executeQuery();
                )
        {
            while (resultSet.next()){
                int productId = resultSet.getInt(1);
                String productName = resultSet.getString(2);
                int categoryId = resultSet.getInt(3);
                double price = resultSet.getDouble(4);
                Product p = new Product(productId, productName, categoryId, price);
                products.add(p);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return products;
    }

    @Override
    public Product getProductById(int productId) {

        String query = """
                select
                productId,
                productName,
                CategoryId,
                UnitPrice
                from
                products
                where ProductId = ?
                """;

        BasicDataSource basicDataSource = new BasicDataSource();
        basicDataSource.setUrl(databaseConfig.getUrl());
        basicDataSource.setUsername(databaseConfig.getUsername());
        basicDataSource.setPassword(databaseConfig.getPassword());

        try (
                Connection connection = basicDataSource.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query);
        )
        {
            preparedStatement.setInt(1, productId);
            try(ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()){
                    String productName = resultSet.getString(2);
                    int categoryId = resultSet.getInt(3);
                    double price = resultSet.getDouble(4);
                    Product p = new Product(productId, productName, categoryId, price);
                    return p;
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }


    @Override
    public Product addProduct(Product product) {

        BasicDataSource basicDataSource = new BasicDataSource();
        basicDataSource.setUrl(databaseConfig.getUrl());
        basicDataSource.setUsername(databaseConfig.getUsername());
        basicDataSource.setPassword(databaseConfig.getPassword());

        String query = """
                insert into products
                (ProductName, SupplierId, categoryid, unitprice)
                values
                (?,1,?,?)
                """;
        try(
             Connection connection = basicDataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);
             ){
            preparedStatement.setString(1, product.getProductName());
            preparedStatement.setInt(2, product.getCategoryId());
            preparedStatement.setDouble(3, product.getPrice());
            //gonna get back num of rows
            int rows = preparedStatement.executeUpdate();
            try(ResultSet resultSet = preparedStatement.getGeneratedKeys()){
                while(resultSet.next()){
                    Product result = new Product();
                    result.setProductId(resultSet.getInt(1));
                    result.setProductName(product.getProductName());
                    result.setPrice(product.getPrice());
                    return result;
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return null;
    }
}
