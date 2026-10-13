# Compose Uri Painter

A Painter for Compose Multiplatform that loads images via a URI.

## Installation

```kotlin
repositories {
    mavenCentral()
}

dependencies {
    implementation("com.composables:compose-uri-painter:1.3.0")
}
```

## Basic Usage

```kotlin
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import com.composables.uripainter.rememberUriPainter

@Composable
fun App() {
    Image(
        painter = rememberUriPainter("https://images.unsplash.com/photo-1476673160081-cf065607f449?q=80&w=1920"),
        contentDescription = null,
        modifier = Modifier.fillMaxSize(),
        contentScale = ContentScale.Crop
    )
}
```

## Why use this

This is the simplest way to add image loading from a URI to your Jetpack Compose and Compose Multiplatform projects.

Uri Painter wraps [Sketch](https://github.com/panpf/sketch) to load still and animated GIF images on Android, iOS, desktop, and web. Pass `crossfade = 300.milliseconds` to fade an image in when it loads.

## Contributing

We accept bug reports and feature requests via GitHub issues.
