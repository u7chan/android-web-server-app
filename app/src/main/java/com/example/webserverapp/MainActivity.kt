package com.example.webserverapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import com.example.webserverapp.lib.WebServer
import com.example.webserverapp.ui.theme.WebServerAppTheme

class MainActivity : ComponentActivity() {

    companion object {
        private const val BIND_PORT = 8080
    }

    private val webserver: WebServer by lazy { WebServer(applicationContext, BIND_PORT) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        webserver.start()
        setContent {
            WebServerAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background
                ) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Starting server on http://localhost:$BIND_PORT",
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        webserver.stop()
    }
}

