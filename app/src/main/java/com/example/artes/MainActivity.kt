package com.example.artes

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ArtGalleryApp()
        }
    }
}

@Composable
fun ArtGalleryApp() {

    val artworks = listOf(
        "Abaporu - Tarsila do Amaral" to Pair(
            R.drawable.artwork1,
            "Abaporu, de Tarsila do Amaral, retrata uma figura humana de pés e mãos exagerados, simbolizando a força do povo brasileiro. A obra é um ícone do modernismo e inspirou o movimento antropofágico."
        ),
        "O Grito - Edvard Munch" to Pair(
            R.drawable.artwork2,
            "O Grito, de Edvard Munch, expressa angústia e desespero por meio de um rosto contorcido em um cenário distorcido. A obra é um marco do expressionismo, simbolizando a ansiedade humana."
        ),
        "A Noite Estrelada - Van Gogh" to Pair(
            R.drawable.artwork3,
            "Noite Estrelada, de Van Gogh, retrata um céu turbulento e vibrante sobre uma pacata vila. A obra transmite emoção e movimento, sendo um ícone do pós-impressionismo."
        )
    )

    var currentIndex by remember { mutableStateOf(0) }
    val context = LocalContext.current

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .pointerInput(Unit) {
                detectHorizontalDragGestures { _, dragAmount ->
                    if (dragAmount < -50) {
                        currentIndex = (currentIndex + 1) % artworks.size
                    } else if (dragAmount > 50) {
                        currentIndex = (currentIndex - 1 + artworks.size) % artworks.size
                    }
                }
            }
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = artworks[currentIndex].second.first),
                contentDescription = "Artwork Image",
                modifier = Modifier
                    .width(300.dp)
                    .height(280.dp)
                    .clip(RoundedCornerShape(16.dp))
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = artworks[currentIndex].first,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.CenterHorizontally)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = artworks[currentIndex].second.second,
                fontSize = 14.sp,
                fontWeight = FontWeight.Normal,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.CenterHorizontally)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                Button(
                    onClick = { currentIndex = (currentIndex - 1 + artworks.size) % artworks.size },
                    modifier = Modifier.pointerInput(Unit) {
                        detectTapGestures(
                            onLongPress = {
                                Toast.makeText(context, "Previous artwork", Toast.LENGTH_SHORT).show()
                            }
                        )
                    }
                ) {
                    Text("Previous")
                }
                Button(
                    onClick = { currentIndex = (currentIndex + 1) % artworks.size },
                    modifier = Modifier.pointerInput(Unit) {
                        detectTapGestures(
                            onLongPress = {
                                Toast.makeText(context, "Next artwork", Toast.LENGTH_SHORT).show()
                            }
                        )
                    }
                ) {
                    Text("Next")
                }
            }
        }
    }
}
