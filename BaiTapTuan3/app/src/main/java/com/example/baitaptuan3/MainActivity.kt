package com.example.baitaptuan3

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.compose.ui.text.input.PasswordVisualTransformation

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme(colorScheme = lightColorScheme()) {
                AppNavigation()
            }
        }
    }
}

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "welcome") {
        composable("welcome") { WelcomeScreen(navController) }
        composable("list") { ComponentListScreen(navController) }
        composable("detail/{name}") { backStackEntry ->
            val name = backStackEntry.arguments?.getString("name")
            ComponentDetailScreen(navController, name)
        }
    }
}

// -------------------- Welcome Screen --------------------

@Composable
fun WelcomeScreen(navController: NavController) {
    val gradient = Brush.verticalGradient(
        listOf(Color(0xFF6A11CB), Color(0xFF2575FC))
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(gradient),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_launcher_foreground),
                contentDescription = "Logo",
                modifier = Modifier
                    .size(120.dp)
                    .clip(CircleShape)
                    .background(Color.White.copy(alpha = 0.2f))
                    .padding(16.dp)
            )

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                "Jetpack Compose",
                color = Color.White,
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold
            )

            Text(
                "Toolkit hiện đại để xây dựng giao diện Android nhanh, đẹp và trực quan.",
                color = Color.White.copy(alpha = 0.9f),
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(vertical = 16.dp)
            )

            Button(
                onClick = { navController.navigate("list") },
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier
                    .padding(top = 16.dp)
                    .width(180.dp)
                    .height(50.dp)
            ) {
                Text("Bắt đầu ngay", fontSize = 18.sp)
            }
        }
    }
}

// -------------------- List Screen --------------------

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ComponentListScreen(navController: NavController) {
    val items = listOf("Text", "Image", "TextField", "PasswordField", "Column", "Row")

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Thành phần Compose", fontWeight = FontWeight.Bold) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF2575FC),
                    titleContentColor = Color.White
                )
            )
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
        ) {
            items(items) { item ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                        .shadow(4.dp, RoundedCornerShape(12.dp))
                        .clickable { navController.navigate("detail/$item") },
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text(
                        text = item,
                        modifier = Modifier.padding(20.dp),
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        }
    }
}

// -------------------- Detail Screen --------------------

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ComponentDetailScreen(navController: NavController, name: String?) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(name ?: "Chi tiết", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Quay lại", tint = Color.White)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF6A11CB),
                    titleContentColor = Color.White
                )
            )
        }
    ) { padding ->
        Crossfade(targetState = name, modifier = Modifier.padding(padding)) { screen ->
            when (screen) {
                "Text" -> TextDetail()
                "Image" -> ImageDetail()
                "TextField" -> TextFieldDetail()
                "PasswordField" -> PasswordFieldDetail()
                "Column" -> ColumnDetail()
                "Row" -> RowDetail()
                else -> Text("Không tìm thấy trang", modifier = Modifier.padding(24.dp))
            }
        }
    }
}

// -------------------- Component Demos --------------------

@Composable
fun TextDetail() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "The quick brown fox jumps over the lazy dog.",
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF2575FC),
            textDecoration = TextDecoration.Underline
        )
    }
}

@Composable
fun ImageDetail() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_launcher_foreground),
            contentDescription = "Sample Image",
            modifier = Modifier
                .size(150.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(Color(0xFFE3F2FD))
                .padding(16.dp)
        )
    }
}

@Composable
fun TextFieldDetail() {
    var text by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center
    ) {
        TextField(
            value = text,
            onValueChange = { text = it },
            label = { Text("Nhập văn bản") },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp)
        )
    }
}

@Composable
fun PasswordFieldDetail() {
    var password by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center
    ) {
        TextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Nhập mật khẩu") },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp)
        )
    }
}

@Composable
fun ColumnDetail() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        repeat(3) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(6.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFE3F2FD)),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text(
                    text = "Item ${it + 1}",
                    modifier = Modifier.padding(16.dp),
                    fontSize = 18.sp,
                    color = Color(0xFF1565C0)
                )
            }
        }
    }
}

@Composable
fun RowDetail() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(24.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        listOf("A", "B", "C").forEach {
            Box(
                modifier = Modifier
                    .size(60.dp)
                    .clip(CircleShape)
                    .background(Color(0xFFBBDEFB)),
                contentAlignment = Alignment.Center
            ) {
                Text(it, fontSize = 20.sp, fontWeight = FontWeight.Bold, color = Color(0xFF1565C0))
            }
        }
    }
}