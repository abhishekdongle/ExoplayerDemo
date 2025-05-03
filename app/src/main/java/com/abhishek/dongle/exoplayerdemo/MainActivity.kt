package com.abhishek.dongle.exoplayerdemo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.abhishek.dongle.exoplayerdemo.ui.theme.ExoplayerDemoTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ExoplayerDemoTheme {
                PlayerScreen()
            }
        }
    }
}