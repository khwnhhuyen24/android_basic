package com.example.myapplication.local;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.myapplication.model.ProductLocalModel;

import java.util.List;

@Dao
public interface ProductDAO {

    @Query("Select * from product")
    List<ProductLocalModel> getAllProductLocalModel();

    @Insert
    void insertProduct(ProductLocalModel productLocalModel);

    @Query("SELECT COUNT(*) FROM product")
    int getProductCount();

    @Query("DELETE FROM product WHERE id = :id")
    void deleteProductById(int id);

    @Update
    void updateProduct(ProductLocalModel productLocalModel);

}
