package com.test.baatcheet.presentation.chat

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.test.baatcheet.data.usecases.ListenToMessagesUseCase
import com.test.baatcheet.data.usecases.SendMessageUseCase
import com.test.baatcheet.domain.model.MessageModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class ChatState(
    val currentMessage: String = "",
    val messages: List<MessageModel> = emptyList(),
)

class ChatViewModel(
    private val sendMessageUseCase: SendMessageUseCase,
    private val listenToMessagesUseCase: ListenToMessagesUseCase,
) : ViewModel() {
    private val _state = MutableStateFlow(ChatState())
    val state = _state.asStateFlow()

    fun setMessage(message: String) {
        _state.update {
            it.copy(
                currentMessage = message
            )
        }
    }

    fun sendMessage(toUserId: String) {
        val senderId = FirebaseAuth.getInstance().currentUser?.uid ?: return
        val message = _state.value.currentMessage
        if (message.isNotEmpty()) {
            viewModelScope.launch {
                sendMessageUseCase.invoke(
                    senderId = senderId,
                    receiverId = toUserId,
                    message = message
                )
                _state.update {
                    it.copy(
                        currentMessage = ""
                    )
                }
            }
        }
    }

    fun fetchMessage(
        currentUserId: String,
        otherUserId: String,
    ) {
        viewModelScope.launch {
            listenToMessagesUseCase.invoke(
                currentUserId = currentUserId,
                otherUserId = otherUserId,
                onMessageChanged = {messages->
                    _state.update {
                        it.copy(
                            messages =messages
                        )
                    }
                }
            )
        }
    }
}