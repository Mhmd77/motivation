package com.tehran.motivation.util

import android.content.Context
import androidx.preference.PreferenceManager
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException
import java.util.*
import kotlin.jvm.Throws

class AddCookiesInterceptor(private val context: Context) : Interceptor {
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val builder = chain.request().newBuilder()
        val preferences =
            PreferenceManager.getDefaultSharedPreferences(context).getStringSet(
                PREF_COOKIES,
                HashSet()
            ) as HashSet<String>
        var cookiestring = ""
        for (cookie in preferences) {
            val parser = cookie.split(";".toRegex()).toTypedArray()
            cookiestring = cookiestring + parser[0] + "; "
        }
        builder.addHeader("Cookie", cookiestring)
        return chain.proceed(builder.build())
    }

    companion object {
        const val PREF_COOKIES = "PREF_COOKIES"
    }
}