package de.florianweinaug.comicscollection.util

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.support.media.ExifInterface
import java.io.File
import java.io.FileOutputStream

object BitmapHelper {

    private const val BITMAP_SIZE = 800
    private const val BITMAP_COMPRESSION = 90

    @JvmStatic
    fun writeBitmap(bitmap: Bitmap, file: File) {
        val fOut = FileOutputStream(file)

        bitmap.compress(Bitmap.CompressFormat.JPEG, BITMAP_COMPRESSION, fOut)

        fOut.flush()
        fOut.close()
    }

    @JvmStatic
    fun readBitmap(path: String) : Bitmap {
        val rotation = getRotation(path)
        val bitmap = BitmapFactory.decodeFile(path)

        return resizeAndRotate(bitmap, BITMAP_SIZE, rotation)
    }

    private fun resizeAndRotate(bitmap: Bitmap, targetSize: Int, rotation: Int): Bitmap {
        val size = Math.min(bitmap.width, bitmap.height)
        val scale = targetSize / size.toFloat()

        val matrix = Matrix()
        matrix.postScale(scale, scale)
        matrix.postRotate(rotation.toFloat())

        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.width, bitmap.height, matrix, false)
    }

    private fun getRotation(path: String): Int {
        var rotate = 0

        try {
            val exif = ExifInterface(path)
            val orientation = exif.getAttributeInt(
                    ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_NORMAL)

            rotate = when (orientation) {
                ExifInterface.ORIENTATION_ROTATE_270 -> 270
                ExifInterface.ORIENTATION_ROTATE_180 -> 180
                ExifInterface.ORIENTATION_ROTATE_90 -> 90
                else -> 0
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return rotate
    }
}