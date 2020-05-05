package com.tehran.motivation.note

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tehran.motivation.data.Motivation
import saman.zamani.persiandate.PersianDate
import saman.zamani.persiandate.PersianDateFormat

class NoteViewModel : ViewModel() {
    val motivationList = MutableLiveData<List<Motivation>>()

    init {
        motivationList.value =
            arrayListOf(Motivation(0, "title", "hi"), Motivation(1, "title", "hi"))
    }
}