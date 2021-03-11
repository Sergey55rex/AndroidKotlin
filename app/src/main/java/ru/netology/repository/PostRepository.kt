package ru.netology.repository

import androidx.lifecycle.LiveData
import ru.netology.Post

interface PostRepository {
    fun getAll(): LiveData<List<Post>>
    fun likeById(id: Long)
    fun toSendsById(id: Long)
    fun viewingById(id: Long)
    fun removeById(id: Long)
    fun save(post: Post)
}