package bauri.palash.panktimob

import android.content.Context
import java.io.File


fun saveToCache(appCon: Context, filename: String, src: String) {

    val f = File(appCon.cacheDir, filename)
    f.writeBytes(src.toByteArray(Charsets.UTF_8))

}

fun isCacheExists(appCon: Context, filename: String): Boolean {
    return File(appCon.cacheDir, filename).exists()
}

fun readFromCache(appCon: Context, filename: String): String {
    return if (isCacheExists(appCon, filename)) {
        File(appCon.cacheDir, filename).readText(Charsets.UTF_8)
    } else {
        ""
    }
}

