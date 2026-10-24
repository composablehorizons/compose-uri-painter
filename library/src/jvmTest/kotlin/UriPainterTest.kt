@file:OptIn(androidx.compose.ui.test.ExperimentalTestApi::class)

package com.composables.uripainter

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Canvas
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.drawscope.CanvasDrawScope
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.toPixelMap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.test.captureToImage
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.v2.runComposeUiTest
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import com.sun.net.httpserver.HttpServer
import java.awt.image.BufferedImage
import java.io.ByteArrayOutputStream
import java.net.InetSocketAddress
import java.util.concurrent.CountDownLatch
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.time.Duration
import kotlin.time.Duration.Companion.milliseconds
import javax.imageio.ImageIO

class UriPainterTest {
    @Test
    fun loadedImageAndDimensionsChangeTogether() = assertLoadingPainterStaysTransparent()

    @Test
    fun crossfadeStillLoadsAndKeepsTheLoadingFrameTransparent() =
        assertLoadingPainterStaysTransparent(100.milliseconds)

    private fun assertLoadingPainterStaysTransparent(crossfade: Duration = Duration.ZERO) = runComposeUiTest {
        val requested = CountDownLatch(1)
        val release = CountDownLatch(1)
        val bitmap = BufferedImage(8, 12, BufferedImage.TYPE_INT_RGB)
        for (x in 0 until bitmap.width) for (y in 0 until bitmap.height) bitmap.setRGB(x, y, 0xFF0000)
        val bytes = ByteArrayOutputStream().use {
            ImageIO.write(bitmap, "png", it)
            it.toByteArray()
        }
        val server = HttpServer.create(InetSocketAddress("127.0.0.1", 0), 0)
        server.createContext("/page.png") { exchange ->
            requested.countDown()
            release.await()
            exchange.sendResponseHeaders(200, bytes.size.toLong())
            exchange.responseBody.use { it.write(bytes) }
        }
        server.start()
        var initialPainter: Painter? = null
        var currentPainter: Painter? = null
        try {
            setContent {
                val painter = rememberUriPainter("http://127.0.0.1:${server.address.port}/page.png", crossfade)
                SideEffect {
                    if (initialPainter == null) initialPainter = painter
                    currentPainter = painter
                }
                Image(painter, "Page", Modifier.size(80.dp), contentScale = ContentScale.Fit)
            }
            waitUntil("the image request has started", timeoutMillis = 5_000) { requested.count == 0L }
            release.countDown()
            waitUntil("the decoded image is visible", timeoutMillis = 5_000) {
                onNodeWithContentDescription("Page").captureToImage().toPixelMap()[40, 40] == Color.Red
            }
            runOnIdle {
                assertEquals(Size(8f, 12f), currentPainter!!.intrinsicSize)
                // A draw invalidation may precede layout. The old loading frame
                // must not draw the new bitmap using the previous dimensions.
                val canvasBitmap = ImageBitmap(80, 80)
                CanvasDrawScope().draw(Density(1f), LayoutDirection.Ltr, Canvas(canvasBitmap), Size(80f, 80f)) {
                    with(initialPainter!!) { draw(size) }
                }
                assertEquals(Color.Transparent, canvasBitmap.toPixelMap()[40, 40])
            }
        } finally {
            release.countDown()
            server.stop(0)
        }
    }
}
