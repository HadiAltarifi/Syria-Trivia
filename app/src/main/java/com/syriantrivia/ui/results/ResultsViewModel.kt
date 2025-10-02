package com.syriantrivia.ui.results

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ResultsViewModel @Inject constructor() : ViewModel() {
    fun getEncouragementMessage(percentage: Int): String {
        return when {
            percentage >= 80 -> "results_excellent"
            percentage >= 60 -> "results_great"
            percentage >= 40 -> "results_good"
            else -> "results_keep_trying"
        }
    }
}
