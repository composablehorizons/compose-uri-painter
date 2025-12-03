package com.composables.uripainter

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter
import coil3.compose.LocalPlatformContext
import coil3.compose.rememberAsyncImagePainter
import coil3.request.CachePolicy
import coil3.request.ImageRequest

typealias Uri = String

/**
 * Creates a [Painter] that can be used in composables such as [androidx.compose.foundation.Image] to load images via a Uri (such as the network).
 */
@Composable
fun rememberUriPainter(uri: Uri): Painter {
    val context = LocalPlatformContext.current

    return rememberAsyncImagePainter(
        ImageRequest.Builder(context)
            .data(uri)
            .build()
    )
}
