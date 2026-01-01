package com.sjani.usnationalparkguide.ui.screens.onboarding

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.sjani.usnationalparkguide.R
import com.sjani.usnationalparkguide.ui.theme.Primary
import com.sjani.usnationalparkguide.ui.theme.USNationalParkGuideTheme

private val BottomGreen = Color(0xFF3D5A45)

@Composable
fun OnboardingScreen(
    onGetStarted: () -> Unit = {}
) {
    val context = LocalContext.current
    
    Column(modifier = Modifier.fillMaxSize()) {
        // Top section with image background
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            // Background image - fills only this section, bottom aligned
            AsyncImage(
                model = ImageRequest.Builder(context)
                    .data(R.drawable.onboarding_background_fill)
                    .crossfade(true)
                    .build(),
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop,
                alignment = Alignment.BottomCenter
            )
            
            // Top content (icon and title)
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .statusBarsPadding()
                    .padding(horizontal = 24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(24.dp))
                
                // App icon
                Image(
                    painter = painterResource(id = R.drawable.ic_app_square),
                    contentDescription = "App Icon",
                    modifier = Modifier
                        .size(140.dp)
                        .clip(RoundedCornerShape(28.dp)),
                    contentScale = ContentScale.Fit
                )
                
                Spacer(modifier = Modifier.height(24.dp))
                
                // Title
                Text(
                    text = "Explore America's\nNational Parks",
                    style = MaterialTheme.typography.headlineLarge.copy(
                        fontWeight = FontWeight.Bold,
                        fontSize = 32.sp,
                        lineHeight = 40.sp
                    ),
                    color = Color(0xFF2C3E2F),
                    textAlign = TextAlign.Center
                )
            }
        }
        
        // Bottom green section
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(BottomGreen)
                .navigationBarsPadding()
                .padding(horizontal = 24.dp)
                .padding(top = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Subtitle
            Text(
                text = "Discover the beauty of great outdoors",
                style = MaterialTheme.typography.bodyLarge,
                color = Color(0xFFFFFFFF),
                textAlign = TextAlign.Center
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // Get Started button
            Button(
                onClick = onGetStarted,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = RoundedCornerShape(28.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF5A7D62)
                )
            ) {
                Text(
                    text = "Get Started",
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.SemiBold
                    ),
                    color = Color.White
                )
            }
            
            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun OnboardingScreenPreview() {
    USNationalParkGuideTheme {
        OnboardingScreen()
    }
}

