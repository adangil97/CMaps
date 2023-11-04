import cocoapods.GoogleMaps.GMSCameraPosition
import cocoapods.GoogleMaps.GMSMapView
import cocoapods.GoogleMaps.GMSMapViewDelegateProtocol
import cocoapods.GoogleMaps.GMSServices
import kotlinx.cinterop.CValue
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.useContents
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import platform.CoreLocation.CLLocationCoordinate2D
import platform.darwin.NSObject

@OptIn(ExperimentalForeignApi::class)
class CMapDelegate : NSObject(), GMSMapViewDelegateProtocol {
    private val mapView: GMSMapView
    private val mutableState: MutableStateFlow<Pair<Double, Double>> = MutableStateFlow(0.0 to 0.0)
    val state: StateFlow<Pair<Double, Double>> = mutableState.asStateFlow()

    init {
        GMSServices.provideAPIKey("YOUR_API_KEY")
        GMSServices.setMetalRendererEnabled(true)
        mapView = GMSMapView().apply {
            val camera = GMSCameraPosition.cameraWithLatitude(1.35, 103.87, 10f)
            setCamera(camera)
        }
        mapView.delegate = this
    }

    override fun mapView(mapView: GMSMapView, didTapAtCoordinate: CValue<CLLocationCoordinate2D>) {
        didTapAtCoordinate.useContents {
            mutableState.value = this.latitude to this.longitude
        }
    }

    fun getMapView(): GMSMapView {
        return mapView
    }
}