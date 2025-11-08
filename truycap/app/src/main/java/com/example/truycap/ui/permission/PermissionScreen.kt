package com.example.truycap.ui.permission
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import com.example.truycap.R

@Composable
fun PermissionScreen(
    onFinish: () -> Unit,
    viewModel: PermissionViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
) {
    var step by remember { mutableStateOf(0) }
    val context = LocalContext.current

    val pages = listOf(
        PermissionPageData(
            title = "Location",
            description = "Allow maps to access your location while you use the app?",
            iconRes = R.drawable.ic_location,
            allowText = "Allow"
        ),
        PermissionPageData(
            title = "Notification",
            description = "Please enable notifications to receive updates and reminders",
            iconRes = R.drawable.ic_notification,
            allowText = "Turn on"
        ),
        PermissionPageData(
            title = "Camera",
            description = "We need access to your camera to scan QR codes",
            iconRes = R.drawable.ic_camera,
            allowText = "Turn on"
        )
    )

    // Launcher xin quyền
    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        // Kiểm tra tất cả có được chấp thuận không
        val granted = permissions.values.all { it }
        if (granted) {
            if (step < pages.lastIndex) step++ else onFinish()
        } else {
            // Nếu từ chối vẫn chuyển bước (hoặc có thể hiển thị warning)
            if (step < pages.lastIndex) step++ else onFinish()
        }
    }

    val current = pages[step]

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        contentAlignment = Alignment.Center
    ) {
        PermissionCard(
            data = current,
            onAllow = {
                val permissions = viewModel.getPermissionForStep(step)
                if (permissions.isEmpty()) {
                    if (step < pages.lastIndex) step++ else onFinish()
                } else {
                    // Kiểm tra nếu đã có quyền
                    val allGranted = permissions.all {
                        ContextCompat.checkSelfPermission(context, it) == PackageManager.PERMISSION_GRANTED
                    }
                    if (allGranted) {
                        if (step < pages.lastIndex) step++ else onFinish()
                    } else {
                        permissionLauncher.launch(permissions)
                    }
                }
            },
            onSkip = {
                if (step < pages.lastIndex) step++ else onFinish()
            }
        )
    }
}