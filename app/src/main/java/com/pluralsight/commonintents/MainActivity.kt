package com.pluralsight.commonintents

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.content.Intent.ACTION_GET_CONTENT
import android.content.Intent.ACTION_OPEN_DOCUMENT
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.AlarmClock
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import com.google.android.gms.actions.NoteIntents
import java.io.File
import java.text.SimpleDateFormat
import java.util.*


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val buttonReminder = findViewById<Button>(R.id.buttonReminder)
        buttonReminder.setOnClickListener {
            onButtonReminderClick()
        }

        val buttonAttach = findViewById<Button>(R.id.buttonAttach)
        buttonAttach.setOnClickListener {
            onButtonAttachClick()
        }

        val imageViewAvatar = findViewById<ImageView>(R.id.imageViewAvatar)
        imageViewAvatar.setOnClickListener {
            openProfile()
        }

        val textViewName = findViewById<TextView>(R.id.textViewName)
        textViewName.setOnClickListener {
            openProfile()
        }

        val buttonSave = findViewById<Button>(R.id.buttonSave)
        buttonSave.setOnClickListener {
            saveMessageAsNote()
        }
    }

    private fun saveMessageAsNote() {
        val intent = Intent(NoteIntents.ACTION_CREATE_NOTE).apply {
            putExtra(NoteIntents.EXTRA_NAME, "Message Subject")
            putExtra(NoteIntents.EXTRA_TEXT, "Message Text")
            type = "text/plain"
        }
        if (intent.resolveActivity(packageManager) != null) {
            startActivity(intent)
        }
    }

    private fun openProfile() {
        val intent = Intent(this, ProfileActivity::class.java)
        startActivity(intent)
    }

    private fun onButtonAttachClick() {

        val intent = Intent(ACTION_GET_CONTENT).apply {
            type = "audio/*"
            putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
        }
        if (intent.resolveActivity(packageManager) != null) {
            startActivityForResult(intent, REQUEST_IMAGE_GET)
        }


//        uriSavedImage = FileProvider.getUriForFile(this,
//            BuildConfig.APPLICATION_ID + ".provider", createImageFile())
//        Log.i("MainActivity", "Storing image in $uriSavedImage")
//
//        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE).apply {
//            putExtra(MediaStore.EXTRA_OUTPUT, uriSavedImage)
//        }
//        if (intent.resolveActivity(packageManager) != null) {
//            startActivityForResult(intent, REQUEST_IMAGE_CAPTURE)
//        }
    }

    private var uriSavedImage: Uri? = null
    private var currentPhotoPath: String = ""
    private val REQUEST_IMAGE_CAPTURE = 1
    private val REQUEST_IMAGE_GET = 2

    // Method to generate the file name, from
    // https://developer.android.com/training/camera/photobasics
    @SuppressLint("SimpleDateFormat")
    private fun createImageFile(): File {
        // Create an image file name
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val storageDir: File? = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(
            "JPEG_${timeStamp}_", /* prefix */
            ".jpg", /* suffix */
            storageDir /* directory */
        ).apply {
            // Save a file: path for use with ACTION_VIEW intents
            currentPhotoPath = absolutePath
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == Activity.RESULT_OK) {

            val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, uriSavedImage)

            val imageViewAttachmentPreview = findViewById<ImageView>(R.id.imageViewAttachmentPreview)
            imageViewAttachmentPreview.visibility = View.VISIBLE
            imageViewAttachmentPreview.setImageBitmap(bitmap)
        } else if (requestCode == REQUEST_IMAGE_GET && resultCode == Activity.RESULT_OK) {

//            val fullPhotoUri: Uri = data?.data ?: error("Missing data")

            val clipData = data?.getClipData() ?: error("Missing ClipData")
            val itemCount = clipData.itemCount
            Log.d("MainActivity", "Item count: $itemCount")

            val item = clipData.getItemAt(0)
            val fullPhotoUri = item.uri

            Log.d("MainActivity", "Foto URI: $fullPhotoUri")
            val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, fullPhotoUri)

            val imageViewAttachmentPreview = findViewById<ImageView>(R.id.imageViewAttachmentPreview)
            imageViewAttachmentPreview.visibility = View.VISIBLE
            imageViewAttachmentPreview.setImageBitmap(bitmap)
        }
    }

    private fun onButtonReminderClick() {
        val intent = Intent(AlarmClock.ACTION_SET_ALARM).apply {
            putExtra(AlarmClock.EXTRA_MESSAGE, "Write Post")
            putExtra(AlarmClock.EXTRA_HOUR, 17)
            putExtra(AlarmClock.EXTRA_MINUTES, 0)
            putExtra(
                AlarmClock.EXTRA_DAYS, arrayListOf(
                    Calendar.SUNDAY,
                    Calendar.MONDAY,
                    Calendar.TUESDAY,
                    Calendar.WEDNESDAY,
                    Calendar.THURSDAY,
                    Calendar.FRIDAY,
                    Calendar.SATURDAY
                )
            )
        }
        if (intent.resolveActivity(packageManager) != null) {
            startActivity(intent)
        }
    }

    fun startLocalizationActivity(view: View) {
        val intentLocalization = Intent(this,LocalizationActivity::class.java)
        startActivity(intentLocalization)

        Toast.makeText(this,"Starting Local Activity",Toast.LENGTH_LONG).show()
    }
}
