package com.syriantrivia.ui.quiz

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.syriantrivia.data.model.Difficulty
import com.syriantrivia.data.model.ModuleType
import com.syriantrivia.data.model.Question
import com.syriantrivia.data.repository.QuestionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

data class QuizUiState(
    val currentQuestion: Question? = null,
    val currentQuestionIndex: Int = 0,
    val totalQuestions: Int = 0,
    val selectedAnswer: Int? = null,
    val isAnswerCorrect: Boolean? = null,
    val score: Int = 0,
    val isLoading: Boolean = true,
    val error: String? = null,
    val isQuizComplete: Boolean = false
)

@HiltViewModel
class QuizViewModel @Inject constructor(
    private val questionRepository: QuestionRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(QuizUiState())
    val uiState: StateFlow<QuizUiState> = _uiState.asStateFlow()

    private var questions: List<Question> = emptyList()
    private val locale = Locale.getDefault()
    private val isArabic = locale.language == "ar"

    fun loadQuestions(moduleType: String, difficulty: String) {
        viewModelScope.launch {
            try {
                _uiState.value = _uiState.value.copy(isLoading = true)

                val allQuestions = questionRepository.getQuestionsByModuleAndDifficulty(
                    ModuleType.fromId(moduleType),
                    Difficulty.fromId(difficulty)
                )

                if (allQuestions.isEmpty()) {
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        error = "No questions available"
                    )
                    return@launch
                }

                // show all questions in random order
                questions = allQuestions.shuffled()

                _uiState.value = _uiState.value.copy(
                    currentQuestion = questions.firstOrNull(),
                    currentQuestionIndex = 0,
                    totalQuestions = questions.size,
                    isLoading = false
                )
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    error = e.message
                )
            }
        }
    }

    fun selectAnswer(answerIndex: Int) {
        val currentState = _uiState.value
        val currentQuestion = currentState.currentQuestion ?: return

        val isCorrect = answerIndex == currentQuestion.correctAnswer
        val newScore = if (isCorrect) currentState.score + 1 else currentState.score

        _uiState.value = currentState.copy(
            selectedAnswer = answerIndex,
            isAnswerCorrect = isCorrect,
            score = newScore
        )
    }

    fun nextQuestion() {
        val currentState = _uiState.value
        val nextIndex = currentState.currentQuestionIndex + 1

        if (nextIndex >= currentState.totalQuestions) {
            _uiState.value = currentState.copy(isQuizComplete = true)
            return
        }

        _uiState.value = currentState.copy(
            currentQuestion = questions.getOrNull(nextIndex),
            currentQuestionIndex = nextIndex,
            selectedAnswer = null,
            isAnswerCorrect = null
        )
    }

    fun getQuestionText(): String {
        return _uiState.value.currentQuestion?.getQuestion(isArabic) ?: ""
    }

    fun getOptions(): List<String> {
        return _uiState.value.currentQuestion?.getOptions(isArabic) ?: emptyList()
    }

    fun getCorrectAnswerText(): String {
        val question = _uiState.value.currentQuestion ?: return ""
        val options = getOptions()
        return options.getOrNull(question.correctAnswer) ?: ""
    }
}
