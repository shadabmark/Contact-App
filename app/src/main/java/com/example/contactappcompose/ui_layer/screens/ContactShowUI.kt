package com.example.contactappcompose.ui_layer.screens

import android.graphics.BitmapFactory
import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.Card
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.contactappcompose.R
import com.example.contactappcompose.ui.theme.AppColor
import com.example.contactappcompose.ui_layer.appViewModel.ContactAppViewModel
import com.example.contactappcompose.ui_layer.contactState.ContactState
import com.example.contactappcompose.ui_layer.navigation.ContactAdd
import com.example.contactappcompose.ui_layer.navigation.ContactDetails
import com.example.contactappcompose.ui_layer.navigation.ContactShow

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun ContactShowUI(
    navController: NavController,
    viewModel: ContactAppViewModel = hiltViewModel(),
    state: ContactState
) {
    val filteredContacts by viewModel.filteredContacts.collectAsState()
    val menuStates = remember { mutableStateOf<Map<Int, Boolean>>(emptyMap()) }
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxHeight()
                    ) {
                        Text(
                            text = "Contacts",
                            color = Color.White,
                            fontSize = 22.sp,
                            fontWeight = FontWeight.SemiBold,
                            fontFamily = FontFamily.Serif
                        )
                    }
                },
                navigationIcon = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxHeight()
                    ) {
                        Icon(
                            imageVector = Icons.Default.Menu,
                            contentDescription = null,
                            modifier = Modifier
                                .size(32.dp)
                                .clickable {
                                    // Dropdown Item [ FvtContact, Settings->( UnBlock, DeleteRecovery]
                                }
                        )
                    }
                    Spacer(modifier = Modifier.width(35.dp))
                },
                modifier = Modifier.height(86.dp),
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = AppColor,
                    titleContentColor = Color.White,
                    navigationIconContentColor = Color.White
                )
            )
        },

        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    viewModel.state.value.id.value = null
                    viewModel.state.value.name.value = ""
                    viewModel.state.value.phoneNumber.value = ""
                    viewModel.state.value.email.value = ""
                    viewModel.state.value.image.value = null
                    navController.navigate(ContactAdd)
                },
                containerColor = AppColor
            ) {
                Icon(
                    imageVector = Icons.Rounded.Add, contentDescription = "Add",
                    tint = Color.White
                )
            }
        }
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            item {
                Spacer(modifier = Modifier.height(35.dp))
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp)
                ) {
                    OutlinedTextField(
                        value = viewModel.searchQuery.collectAsState().value,
                        onValueChange = { viewModel.updateSearchQuery(it) },
                        label = { Text(text = "Search....") },
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = AppColor,
                            unfocusedBorderColor = Color.Gray
                        ),
                        modifier = Modifier.size(width = 400.dp, height = 60.dp),
                        shape = RoundedCornerShape(12.dp),
                        trailingIcon = {
                            Icon(Icons.Filled.Search, contentDescription = null)
                        }
                    )
                }
                Spacer(modifier = Modifier.height(20.dp))
            }
            items(filteredContacts) { contact ->
                val isMenuOpen = menuStates.value[contact.id] ?: false

                Card(
                    modifier = Modifier
                        .size(height = 85.dp, width = 420.dp)
                        .padding(start = 8.dp, top = 6.dp, end = 8.dp, bottom = 8.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = 6.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 12.dp, end = 12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            if (contact.image == null) {
                                Image(
                                    painter = painterResource(id = R.drawable.person00),
                                    contentDescription = null,
                                    modifier = Modifier
                                        .size(56.dp)
                                        .clip(CircleShape),
                                    contentScale = ContentScale.Crop
                                )
                            } else {
                                Image(
                                    bitmap = BitmapFactory.decodeByteArray(
                                        contact.image,
                                        0,
                                        contact.image.size
                                    ).asImageBitmap(), contentDescription = null,
                                    modifier = Modifier
                                        .size(60.dp)
                                        .clip(CircleShape),
                                    contentScale = ContentScale.Crop
                                )
                            }
                            Spacer(modifier = Modifier.width(55.dp))
                            Text(
                                text = contact.name,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                            Spacer(modifier = Modifier.weight(1f))
                            Icon(Icons.Filled.MoreVert, contentDescription = null,
                                modifier = Modifier.clickable {
                                    menuStates.value = menuStates.value.toMutableMap()
                                        .apply { contact.id?.let { it1 -> put(it1, !isMenuOpen) } }
                                }
                            )
                            DropdownMenu(
                                expanded = isMenuOpen,
                                onDismissRequest = {
                                    menuStates.value = menuStates.value.toMutableMap()
                                        .apply { contact.id?.let { it1 -> put(it1, false) } }
                                }
                            ) {
                                DropdownMenuItem(
                                    text = { Text(text = "Delete") },
                                    onClick = {
                                        state.id.value = contact.id
                                        state.name.value = contact.name
                                        state.phoneNumber.value = contact.phoneNumber
                                        state.email.value = contact.email
                                        state.image.value = contact.image
                                        viewModel.deleteContact()
                                    }
                                )
                                DropdownMenuItem(
                                    text = { Text(text = "More") },
                                    onClick = {
                                        state.name.value = contact.name
                                        state.phoneNumber.value = contact.phoneNumber
                                        state.email.value = contact.email
                                        state.id.value = contact.id
                                        state.image.value = contact.image
                                        navController.navigate(ContactDetails)
                                        menuStates.value = menuStates.value.toMutableMap()
                                            .apply { contact.id?.let { it1 -> put(it1, false) } }
                                    }
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}