package com.example.rickandmorty.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.rickandmorty.ui.Screen.*
import com.example.rickandmorty.ui.home.HomeScreen

@Composable
fun RickAndMortyNavGraph(
    modifier: Modifier = Modifier,
    navigateToHome: () -> Unit,
    navigateToDetail: (Int) -> Unit,
    navController: NavHostController = rememberNavController(),
    startDestination: String = Home.route
){
    NavHost(
        navController = navController,
        modifier = modifier,
        startDestination = startDestination,
    ){
        composable(
            route = Home.route
        ){
            HomeScreen(
                onItemClicked = { navigateToDetail(it) }
            )
        }
    }
}