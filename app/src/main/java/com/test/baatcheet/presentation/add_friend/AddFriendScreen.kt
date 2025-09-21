package com.test.baatcheet.presentation.add_friend

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.test.baatcheet.core.LoadingBox
import com.test.baatcheet.core.TextView
import com.test.baatcheet.data.network.NetworkResponse
import com.test.baatcheet.presentation.home.HomeViewModel
import com.test.baatcheet.presentation.home.components.AddUserCard
import com.test.baatcheet.ui.theme.BgColor
import com.test.baatcheet.ui.theme.MainColor
import org.koin.androidx.compose.koinViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddFriendScreen(
    viewModel: HomeViewModel = koinViewModel(),
) {

    val state by viewModel.state.collectAsStateWithLifecycle()
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    TextView(
                        value = "Add Friend", fontSize = 22, color = Color.White
                    )
                },

                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MainColor
                )
            )
        }, containerColor = BgColor
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            when (val response = state.users) {
                is NetworkResponse.Failure -> {

                }

                NetworkResponse.Idle -> {

                }

                NetworkResponse.Loading -> {
                    item {
                        LoadingBox()
                    }
                }

                is NetworkResponse.Success -> {
                    items(response.data) { userModel ->
                        AddUserCard(
                            user = userModel,
                            onAddClick = {
                                viewModel.addFriend(userModel.id)
                            }
                        )
                    }
                }
            }
        }
    }
}