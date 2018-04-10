package de.florianweinaug.comicscollection.ui.common

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import android.support.v4.content.FileProvider
import android.support.v7.app.AppCompatActivity
import de.florianweinaug.comicscollection.util.BitmapHelper
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*
import android.os.Bundle

abstract class CameraActivity : AppCompatActivity() {

    private var mCurrentPhotoPath: String? = null

    public override fun onSaveInstanceState(savedInstanceState: Bundle) {
        if (mCurrentPhotoPath != null) {
            savedInstanceState.putString(CAPTURED_PHOTO_PATH_KEY, mCurrentPhotoPath)
        }
        super.onSaveInstanceState(savedInstanceState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        if (savedInstanceState.containsKey(CAPTURED_PHOTO_PATH_KEY)) {
            mCurrentPhotoPath = savedInstanceState.getString(CAPTURED_PHOTO_PATH_KEY)
        }
        super.onRestoreInstanceState(savedInstanceState)
    }

    protected fun dispatchTakePictureIntent() {
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)

        if (takePictureIntent.resolveActivity(packageManager) != null) {
            try {
                val photoFile = createImageFile()
                val photoURI = FileProvider.getUriForFile(this,
                        "de.florianweinaug.comicscollection.fileprovider",
                        photoFile)

                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)

                startActivityForResult(takePictureIntent, REQUEST_PHOTO_CAPTURE)
            } catch (ex: IOException) {
            }
        }
    }

    private fun createImageFile(): File {
        val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US).format(Date())
        val imageFileName = "JPEG_" + timeStamp + "_"

        val storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES)

        val image = File.createTempFile(
                imageFileName,
                ".jpg",
                storageDir
        )

        mCurrentPhotoPath = image.absolutePath

        return image
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQUEST_PHOTO_CAPTURE && resultCode == Activity.RESULT_OK) {

            galleryAddPic()

            val file = File("$mCurrentPhotoPath.tmp")
            file.createNewFile()

            val bitmap = BitmapHelper.readBitmap(mCurrentPhotoPath!!)
            BitmapHelper.writeBitmap(bitmap, file)
            bitmap.recycle()

            processPhoto(file)
        }
    }

    private fun galleryAddPic() {
        val file = File(mCurrentPhotoPath)
        val contentUri = Uri.fromFile(file)

        val mediaScanIntent = Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE)
        mediaScanIntent.data = contentUri

        sendBroadcast(mediaScanIntent)
    }

    protected abstract fun processPhoto(file: File)

    companion object {
        const val REQUEST_PHOTO_CAPTURE = 1
        const val CAPTURED_PHOTO_PATH_KEY = "mCurrentPhotoPath"
    }
}