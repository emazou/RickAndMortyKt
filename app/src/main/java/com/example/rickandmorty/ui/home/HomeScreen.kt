package com.example.rickandmorty.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material.icons.rounded.NavigateBefore
import androidx.compose.material.icons.rounded.NavigateNext
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.rickandmorty.R
import com.example.rickandmorty.domain.model.Characters
import com.example.rickandmorty.ui.home.components.CharacterItem
import kotlinx.coroutines.flow.collectLatest

@Composable
fun HomeScreen(
    onItemClicked: (Int) -> Unit,
    viewModel: HomeViewModel = hiltViewModel()
){
    val state =viewModel.state
    val eventFlow = viewModel.eventFlow
    val scaffoldState = rememberScaffoldState()
    
    LaunchedEffect(key1 = true ){
        eventFlow.collectLatest { event ->
            when(event) {
                is HomeViewModel.UIEvent.ShowSnackBar -> {
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = event.message
                    )
                }
            }
        }
    }
    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            HomeToBar()
        },
        bottomBar = {
            HomeBottomBar(
                showPrevious = state.showPrevious ,
                showNext = state.showNext,
                onPreviousPressed = { viewModel.getCharacters(false) },
                onNextPressed = { viewModel.getCharacters(true) },
            )
        }
    ){ innerPadding ->
        HomeContent(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxHeight(),
            onItemClicked = { onItemClicked(it)},
            isLoading = state.isLoading,
            characters = state.characters
        )
    }
}
@Composable
private fun HomeToBar(
    modifier: Modifier = Modifier
){
    TopAppBar (
        title = {
            Icon(
                Icons.Rounded.Favorite,
                contentDescription = null,
                tint = Color(0xFF3A491A)
            )
            Text(
                text = stringResource(id = R.string.home_title),
                color= Color(0xFF4B6416),
                fontWeight = FontWeight.Bold,
                fontSize = 25.sp,
                modifier = modifier
                    .wrapContentSize(Alignment.Center)
                    .padding(horizontal = 10.dp)
            )
        },
        backgroundColor = Color(0xFFA1AE7C),
    )
}

@Composable
fun HomeContent(
    modifier: Modifier = Modifier,
    onItemClicked: (Int) -> Unit,
    isLoading: Boolean = false,
    characters: List<Characters> = emptyList(),

){
    val scrollState = rememberScrollState()
    Box(
        modifier = modifier,
    ) {
        LazyColumn(
            contentPadding = PaddingValues(vertical = 6.dp),
            modifier = Modifier
                .fillMaxWidth(),
            content = {
                items(characters.size){ index ->
                    CharacterItem(
                        modifier = Modifier
                            .fillMaxWidth(),
                        item = characters[index],
                        onItemClicked = { onItemClicked(it) }
                    )
                }
            }
        )
        if (isLoading) FullScreenLoading()
    }
}

@Composable
private fun HomeBottomBar(
    showPrevious: Boolean,
    showNext: Boolean,
    onPreviousPressed: () -> Unit,
    onNextPressed: () -> Unit
){
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.Transparent)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFFA2AF7C)),
            horizontalArrangement = Arrangement.SpaceBetween,
        ){
            TextButton(
                modifier = Modifier
                    .weight(1f)
                    .height(48.dp),
                enabled = showPrevious,
                onClick = onPreviousPressed
            ) {
                Icon(
                    Icons.Rounded.NavigateBefore,
                    contentDescription = null,
                    tint = Color(0xFF3A491A)
                )
                Text(
                    text = stringResource(id = R.string.previous_button),
                    color = Color(0xFF3A491A),
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                )
            }
            TextButton(
                modifier = Modifier
                    .weight(1f)
                    .height(48.dp),
                enabled = showNext,
                onClick = onNextPressed
            ) {
                Text(
                    text = stringResource(id = R.string.next_button),
                    color = Color(0xFF3A491A),
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,

                )
                Icon(
                    Icons.Rounded.NavigateNext,
                    contentDescription = null,
                    tint = Color(0xFF3A491A)
                )
            }
        }
    }
}

@Composable
private fun FullScreenLoading() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .wrapContentSize()
    ){
        CircularProgressIndicator()
    }
}