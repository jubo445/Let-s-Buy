package com.creativeitinstitute.letsbuy.views.dashboard.customer

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

import com.creativeitinstitute.letsbuy.core.DataState
import com.creativeitinstitute.letsbuy.data.Product
import com.creativeitinstitute.letsbuy.data.repository.CustomerRepository
import com.creativeitinstitute.letsbuy.data.repository.SellerRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProductViewModelCustomer @Inject constructor(private val repo: CustomerRepository):ViewModel(){

    private val _productResponse = MutableLiveData<DataState<List<Product>>>()
    val productResponse : LiveData<DataState<List<Product>>> = _productResponse

    init {
        getAllProduct()
    }

    private fun getAllProduct(){

        _productResponse.postValue(DataState.Loading())


        repo.getAllProduct().addOnSuccessListener { document ->

            val productList = mutableListOf<Product>()

            document.documents.forEach { doc->


               doc.toObject(Product::class.java)?.let {
                    productList.add(it)
                }

            }

            _productResponse.postValue(DataState.Success(productList))




        }.addOnFailureListener {

            _productResponse.postValue(DataState.Error("${it.message}"))

        }



    }
}