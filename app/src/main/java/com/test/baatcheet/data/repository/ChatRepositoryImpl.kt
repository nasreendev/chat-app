package com.test.baatcheet.data.repository

import com.google.firebase.firestore.FirebaseFirestore
import com.test.baatcheet.data.network.NetworkResponse
import com.test.baatcheet.domain.model.MessageModel
import com.test.baatcheet.domain.repository.ChatRepository

    fun generateChatId(uid1: String, uid2: String): String {
        return listOf(uid1, uid2).sorted().joinToString("_")
    }

    class ChatRepositoryImpl : ChatRepository {

        private val firestore = FirebaseFirestore.getInstance()
        override suspend fun sendMessage(
            senderId: String,
            receiverId: String,
            message: String,
        ): NetworkResponse<Boolean> {

            return try {
                val chatId = generateChatId(senderId, receiverId)
                val message = MessageModel(
                    senderId = senderId,
                    receiverId = receiverId,
                    message = message
                )
                firestore.collection("chats")
                    .document(chatId)
                    .collection("messages")
                    .add(message)
                NetworkResponse.Success(true)
            } catch (e: Exception) {
                NetworkResponse.Failure(e.localizedMessage ?: "")
            }

        }

        override suspend fun listenToMessages(
            currentUserId: String,
            otherUserId: String,
            onMessageChanged: (List<MessageModel>) -> Unit,
        ) {
            val chatId = generateChatId(currentUserId, otherUserId)
            firestore.collection("chats")
                .document(chatId)
                .collection("messages")
                .orderBy("timestamp")
                .addSnapshotListener {snapshot,_->
                    snapshot?.let {
                        val messages=it.documents.mapNotNull { doc ->
                            doc.toObject(MessageModel::class.java)
                        }
                        onMessageChanged(messages)
                    }
                }

        }
    }