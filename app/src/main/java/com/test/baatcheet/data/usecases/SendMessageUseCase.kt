package com.test.baatcheet.data.usecases

import com.test.baatcheet.data.network.NetworkResponse
import com.test.baatcheet.domain.repository.ChatRepository

class SendMessageUseCase(
    private val repository: ChatRepository,
) {

    suspend fun invoke(
        senderId: String,
        receiverId: String,
        message: String,
    ): NetworkResponse<Boolean> = repository.sendMessage(
        senderId,
        receiverId,
        message
    )
}