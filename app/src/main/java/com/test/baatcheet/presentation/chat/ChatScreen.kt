package com.test.baatcheet.presentation.chat

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.components.Lazy
import com.test.baatcheet.core.TextView
import com.test.baatcheet.core.VerticalSpace
import com.test.baatcheet.presentation.chat.components.ChatInputField
import com.test.baatcheet.presentation.chat.components.ChatMessageItem
import com.test.baatcheet.ui.theme.MainColor
import org.koin.androidx.compose.koinViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatScreen(
    id: String,
    name: String,
    viewModel: ChatViewModel = koinViewModel(),
    popBackStack: () -> Unit,
) {

    val state by viewModel.state.collectAsStateWithLifecycle()
    val currentUserId = FirebaseAuth.getInstance().currentUser?.uid
    LaunchedEffect(id,currentUserId) {
        if (currentUserId != null) {
            viewModel.fetchMessage(
                otherUserId = id,
                currentUserId = currentUserId
            )
        }
    }
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    TextView(name, color = Color.White)
                },
                navigationIcon = {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "",
                        tint = Color.White,
                        modifier = Modifier
                            .padding(start = 16.dp, end = 10.dp)
                            .clickable {
                                popBackStack.invoke()
                            }
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MainColor
                )
            )
        },
        bottomBar = {
            ChatInputField(
                message = state.currentMessage,
                onMessageChange = {
                    viewModel.setMessage(it)
                },
                onSendClick = {
                    viewModel.sendMessage(id)
                }
            )
        }
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            items(state.messages) {model->
                VerticalSpace()
                ChatMessageItem(
                    model =model ,
                    isCurrentUser=model.senderId==currentUserId
                )
            }
        }
    }
}