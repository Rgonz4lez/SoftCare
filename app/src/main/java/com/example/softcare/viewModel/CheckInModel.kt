import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class CheckInModel : ViewModel() {
    var selectedMood = mutableStateOf<String?>(null)

    fun hasCompletedCheckIn(): Boolean {
        return selectedMood.value != null
    }

    fun forceCheckInComplete() {
        selectedMood.value = "check-in pulado"
    }
}