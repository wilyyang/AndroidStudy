package com.example.roomdemo.ui.main

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.roomdemo.module.model.Product
import com.example.roomdemo.module.ProductRepository
import com.example.roomdemo.module.ProductRoomDatabase

class MainViewModel(val contextApplication: Application): ViewModel() {
    companion object {
        fun provideFactory(contextApplication: Application): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return MainViewModel(contextApplication) as T
            }
        }
    }

    private val repository : ProductRepository
    val allProducts : LiveData<List<Product>>
    val searchResults : MutableLiveData<List<Product>>

    init {
        val productDb = ProductRoomDatabase.getInstance(contextApplication)
        val productDao = productDb.productDao()
        repository = ProductRepository(productDao)

        allProducts = repository.allProducts
        searchResults = repository.searchResults
    }

    fun insertProduct(product : Product){
        repository.insertProduct(product)
    }

    fun findProduct(name : String){
        repository.findProduct(name)
    }

    fun deleteProduct(name : String){
        repository.deleteProduct(name)
    }
}