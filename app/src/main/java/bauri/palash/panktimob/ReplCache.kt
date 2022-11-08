package bauri.palash.panktimob

import android.content.Context
import java.io.File
import java.nio.charset.Charset

const val CACHE_FILE = "replcache.txt"

fun saveToCache( appCon : Context , src : String){
    /*
    val f = appCon.openFileOutput(CACHE_FILE , Context.MODE_PRIVATE).use {
        it.write(src.toByteArray())
    }*/
    val f = File(appCon.cacheDir , CACHE_FILE)
    f.writeBytes(src.toByteArray(Charsets.UTF_8))

}

fun isCacheExists(appCon: Context) : Boolean{
    return File(appCon.cacheDir , CACHE_FILE).exists()
}

fun readFromCache(appCon: Context) : String{
    if (isCacheExists(appCon)) {
        return File(appCon.cacheDir , CACHE_FILE).readText(Charsets.UTF_8)
    }else{
        return "x"
    }
}

