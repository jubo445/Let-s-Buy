package com.creativeitinstitute.letsbuy.views.dashboard.seller.products

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

import com.creativeitinstitute.letsbuy.core.DataState
import com.creativeitinstitute.letsbuy.data.Product
import com.creativeitinstitute.letsbuy.data.repository.SellerRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProductViewModel @Inject constructor(private val repo: SellerRepository):ViewModel(){

    private val _productResponse = MutableLiveData<DataState<List<Product>>>()
    val productResponse : LiveData<DataState<List<Product>>> = _productResponse

    fun getProductByID(userID:String){

        _productResponse.postValue(DataState.Loading())


        repo.getAllProductByUserID(userID).addOnSuccessListener { document ->

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