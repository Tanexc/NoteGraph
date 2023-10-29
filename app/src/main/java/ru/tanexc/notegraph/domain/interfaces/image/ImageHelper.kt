package ru.tanexc.notegraph.domain.interfaces.image

import android.net.Uri
import androidx.compose.ui.graphics.ImageBitmap

interface ImageHelper {
    suspend fun getImageByUri(uri: Uri?): ImageBitmap?
}