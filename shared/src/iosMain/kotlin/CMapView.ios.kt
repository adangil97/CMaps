import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.interop.UIKitView
import cocoapods.GoogleMaps.GMSMarker
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.copy

val mapDelegate = CMapDelegate()

@OptIn(ExperimentalForeignApi::class)
@Composable
actual fun CMapView() {
    val state by mapDelegate.state.collectAsState()
    val mapView = mapDelegate.getMapView()
    val markerState = remember {
        GMSMarker()
    }
    state.takeIf {
        it.first != 0.0 && it.second != 0.0
    }?.let {
        markerState.position = markerState.position().copy {
            latitude = it.first
            longitude = it.second
        }
        markerState.map = mapView
    }
    UIKitView(
        factory = {
            mapView
        },
        modifier = Modifier.fillMaxSize()
    )
}