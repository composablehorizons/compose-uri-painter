package com.composables.uripainter

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter
import coil3.compose.LocalPlatformContext
import coil3.compose.rememberAsyncImagePainter
import coil3.request.CachePolicy
import coil3.request.ImageRequest
import coil3.request.crossfade
import kotlin.time.Duration
import kotlin.time.Duration.Companion.milliseconds

typealias Uri = String

/**
 * Creates a [Painter] that can be used in composables such as [androidx.compose.foundation.Image] to load images via a Uri (such as the network).
 */
@Composable
fun rememberUriPainter(uri: Uri, crossfade: Duration = 0.milliseconds): Painter {
    val context = LocalPlatformContext.current

    return rememberAsyncImagePainter(
        ImageRequest.Builder(context)
            .data(uri)
            .crossfade(crossfade.inWholeMilliseconds.toInt())
            .build()
    )
}
