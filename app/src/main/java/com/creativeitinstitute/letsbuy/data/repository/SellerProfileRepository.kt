package com.creativeitinstitute.letsbuy.data.repository

import android.net.Uri
import com.creativeitinstitute.letsbuy.core.Nodes
import com.creativeitinstitute.letsbuy.views.dashboard.seller.profile.Profile
import com.creativeitinstitute.letsbuy.views.dashboard.seller.profile.toMap
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.UploadTask
import javax.inject.Inject

class SellerProfileRepository @Inject constructor(
    private val db: FirebaseFirestore,
    private val storageRef: StorageReference
)  {
     fun uploadImage(productImageUri: Uri) :UploadTask{

        val storage: StorageReference = storageRef.child("profile").child("USER_${System.currentTimeMillis()}")

       return storage.putFile(productImageUri)

    }

     fun updateUser(user: Profile):Task<Void> {
       return db.collection(Nodes.USER).document(user.userID).update(user.toMap())

    }

    fun getUserByUserID(userID: String): Task<QuerySnapshot> {

        return db.collection(Nodes.USER).whereEqualTo("userID", userID).get()


    }


}