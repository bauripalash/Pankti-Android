package bauri.palash.panktimob.views

import android.os.Build
import android.os.Environment
import java.io.File

fun GetCommonDocPath(folder: String): File? {
    val dir: File = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
        Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS + "/" + folder)

    } else {
        File(Environment.getExternalStorageDirectory().path + "/" + folder)
    }

    if (!dir.exists()) {

        val suc = dir.mkdirs()

        if (!suc) {
            return null
        }
    }
    return dir

}