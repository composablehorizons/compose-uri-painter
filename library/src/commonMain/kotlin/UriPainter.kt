package com.composables.uripainter

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.toIntSize
import com.github.panpf.sketch.AsyncImageState
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
    val loader = rememberAsyncImagePainter(
        request = ComposableImageRequest(uri) {
            if (crossfade > Duration.ZERO) {
                crossfade(crossfade.inWholeMilliseconds.toInt(), alwaysUse = true)
            }
        },
        contentScale = contentScale,
    )
    // Expose each resolved painter during composition so Image updates its
    // dimensions and drawing together, even if a draw precedes the next layout.
    return loader.state.painter ?: remember(loader) { LoadingPainter(loader.state) }
}

private class LoadingPainter(private val state: AsyncImageState) : Painter() {
    override val intrinsicSize: Size = Size.Unspecified

    override fun DrawScope.onDraw() {
        // The request needs draw bounds to start, but the loading frame must
        // remain transparent when the decoded image arrives before recomposition.
        if (state.size == null) state.setSizeWithLeast(size.toIntSize())
    }
}
