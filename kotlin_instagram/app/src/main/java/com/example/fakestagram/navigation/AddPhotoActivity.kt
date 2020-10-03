package com.example.fakestagram.navigation

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.fakestagram.R
import com.google.firebase.storage.FirebaseStorage

class AddPhotoActivity : AppCompatActivity() {
    var PICK_IMAGE_FROM_ALBUM = 0
    var storage : FirebaseStorage? = null
    var photoUri : Uri? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_photo)

        //Initiate storage 초기화
        storage = FirebaseStorage.getInstance()

        // Open the album
        var photoPickerIntent = Intent(Intent.ACTION_PICK)
        photo
    }
}