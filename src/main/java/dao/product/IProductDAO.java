package dao.product;

import domain.Product;

import java.util.List;

public interface IProductDAO {

    public Integer toAdd(Product product) throws Exception;
    public Integer toRefresh(Product product) throws Exception;
    public Product toFind(Long id) throws Exception;

    public List<Product> allToFind() throws Exception;
    public Integer delete(Product product) throws Exception;
}