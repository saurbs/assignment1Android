package com.example.sauravgautam_assignment1

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.example.sauravgautam_assignment1.dataStoreST
import com.example.sauravgautam_assignment1.ui.theme.SauravGautam_Assignment1Theme
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

@ExperimentalMaterial3Api
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SauravGautam_Assignment1Theme {

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainScreen()
                }
            }
        }
    }
}

@ExperimentalMaterial3Api
@Composable
fun MainScreen() {
    // context
    val context = LocalContext.current
    // scope
    val scope = rememberCoroutineScope()
    // datastore
    val dataStore = dataStoreST(context)

    val savedUsernameState = dataStore.getUsername.collectAsState(initial = "")
    val savedEmailState = dataStore.getEmail.collectAsState(initial = "")
    val savedIDState = dataStore.getID.collectAsState(initial = "")

    var username by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var id by remember { mutableStateOf("") }

    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxSize()) {

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(20.dp)
        ) {
            OutlinedTextField(
                value = username,
                onValueChange = { username = it },
                singleLine = true,
                label = {
                    Text("Username")
                },
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                singleLine = true,
                label = {
                    Text("Email")
                },
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = id,
                onValueChange = { id = it },
                singleLine = true,
                label = {
                    Text("ID")
                },
                modifier = Modifier.fillMaxWidth()
            )
        }

        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(bottom = 130.dp)) {
            Button(onClick = {
                scope.launch {
                    val savedUsername = dataStore.getUsername.first() ?: ""
                    val savedEmail = dataStore.getEmail.first() ?: ""
                    val savedID = dataStore.getID.first() ?: ""

                    // Update the UI state variables
                    username = savedUsername
                    email = savedEmail
                    id = savedID

                    Toast.makeText(context, "Load button clicked", Toast.LENGTH_SHORT).show()
                }
            }) {
                Text(text = "Load")
            }


            Button(onClick = {
                scope.launch {
                    // Save the data to DataStore
                    dataStore.saveInfo(username, email, id)

                    // Clear only the UI state variables
                    username = ""
                    email = ""
                    id = ""

                    // Show a toast message
                    Toast.makeText(context, "Save button clicked", Toast.LENGTH_SHORT).show()
                }
            }, Modifier.padding(horizontal = 16.dp)) {
                Text(text = "Save")
            }

            Button(onClick = {
                scope.launch {
                    dataStore.clearData()
                    username = ""
                    email = ""
                    id = ""
                }
                Toast.makeText(context, "Clear button clicked", Toast.LENGTH_SHORT).show()
            }) {
                Text(text = "Clear")
            }
        }

        if (username == "" || id == "") {
            Info(infoName = "Name", infoID = "College ID")
        }
        else {
            Info(infoName = username, infoID = id)
        }
    }

}

@Composable
fun Info(
    infoName: String,
    infoID: String
) {
    Box(
        modifier = Modifier
            .padding(20.dp)
            .fillMaxWidth()
            .border(1.dp, Color.Blue)
            .padding(16.dp)
    ) {
        Column {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Name:",
                    style = TextStyle(color = Color.Blue, fontSize = 20.sp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = infoName,
                    style = TextStyle(color = Color.Blue, fontSize = 20.sp)
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "College ID:",
                    style = TextStyle(color = Color.Blue, fontSize = 20.sp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = infoID,
                    style = TextStyle(color = Color.Blue, fontSize = 20.sp)
                )
            }
        }
    }
}




@ExperimentalMaterial3Api
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun Preview() {
    SauravGautam_Assignment1Theme {
        MainScreen()
    }
}
