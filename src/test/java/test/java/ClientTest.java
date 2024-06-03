package test.java;

import dao.client.ClientDAO;
import dao.client.IClientDAO;
import domain.Client;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class ClientTest {

    private IClientDAO clientDAO;

    @Test
    public void addTest() throws Exception {
        clientDAO = new ClientDAO();

        // add new client
        Client client = new Client();
        client.setCode("01");
        client.setName("Bruno Correia");
        Integer countAdd = clientDAO.toAdd(client);
        assertTrue(countAdd == 1);
        assertNotNull(countAdd);

        // find added client
        Client clientBD = clientDAO.toFind("01");
        assertNotNull(clientBD);
        assertEquals(client.getCode(), clientBD.getCode());
        assertEquals(client.getName(), clientBD.getName());

        // delete added client
        Integer countDel = clientDAO.delete(clientBD);
        assertNotNull(countDel == 1);
    }

    @Test
    public void findTest() throws Exception {
        clientDAO = new ClientDAO();

        // find client 01 (no existing)
        Client clientBD = clientDAO.toFind("01");
        Assert.assertNull(clientBD);

        // add client 01
        Client client = new Client();
        client.setCode("01");
        client.setName("Bruno Correia");
        Integer countAdd = clientDAO.toAdd(client);
        assertTrue(countAdd == 1);
        assertNotNull(countAdd);

        // find client 01
        clientBD = clientDAO.toFind("01");
        assertNotNull(clientBD);
        assertEquals(client.getCode(), clientBD.getCode());
        assertEquals(client.getName(), clientBD.getName());

        // delete client 01
        Integer countDel = clientDAO.delete(clientBD);
        assertNotNull(countDel == 1);
    }

    @Test
    public void deleteTest() throws Exception {
        clientDAO = new ClientDAO();

        // add client 20
        Client client = new Client();
        client.setCode("20");
        client.setName("Maria Garcia");
        Integer countAdd = clientDAO.toAdd(client);
        assertTrue(countAdd == 1);
        assertNotNull(countAdd);

        // find client 20
        Client clientBD = clientDAO.toFind("20");
        assertNotNull(clientBD);
        assertEquals(client.getCode(), clientBD.getCode());
        assertEquals(client.getName(), clientBD.getName());

        // delete client 20
        Integer countDel = clientDAO.delete(clientBD);
        assertNotNull(countDel == 1);
    }

    @Test
    public void findAllTest() throws Exception {
        clientDAO = new ClientDAO();

        // add client 01
        Client client = new Client();
        client.setCode("01");
        client.setName("Bruno Correia");
        Integer countAdd = clientDAO.toAdd(client);
        assertTrue(countAdd == 1);
        assertNotNull(countAdd);

        // add client 20
        Client client2 = new Client();
        client2.setCode("20");
        client2.setName("Maria Garcia");
        Integer countAdd2 = clientDAO.toAdd(client);
        assertTrue(countAdd2 == 1);
        assertNotNull(countAdd);

        //
        List<Client> list = clientDAO.allToFind();
        assertNotNull(list);
        assertEquals(2, list.size());

        int countDel = 0;
        for (Client cli : list) {
            clientDAO.delete(cli);
            countDel++;
        } assertEquals(list.size(), countDel);

        list = clientDAO.allToFind();
        assertEquals(list.size(), 0);
    }

    @Test
    public void refreshTest() throws Exception {
        clientDAO = new ClientDAO();

        // add client 01
        Client client = new Client();
        client.setCode("01");
        client.setName("Bruno Correia");
        Integer countAdd = clientDAO.toAdd(client);
        assertTrue(countAdd == 1);
        assertNotNull(countAdd);

        // find client 01
        Client clientBD = clientDAO.toFind("01");
        assertNotNull(clientBD);
        assertEquals(client.getCode(), clientBD.getCode());
        assertEquals(client.getName(), clientBD.getName());

        // refresh data client 01
        clientBD.setCode("30");
        clientBD.setName("Nuno Correa");
        Integer countUpdate = clientDAO.toRefresh(clientBD);
        assertTrue(countUpdate == 1);

        // find client with old data
        Client clientBD1 = clientDAO.toFind("01");
        assertNull(clientBD1);

        // find client with new data && data compare
        Client clientBD2 = clientDAO.toFind("30");
        assertNotNull(clientBD2);
        assertEquals(clientBD.getId(), clientBD2.getId());
        assertEquals(clientBD.getCode(), clientBD2.getCode());
        assertEquals(clientBD.getName(), clientBD2.getName());

        List<Client> list = clientDAO.allToFind();
        for (Client cli : list) {
            clientDAO.delete(cli);
        }
    }
}