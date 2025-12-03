package com.composeunstyled.demo

import App
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.window.ComposeViewport
import kotlinx.browser.document
import kotlinx.browser.window
import org.jetbrains.skiko.wasm.onWasmReady
import org.w3c.dom.HTMLStyleElement
import org.w3c.dom.url.URLSearchParams

@OptIn(ExperimentalWasmJsInterop::class, ExperimentalComposeUiApi::class)
fun main() {
    val iFrameParams = URLSearchParams(document.location?.search)
    val id = iFrameParams.get("id")
    onWasmReady {
        val composeTarget = document.getElementById("ComposeTarget") ?: error("No ComposeTarget")
        ComposeViewport(composeTarget) {
            App()
        }
    }
}
