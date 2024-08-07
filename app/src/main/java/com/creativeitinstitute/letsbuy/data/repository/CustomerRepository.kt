package com.creativeitinstitute.letsbuy.data.repository

import android.net.Uri
import com.creativeitinstitute.letsbuy.core.Nodes
import com.creativeitinstitute.letsbuy.data.Product
import com.creativeitinstitute.letsbuy.data.models.CustomerSource
import com.creativeitinstitute.letsbuy.data.models.SellerSource
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.UploadTask
import javax.inject.Inject

class CustomerRepository @Inject constructor(
    private val db: FirebaseFirestore,
    private val storageRef: StorageReference
) : CustomerSource {
    override fun uploadProductImage(productImageUri: Uri) :UploadTask{

        val storage: StorageReference = storageRef.child("products").child("PRD_${System.currentTimeMillis()}")

       return storage.putFile(productImageUri)

    }

    override fun uploadProduct(product: Product):Task<Void> {
       return db.collection(Nodes.PRODUCT).document(product.productID).set(product)

    }

    override fun getAllProductByUserID(userID: String): Task<QuerySnapshot> {

       return db.collection(Nodes.PRODUCT).whereEqualTo("sellerID", userID).get()


    }

    override fun getAllProduct(): Task<QuerySnapshot> {
        return db.collection(Nodes.PRODUCT).get()
    }
}