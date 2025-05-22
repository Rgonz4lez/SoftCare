import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class CheckInModel : ViewModel() {
    var selectedMood = mutableStateOf<String?>(null)

    fun hasCompletedCheckIn(): Boolean {
        return selectedMood.value != null
    }

    fun forceCheckInComplete() {
        selectedMood.value = "check-in pulado"
    }

    fun saveCheckIn(onSuccess: () -> Unit, onFailure: (Exception) -> Unit) {
        val mood = selectedMood.value ?: return
        val uid = FirebaseAuth.getInstance().currentUser?.uid ?: return

        val db = FirebaseFirestore.getInstance()
        val date = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())

        val data = hashMapOf(
            "data" to date,
            "humor" to mood
        )

        db.collection("usuarios")
            .document(uid)
            .collection("checkins")
            .add(data)
            .addOnSuccessListener { onSuccess() }
            .addOnFailureListener { onFailure(it) }
    }
}