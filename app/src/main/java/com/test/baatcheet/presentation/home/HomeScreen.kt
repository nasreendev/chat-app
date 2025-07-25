package com.test.baatcheet.presentation.home

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.test.baatcheet.R
import com.test.baatcheet.core.TextView
import com.test.baatcheet.ui.theme.BgColor
import com.test.baatcheet.ui.theme.MainColor
import org.koin.androidx.compose.koinViewModel
import androidx.compose.runtime.getValue
import com.test.baatcheet.core.LoadingBox
import com.test.baatcheet.data.network.NetworkResponse
import com.test.baatcheet.presentation.home.components.UserCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: HomeViewModel = koinViewModel(),
    popBackStack: () -> Unit,
    modifier: Modifier = Modifier,
    navToChat: (String, String) -> Unit,
) {

    val state by viewModel.state.collectAsStateWithLifecycle()
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                TextView(
                    value = "BaatCheet", fontSize = 22, color = Color.White
                )
            }, actions = {
                Image(
                    painter = painterResource(R.drawable.ic_logout),
                    contentDescription = "",
                    modifier = Modifier
                        .padding(end = 16.dp)
                        .clip(CircleShape)
                        .clickable {
                            viewModel.signOut()
                            popBackStack.invoke()
                        },
                    contentScale = ContentScale.Crop,
                    colorFilter = ColorFilter.tint(Color.White)
                )
            }, colors = TopAppBarDefaults.topAppBarColors(
                containerColor = MainColor
            )
            )
        }, containerColor = BgColor, modifier = modifier
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
                    items(response.data) { model ->
                        UserCard(
                            user = model, navToChat = {
                                navToChat.invoke(model.name,model.id)
                            })
                    }
                }
            }
        }
    }
}