package dao.client;

import domain.Client;

import java.util.List;

public interface IClientDAO {

    public Integer toAdd(Client client) throws Exception;
    public Integer toRefresh(Client client) throws Exception;
    public Client toFind(String code) throws Exception;
    public List<Client> allToFind() throws Exception;
    public Integer delete(Client client) throws Exception;
}