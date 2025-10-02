package com.syriantrivia.ui.difficulty

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.syriantrivia.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DifficultyScreen(
    moduleType: String,
    onDifficultySelected: (String) -> Unit,
    onBackClick: () -> Unit,
    viewModel: DifficultyViewModel = hiltViewModel()
) {
    val moduleTitle = when (moduleType) {
        "culture" -> stringResource(R.string.module_culture)
        "history" -> stringResource(R.string.module_history)
        else -> moduleType
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.difficulty_title)) },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.Default.ArrowBack, contentDescription = stringResource(R.string.back))
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = moduleTitle,
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.padding(bottom = 32.dp)
            )

            DifficultyButton(
                title = stringResource(R.string.difficulty_easy),
                description = stringResource(R.string.difficulty_easy_desc),
                onClick = { onDifficultySelected("easy") }
            )

            Spacer(modifier = Modifier.height(16.dp))

            DifficultyButton(
                title = stringResource(R.string.difficulty_moderate),
                description = stringResource(R.string.difficulty_moderate_desc),
                onClick = { onDifficultySelected("moderate") }
            )

            Spacer(modifier = Modifier.height(16.dp))

            DifficultyButton(
                title = stringResource(R.string.difficulty_hard),
                description = stringResource(R.string.difficulty_hard_desc),
                onClick = { onDifficultySelected("hard") }
            )
        }
    }
}

@Composable
fun DifficultyButton(
    title: String,
    description: String,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleLarge
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = description,
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}
