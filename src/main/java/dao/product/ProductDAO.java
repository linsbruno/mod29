package dao.product;

import dao.generic.ConnectionFactory;
import dao.product.IProductDAO;
import domain.Product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductDAO implements IProductDAO {

    public Integer toAdd(Product product) throws Exception {
        Connection connection = null;
        PreparedStatement stm = null;
        try {
            connection = ConnectionFactory.getConnection();
            String sql = getSqlInsert();
            stm = connection.prepareStatement(sql);
            addParamsInsert(stm, product);
            return stm.executeUpdate();
        } catch (Exception e) {
            throw e;
        } finally {
            closeConnection(connection, stm, null);
        }
    }

    @Override
    public Integer toRefresh(Product product) throws Exception {
        Connection connection = null;
        PreparedStatement stm = null;
        try {
            connection = ConnectionFactory.getConnection();
            String sql = getSqlUpdate();
            stm = connection.prepareStatement(sql);
            addParamsUpdate(stm, product);
            return stm.executeUpdate();
        } catch (Exception e) {
            throw e;
        } finally {
            closeConnection(connection, stm, null);
        }
    }

    @Override
    public Product toFind(Long id) throws Exception {
        Connection connection = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        Product product = null;

        try {
            connection = ConnectionFactory.getConnection();
            String sql = getSqlSelect();
            stm = connection.prepareStatement(sql);
            addParamsSelect(stm, id);
            rs = stm.executeQuery();

            if (rs.next()) {
                product = new Product();
                product.setId(rs.getLong("id"));
                product.setName(rs.getString("name"));
                product.setUnitValue(rs.getDouble("unit_value"));
            }
        } catch (Exception e) {
            throw e;
        } finally {
            closeConnection(connection, stm, rs);
        } return product;
    }

    @Override
    public List<Product> allToFind() throws Exception {
        Connection connection = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        List<Product> list = new ArrayList<>();
        Product product = null;

        try {
            connection = ConnectionFactory.getConnection();
            String sql = getSqlSelectAll();
            stm = connection.prepareStatement(sql);
            rs = stm.executeQuery();

            while (rs.next()) {
                product = new Product();
                product.setId(rs.getLong("id"));
                product.setName(rs.getString("name"));
                product.setUnitValue(rs.getDouble("unit_value"));

                list.add(product);
            }
        } catch (Exception e) {
            throw e;
        } finally {
            closeConnection(connection, stm, rs);
        } return list;
    }

    @Override
    public Integer delete(Product product) throws Exception {
        Connection connection = null;
        PreparedStatement stm = null;

        try {
            connection = ConnectionFactory.getConnection();
            String sql = getSqlDelete();
            stm = connection.prepareStatement(sql);
            addParamsDelete(stm, product);
            return stm.executeUpdate();
        } catch (Exception e) {
            throw e;
        } finally {
            closeConnection(connection, stm, null);
        }
    }


    private void closeConnection(Connection connection, PreparedStatement stm, ResultSet rs) {
        try {
            if (rs != null && !rs.isClosed()) {
                rs.close();
            }
            if (stm != null && !stm.isClosed()) {
                stm.close();
            }
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
    }

    private void addParamsInsert(PreparedStatement stm, Product product) throws SQLException {
        stm.setString(1, product.getName());
        stm.setDouble(2, product.getUnitValue());
        stm.setLong(3, product.getId());
    }

    private void addParamsUpdate(PreparedStatement stm, Product product) throws SQLException {
        stm.setString(1, product.getName());
        stm.setDouble(2, product.getUnitValue());
    }

    private void addParamsSelect(PreparedStatement stm, Long id) throws SQLException {
        stm.setString(3, String.valueOf(id));
    }

    private void addParamsDelete(PreparedStatement stm, Product product) throws SQLException {
        stm.setLong(1, product.getId());
    }

    private String getSqlInsert() {
        StringBuilder sb = new StringBuilder();
        sb.append("INSERT INTO TB_PRODUCT (ID, UNIT_VALUE, NAME)");
        sb.append("VALUES (nextval('SQ_PRODUT'),?,?)");
        return sb.toString();
    }

    private String getSqlUpdate() {
        StringBuilder sb = new StringBuilder();
        sb.append("UPDATE TB_PRODUCT ");
        sb.append("SET NAME = ?, UNIT_VALUE = ?");
        sb.append("WHERE ID = ?");
        return sb.toString();
    }

    private String getSqlSelect() {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT * FROM TB_PRODUCT ");
        sb.append("WHERE ID = ?");
        return sb.toString();
    }

    private String getSqlSelectAll() {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT * FROM TB_PRODUCT");
        return sb.toString();
    }

    private String getSqlDelete() {
        StringBuilder sb = new StringBuilder();
        sb.append("DELETE FROM TB_PRODUCT ");
        sb.append("WHERE ID = ?");
        return sb.toString();
    }
}