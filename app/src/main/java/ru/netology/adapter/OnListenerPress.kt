package ru.netology.adapter

import ru.netology.dto.Post

interface OnListenerPress {
    fun onLikeListener(post: Post)
    fun onToSendListener(post: Post)
    fun onVievingListener(post: Post)
    fun onEdit(post: Post) {}
    fun onRemove(post: Post) {}
    fun onPlay(post: Post) {}

}