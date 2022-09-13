package pro.yakuraion.englishhelper.common

import android.util.Log
import java.io.BufferedInputStream
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.net.MalformedURLException
import java.net.SocketTimeoutException
import java.net.URL

private const val DOWNLOAD_BUFFER_SIZE = 1024

fun File.createNewFilesWithParentDirs() {
    parentFile?.mkdirs()
    createNewFile()
}

fun downloadFile(downloadUrl: String, outputFile: File): Boolean {
    return try {
        val url = URL(downloadUrl)
        val connection = url.openConnection()
        connection.connect()
        val input = BufferedInputStream(url.openStream())

        outputFile.createNewFilesWithParentDirs()
        val output = FileOutputStream(outputFile)
        val data = ByteArray(DOWNLOAD_BUFFER_SIZE)

        var count = input.read(data)
        while (count != -1) {
            output.write(data, 0, count)
            count = input.read(data)
        }

        output.flush()
        output.close()
        input.close()
        true
    } catch (e: IOException) {
        Log.e("downloadFile", e.message, e)
        false
    } catch (e: MalformedURLException) {
        Log.e("downloadFile", e.message, e)
        false
    } catch (e: SocketTimeoutException) {
        Log.e("downloadFile", e.message, e)
        false
    } catch (e: SecurityException) {
        Log.e("downloadFile", e.message, e)
        false
    }
}
