package com.example.contactappcompose.ui_layer.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.contactappcompose.ui_layer.appViewModel.ContactAppViewModel
import com.example.contactappcompose.ui_layer.screens.AddContactUI
import com.example.contactappcompose.ui_layer.screens.ContactDetailsUI
import com.example.contactappcompose.ui_layer.screens.ContactShowUI

@Composable
fun AppNavigation(viewModel: ContactAppViewModel = hiltViewModel()) {
    val state by viewModel.state.collectAsState()
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = ContactShow) {

        composable<ContactShow> {
            ContactShowUI(navController = navController, viewModel, state)
        }

        composable<ContactAdd> {
            AddContactUI(navController = navController, viewModel, state)
        }

        composable<ContactDetails> {
            ContactDetailsUI(navController = navController, viewModel, state)
        }
    }
}