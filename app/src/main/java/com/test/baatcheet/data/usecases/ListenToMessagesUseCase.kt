package com.test.baatcheet.data.usecases

import com.test.baatcheet.domain.model.MessageModel
import com.test.baatcheet.domain.repository.ChatRepository

class ListenToMessagesUseCase(
    private val repository: ChatRepository,
) {

    suspend fun invoke(
        currentUserId: String,
        otherUserId: String,
        onMessageChanged: (List<MessageModel>) -> Unit,
    ) {
        repository.listenToMessages(
            currentUserId = currentUserId,
            otherUserId = otherUserId,
            onMessageChanged = onMessageChanged
        )
    }
}