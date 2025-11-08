package com.example.truycap.ui.permission


import android.Manifest
import android.os.Build
import androidx.lifecycle.ViewModel

class PermissionViewModel : ViewModel() {

    // Trả về danh sách permission tương ứng với bước
    fun getPermissionForStep(step: Int): Array<String> {
        return when (step) {
            0 -> arrayOf(Manifest.permission.ACCESS_FINE_LOCATION)
            1 -> {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU)
                    arrayOf(Manifest.permission.POST_NOTIFICATIONS)
                else
                    emptyArray()
            }
            2 -> arrayOf(Manifest.permission.CAMERA)
            else -> emptyArray()
        }
    }
}