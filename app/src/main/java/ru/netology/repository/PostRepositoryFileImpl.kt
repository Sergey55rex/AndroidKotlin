package ru.netology.repository

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import ru.netology.Post

class PostRepositoryFileImpl(
    private val context: Context,
) : PostRepository {
    private val gson = Gson()
    private val type = TypeToken.getParameterized(List::class.java, Post::class.java).type
    private val filename = "posts.json"
    private var nextId = 1L
    private var posts = emptyList<Post>()
    private val data = MutableLiveData(posts)

    init {
        val file = context.filesDir.resolve(filename)
        if (file.exists()) {
            // если файл есть - читаем
            context.openFileInput(filename).bufferedReader().use {
                posts = gson.fromJson(it, type)
                data.value = posts
            }
        } else {
            // если нет, записываем пустой массив
            sync()
        }
    }
    // для презентации убрали пустые строки
    override fun getAll(): LiveData<List<Post>> = data

//    override fun save(post: Post) {
//        if (post.id == 0L) {
//            // TODO: remove hardcoded author & published
//            posts = listOf(
//                post.copy(
//                    id = nextId++,
//                    author = "Me",
//                    liked  = false,
//                    published = "now"
//                )
//            ) + posts
//            data.value = posts
//            sync()
//            return
//        }
//
//        posts = posts.map {
//            if (it.id != post.id) it else it.copy(content = post.content)
//        }
//        data.value = posts
//        sync()
//    }

    override fun save(post: Post) {
        if (post.id == 0L) {
            // TODO: author, published
            posts = listOf(
                post.copy(
                    id = nextId++,
                    author = "Me",
                    liked = false,
                    published = "now",
                    likes = 0,
                    toSend = false,
                    viewing = false
                )
            ) + posts
            data.value = posts
            sync()
            return
        }

        posts = posts.map {
            if (it.id != post.id) it else it.copy(content = post.content)
        }
        data.value = posts
        sync()
    }

    override fun likeById(id: Long) {
        posts = posts.map{
            if (it.id != id) it else
               it.copy(liked = !it.liked, likes = if (it.liked) it.likes-1 else it.likes+1)
        }
        data.value = posts
        sync()
    }

    override fun toSendsById(id: Long) {
        posts = posts.map{
            if (it.id != id) it else it.copy(toSends = if (it.toSend) it.toSends+1 else it.toSends+1)
        }
        data.value = posts
        sync()
    }

    override fun viewingById(id: Long) {
        posts = posts.map {
            if (it.id != id) it else it.copy(viewings = if (it.viewing) it.viewings+1 else it.viewings+1)
        }
        data.value = posts
        sync()
    }

    override fun removeById(id: Long) {
        posts = posts.filter { it.id != id }
        data.value = posts
        sync()
    }

    private fun sync() {
        context.openFileOutput(filename, Context.MODE_PRIVATE).bufferedWriter().use {
            it.write(gson.toJson(posts))
        }
    }
}