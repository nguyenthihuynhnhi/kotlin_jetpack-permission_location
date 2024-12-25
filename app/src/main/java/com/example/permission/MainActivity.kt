package com.example.permission

import android.Manifest
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

class MainActivity : ComponentActivity() {

    private val locationPermissions = arrayOf(
        Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.ACCESS_COARSE_LOCATION
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LocationPermissionApp()
        }
    }

    @OptIn(ExperimentalMaterial3Api::class) // Sử dụng annotation để xử lý các API đang thử nghiệm
    @Composable
    fun LocationPermissionApp() {
        // State to track permission results
        var permissionsGranted by remember { mutableStateOf(false) }

        // Permission launcher
        val permissionLauncher = rememberLauncherForActivityResult(
            contract = ActivityResultContracts.RequestMultiplePermissions()
        ) { permissions ->
            // Update state when permissions are granted or denied
            permissionsGranted = permissions[Manifest.permission.ACCESS_FINE_LOCATION] == true &&
                    permissions[Manifest.permission.ACCESS_COARSE_LOCATION] == true
        }

        Scaffold(
            topBar = { TopAppBar(title = { Text("Location Permission App") }) },
            content = { contentPadding ->
                // Sử dụng contentPadding để thêm padding cho Column
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(contentPadding)
                        .padding(16.dp),
                    verticalArrangement = Arrangement.Center
                ) {
                    Button(onClick = { permissionLauncher.launch(locationPermissions) }) {
                        Text("Request Location Permissions")
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = if (permissionsGranted) {
                            "Location permissions granted!"
                        } else {
                            "Please grant location permissions."
                        }
                    )
                }
            }
        )
    }
}
