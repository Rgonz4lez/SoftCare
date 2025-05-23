package com.example.softcare.viewModel

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FieldValue
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
            "dataHora" to FieldValue.serverTimestamp()
        )

        db.collection("avaliacoes")
            .add(data)
            .addOnSuccessListener { onSuccess() }
            .addOnFailureListener { onFailure(it) }
    }

    fun fetchAssessments(
        onResult: (Map<Int, Int>) -> Unit,
        onError: (Exception) -> Unit
    ) {
        db.collection("avaliacoes")
            .get()
            .addOnSuccessListener { result ->
                val allResponses = result.documents.flatMap { doc ->
                    (doc.get("respostas") as? List<*>)?.mapNotNull { (it as? Number)?.toInt() } ?: emptyList()
                }
                val frequencies = allResponses.groupingBy { it }.eachCount()
                onResult(frequencies)
            }
            .addOnFailureListener { onError(it) }
    }
}