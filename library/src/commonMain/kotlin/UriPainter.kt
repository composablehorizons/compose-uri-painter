package com.composables.uripainter

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import com.github.panpf.sketch.rememberAsyncImagePainter
import com.github.panpf.sketch.request.ComposableImageRequest
import kotlin.time.Duration
import kotlin.time.Duration.Companion.milliseconds

typealias Uri = String

/**
 * Creates a [Painter] that can be used in composables such as [androidx.compose.foundation.Image] to load images via a Uri (such as the network).
 */
@Composable
fun rememberUriPainter(
    uri: Uri,
    crossfade: Duration = 0.milliseconds,
    contentScale: ContentScale = ContentScale.Fit,
): Painter {
    return rememberAsyncImagePainter(
        request = ComposableImageRequest(uri) {
            if (crossfade > Duration.ZERO) {
                crossfade(crossfade.inWholeMilliseconds.toInt(), alwaysUse = true)
            }
        },
        contentScale = contentScale,
    )
}
