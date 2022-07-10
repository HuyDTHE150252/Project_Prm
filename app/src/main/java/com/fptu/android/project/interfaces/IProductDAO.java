package com.fptu.android.project.interfaces;

import com.fptu.android.project.model.Product;

import java.util.List;

public interface IProductDAO {

    public void insert(Product p);

    public void update(Product p);

    public void delete(Product p);

    public List<Product> getAllProduct();


}
