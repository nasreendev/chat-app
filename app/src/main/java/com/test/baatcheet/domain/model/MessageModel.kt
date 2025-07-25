package com.test.baatcheet.domain.model

data class MessageModel(
    val senderId: String="",
    val receiverId: String="",
    val message: String="",
    val timestamp: Long= System.currentTimeMillis()
)
