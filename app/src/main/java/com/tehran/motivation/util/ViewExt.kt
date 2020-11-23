package com.tehran.motivation.util

import android.net.Uri
import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.github.twocoffeesoneteam.glidetovectoryou.GlideToVectorYou
import com.google.android.material.snackbar.Snackbar
import com.tehran.motivation.Event
import com.tehran.motivation.category.CategoryAdapter
import com.tehran.motivation.data.Book
import com.tehran.motivation.data.Category
import com.tehran.motivation.data.Motivation
import com.tehran.motivation.data.Video
import com.tehran.motivation.library.BookAdapter
import com.tehran.motivation.library.VideoAdapter
import com.tehran.motivation.note.NoteAdapter


@BindingAdapter("motivationList")
fun RecyclerView.motivationList(motivations: List<Motivation>?) = motivations?.let {
    (adapter as NoteAdapter).submitList(it)
}

@BindingAdapter("categoryList")
fun RecyclerView.categoryList(categories: List<Category>?) = categories?.let {
    (adapter as CategoryAdapter).submitList(it)
}

@BindingAdapter("videoList")
fun RecyclerView.videoList(videos: List<Video>?) = videos?.let {
    (adapter as VideoAdapter).submitList(it)
}

@BindingAdapter("bookList")
fun RecyclerView.bookList(books: List<Book>?) = books?.let {
    (adapter as BookAdapter).submitList(it)
}

@BindingAdapter("thumbnail")
fun ImageView.thumbnail(url: String?) {
    url?.let {
        Glide.with(context)
            .asBitmap()
            .load(it)
            .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
            .into(this)
    }
}

fun View.showSnackbar(snackbarText: String, timeLength: Int) {

    Snackbar.make(this, snackbarText, timeLength).run {
        show()
    }
}

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

@BindingAdapter("loadSvg")
fun ImageView.loadSvg(url: String?) {
    GlideToVectorYou
        .init()
        .with(this.context)
        .load(Uri.parse(url ?: return), this)
}