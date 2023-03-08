package com.example.roomdemo.module

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.roomdemo.module.model.Product

@Dao
interface ProductDao {
    @Insert
    fun insertProduct(product : Product)

    @Query("SELECT * FROM products WHERE productName = :name")
    fun findProduct(name:String):List<Product>

    @Query("DELETE FROM products WHERE productName = :name")
    fun deleteProduct(name:String)

    @Query("SELECT * FROM products")
    fun getAllProducts(): LiveData<List<Product>>
}