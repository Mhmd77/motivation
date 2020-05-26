package com.tehran.motivation.util

import android.view.View
import androidx.databinding.BindingAdapter
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.tehran.motivation.Event
import com.tehran.motivation.category.CategoryAdapter
import com.tehran.motivation.data.Category
import com.tehran.motivation.data.Motivation
import com.tehran.motivation.note.NoteAdapter

@BindingAdapter("motivationList")
fun RecyclerView.motivationList(motivations: List<Motivation>?) = motivations.let {
    (adapter as NoteAdapter).submitList(it)
}

@BindingAdapter("categoryList")
fun RecyclerView.categoryList(categories: List<Category>?) = categories.let {
    (adapter as CategoryAdapter).submitList(it)
}

fun View.showSnackbar(snackbarText: String, timeLength: Int) {
    Snackbar.make(this, snackbarText, timeLength).run {
        show()
    }
}

/**
 * Triggers a snackbar message when the value contained by snackbarTaskMessageLiveEvent is modified.
 */
fun View.setupSnackbar(
    lifecycleOwner: LifecycleOwner,
    snackbarEvent: LiveData<Event<String>>,
    timeLength: Int
) {

    snackbarEvent.observe(lifecycleOwner, Observer { event ->
        event.getContentIfNotHandled()?.let {
            showSnackbar(it, timeLength)
        }
    })
}
