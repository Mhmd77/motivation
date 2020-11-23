package com.tehran.motivation.util

import android.content.Context
import androidx.preference.PreferenceManager
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException
import java.util.*
import kotlin.jvm.Throws

class ReceivedCookiesInterceptor(private val context: Context) :
    Interceptor {
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalResponse = chain.proceed(chain.request())
        if (originalResponse.headers("Set-Cookie").isNotEmpty()) {
            val cookies =
                PreferenceManager.getDefaultSharedPreferences(context)
                    .getStringSet(
                        "PREF_COOKIES",
                        HashSet()
                    ) as HashSet<String>
            originalResponse.headers("Set-Cookie").forEach{
                cookies.add(it)
            }
            val memes =
                PreferenceManager.getDefaultSharedPreferences(context).edit()
            memes.putStringSet("PREF_COOKIES", cookies).apply()
            memes.commit()
        }
        return originalResponse
    }

}