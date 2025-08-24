package com.example.myapplication.local;

import androidx.room.Dao;
import androidx.room.Delete;
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


    @Update
    void updateProduct(ProductLocalModel product);

    @Delete
    void deleteProduct(ProductLocalModel product);

    @Query("DELETE FROM product")
    void deleteAllProducts();

}