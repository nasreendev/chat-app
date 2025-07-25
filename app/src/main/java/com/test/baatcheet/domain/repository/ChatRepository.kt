package com.test.baatcheet.domain.repository

import android.support.v4.os.ResultReceiver
import com.test.baatcheet.data.network.NetworkResponse
import com.test.baatcheet.domain.model.MessageModel

interface ChatRepository {
    suspend fun sendMessage(
        senderId: String,
        receiverId: String,
        message: String
    ): NetworkResponse<Boolean>

    suspend fun listenToMessages(
        currentUserId: String,
        otherUserId: String,
        onMessageChanged:(List<MessageModel>)-> Unit
    )
}