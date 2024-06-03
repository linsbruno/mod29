package dao.client;

import dao.generic.ConnectionFactory;
import domain.Client;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ClientDAO implements IClientDAO {
    @Override
    public Integer toAdd(Client client) throws Exception {
        Connection connection = null;
        PreparedStatement stm = null;
        try {
            connection = ConnectionFactory.getConnection();
            String sql = getSqlInsert();
            stm = connection.prepareStatement(sql);
            addParamsInsert(stm, client);
            return stm.executeUpdate();
        } catch (Exception e) {
            throw e;
        } finally {
            closeConnection(connection, stm, null);
        }
    }

    @Override
    public Integer toRefresh(Client client) throws Exception {
        Connection connection = null;
        PreparedStatement stm = null;
        try {
            connection = ConnectionFactory.getConnection();
            String sql = getSqlUpdate();
            stm = connection.prepareStatement(sql);
            addParamsUpdate(stm, client);
            return stm.executeUpdate();
        } catch (Exception e) {
            throw e;
        } finally {
            closeConnection(connection, stm, null);
        }
    }

    @Override
    public Client toFind(String code) throws Exception {
        Connection connection = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        Client client = null;

        try {
            connection = ConnectionFactory.getConnection();
            String sql = getSqlSelect();
            stm = connection.prepareStatement(sql);
            addParamsSelect(stm, code);
            rs = stm.executeQuery();

            if (rs.next()) {
                client = new Client();
                client.setId(rs.getLong("id"));
                client.setName(rs.getString("name"));
                client.setCode(rs.getString("code"));
            }
        } catch (Exception e) {
            throw e;
        } finally {
            closeConnection(connection, stm, rs);
        } return client;
    }

    @Override
    public List<Client> allToFind() throws Exception {
        Connection connection = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        List<Client> list = new ArrayList<>();
        Client client = null;

        try {
            connection = ConnectionFactory.getConnection();
            String sql = getSqlSelectAll();
            stm = connection.prepareStatement(sql);
            rs = stm.executeQuery();

            while (rs.next()) {
                client = new Client();
                client.setId(rs.getLong("id"));
                client.setName(rs.getString("name"));
                client.setCode(rs.getString("code"));

                list.add(client);
            }
        } catch (Exception e) {
            throw e;
        } finally {
            closeConnection(connection, stm, rs);
        } return list;
    }

    @Override
    public Integer delete(Client client) throws Exception {
        Connection connection = null;
        PreparedStatement stm = null;

        try {
            connection = ConnectionFactory.getConnection();
            String sql = getSqlDelete();
            stm = connection.prepareStatement(sql);
            addParamsDelete(stm, client);
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


    private String getSqlInsert() {
        StringBuilder sb = new StringBuilder();
        sb.append("INSERT INTO TB_CLIENT (ID, CODE, NAME)");
        sb.append("VALUES (nextval('SQ_CLIENT'),?,?)");
        return sb.toString();
    }

    private void addParamsInsert(PreparedStatement stm, Client client) throws SQLException {
        stm.setString(1, client.getCode());
        stm.setString(2, client.getName());

    }


    private String getSqlUpdate() {
        StringBuilder sb = new StringBuilder();
        sb.append("UPDATE TB_CLIENT ");
        sb.append("SET NAME = ?, CODE = ? ");
        sb.append("WHERE ID = ?");
        return sb.toString();
    }
    private void addParamsUpdate(PreparedStatement stm, Client client) throws SQLException {
        stm.setString(1, client.getName());
        stm.setString(2, client.getCode());
        stm.setLong(3, client.getId());
    }


    private String getSqlSelect() {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT * FROM TB_CLIENT ");
        sb.append("WHERE CODE = ?");
        return sb.toString();
    }
    private void addParamsSelect(PreparedStatement stm, String code) throws SQLException {
        stm.setString(1, code);
    }


    private String getSqlDelete() {
        StringBuilder sb = new StringBuilder();
        sb.append("DELETE FROM TB_CLIENT ");
        sb.append("WHERE CODE = ?");
        return sb.toString();
    }
    private void addParamsDelete(PreparedStatement stm, Client client) throws SQLException {
        stm.setString(1, client.getCode());
    }


    private String getSqlSelectAll() {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT * FROM TB_CLIENT");
        return sb.toString();
    }


}