package com.example.softcare.viewModel

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore

class AssessmentModel : ViewModel() {
    val responses = mutableStateListOf<Int?>(null, null, null, null, null)
    private val db = FirebaseFirestore.getInstance()

    fun saveResponse(index: Int, value: Int) {
        responses[index] = value
    }

    fun sendAssessmentToFirebase(onSuccess: () -> Unit, onFailure: (Exception) -> Unit) {
        val data = mapOf(
            "respostas" to responses.map { it ?: 0 },
            "timestamp" to System.currentTimeMillis()
        )

        db.collection("avaliacoes")
            .add(data)
            .addOnSuccessListener { onSuccess() }
            .addOnFailureListener { e -> onFailure(e) }
    }
}