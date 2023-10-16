package ru.tanexc.notegraph.presentation.util.image_loader

import android.content.Context
import android.net.Uri
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.core.graphics.drawable.toBitmapOrNull
import coil.ImageLoader
import coil.request.ImageRequest
import ru.tanexc.notegraph.domain.interfaces.image.ImageHelper

class ImageLoader(private val context: Context): ImageHelper {

    private val loader = ImageLoader(context).newBuilder().build()

    override suspend fun getImageByUri(uri: Uri?): ImageBitmap? {
        val imageRequest = ImageRequest.Builder(context).data(uri).build()
        return loader.execute(imageRequest).drawable?.toBitmapOrNull()?.asImageBitmap()
    }
}