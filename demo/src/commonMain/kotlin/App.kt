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
