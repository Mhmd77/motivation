package com.tehran.motivation.note

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tehran.motivation.data.Motivation
import saman.zamani.persiandate.PersianDate
import saman.zamani.persiandate.PersianDateFormat

class NoteViewModel : ViewModel() {
    val motivationList = MutableLiveData<List<Motivation>>()

    init {
        addDummyNotes()
    }

    private fun addDummyNotes() {
        val notes = arrayListOf<Motivation>()
        val n = Motivation(
            title = "رویاهایتان را بسازید، در غیر این صورت فرد دیگری شما را برای ساختن رویایش به کار خواهد گرفت.",
            description = "توضیحات", time = "time"
        )
        notes.add(n)
        notes.add(n)
        notes.add(n)
        motivationList.value = notes
    }
}