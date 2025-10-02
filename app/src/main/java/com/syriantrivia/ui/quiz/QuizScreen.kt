package com.syriantrivia.ui.quiz

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.syriantrivia.R
import com.syriantrivia.ui.theme.CorrectGreen
import com.syriantrivia.ui.theme.IncorrectRed

@Composable
fun QuizScreen(
    moduleType: String,
    difficulty: String,
    onQuizComplete: (score: Int, total: Int) -> Unit,
    viewModel: QuizViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.loadQuestions(moduleType, difficulty)
    }

    LaunchedEffect(uiState.isQuizComplete) {
        if (uiState.isQuizComplete) {
            onQuizComplete(uiState.score, uiState.totalQuestions)
        }
    }

    when {
        uiState.isLoading -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }
        uiState.error != null -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = stringResource(R.string.error_loading_questions),
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }
        else -> {
            QuizContent(
                uiState = uiState,
                questionText = viewModel.getQuestionText(),
                options = viewModel.getOptions(),
                correctAnswerText = viewModel.getCorrectAnswerText(),
                onAnswerSelected = { viewModel.selectAnswer(it) },
                onNextClick = { viewModel.nextQuestion() }
            )
        }
    }
}

@Composable
fun QuizContent(
    uiState: QuizUiState,
    questionText: String,
    options: List<String>,
    correctAnswerText: String,
    onAnswerSelected: (Int) -> Unit,
    onNextClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Progress indicator
        Text(
            text = stringResource(
                R.string.quiz_progress,
                uiState.currentQuestionIndex + 1,
                uiState.totalQuestions
            ),
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(vertical = 16.dp)
        )

        LinearProgressIndicator(
            progress = (uiState.currentQuestionIndex + 1).toFloat() / uiState.totalQuestions,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 24.dp)
        )

        // Question text
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 24.dp)
        ) {
            Text(
                text = questionText,
                style = MaterialTheme.typography.headlineSmall,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(24.dp)
            )
        }

        // Answer options
        options.forEachIndexed { index, option ->
            AnswerOption(
                text = option,
                isSelected = uiState.selectedAnswer == index,
                isCorrect = uiState.selectedAnswer != null && index == uiState.currentQuestion?.correctAnswer,
                isIncorrect = uiState.selectedAnswer == index && uiState.isAnswerCorrect == false,
                enabled = uiState.selectedAnswer == null,
                onClick = { onAnswerSelected(index) }
            )
            Spacer(modifier = Modifier.height(12.dp))
        }

        // Feedback and correct answer
        if (uiState.selectedAnswer != null) {
            Spacer(modifier = Modifier.height(16.dp))

            if (uiState.isAnswerCorrect == true) {
                Text(
                    text = stringResource(R.string.quiz_correct),
                    style = MaterialTheme.typography.titleMedium,
                    color = CorrectGreen
                )
            } else {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = stringResource(R.string.quiz_incorrect),
                        style = MaterialTheme.typography.titleMedium,
                        color = IncorrectRed
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = stringResource(R.string.quiz_correct_answer, correctAnswerText),
                        style = MaterialTheme.typography.bodyMedium,
                        textAlign = TextAlign.Center
                    )
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            Button(
                onClick = onNextClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
            ) {
                Text(stringResource(R.string.quiz_next))
            }
        }
    }
}

@Composable
fun AnswerOption(
    text: String,
    isSelected: Boolean,
    isCorrect: Boolean,
    isIncorrect: Boolean,
    enabled: Boolean,
    onClick: () -> Unit
) {
    val backgroundColor = when {
        isCorrect -> CorrectGreen
        isIncorrect -> IncorrectRed
        isSelected -> MaterialTheme.colorScheme.primaryContainer
        else -> MaterialTheme.colorScheme.surface
    }

    Card(
        onClick = onClick,
        enabled = enabled,
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = backgroundColor),
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(16.dp)
        )
    }
}
