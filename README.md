# Compose Uri Painter

A Painter for Compose Multiplatform that loads images via a URI.

## Installation

```kotlin
repositories {
    mavenCentral()
}

dependencies {
    implementation("com.composables:compose-uri-painter:1.0.2")
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

As opposed to other image loading libraries, the Uri Painter focuses on loading your images, without handling any extra animation effects.

As a matter of fact, Uri Painter is a [simple wrapper of Coil](https://github.com/coil-kt/coil), pre-configured to work
on all Compose projects out of the box, without all the bells and whistles.

## Contributing

We accept bug reports and feature requests via GitHub issues.
