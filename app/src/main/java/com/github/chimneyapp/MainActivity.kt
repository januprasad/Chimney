package com.github.chimneyapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.github.chimney.VectorComposeView
import com.github.chimney.VectorViewType
import com.github.chimneyapp.ui.theme.ChimneyTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ChimneyTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background,
                ) {
                    val vectors = listOf(
                        R.drawable.arabic_banner_2,
                        R.drawable.arabic_banner_1,
                        R.drawable.arabic_banner_3,
                        R.drawable.arabic_banner_3_1,
                    )
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.SpaceBetween,
                    ) {
                        itemsIndexed(
                            vectors,
                        ) { index, item ->
                            Column(modifier = Modifier.width(200.dp)) {
                                VectorComposeView(
                                    VectorViewType.ImageVector(
                                        vectorResource = item,
                                        imageSrc = R.drawable.mosque,
                                        aspectRatio = 1F,
                                        borderEnabled = true,
                                    ),
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
