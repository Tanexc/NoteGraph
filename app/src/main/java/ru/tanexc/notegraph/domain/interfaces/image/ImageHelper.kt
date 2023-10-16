package ru.tanexc.notegraph.domain.interfaces.image

import android.content.Context
import android.net.Uri
import androidx.compose.ui.graphics.ImageBitmap
import coil.request.ImageRequest

interface ImageHelper {
    suspend fun getImageByUri(uri: Uri?): ImageBitmap?
}