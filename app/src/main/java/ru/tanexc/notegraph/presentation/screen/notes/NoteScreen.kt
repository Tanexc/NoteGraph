package ru.tanexc.notegraph.presentation.screen.notes

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.outlined.Build
import androidx.compose.material.icons.outlined.Menu
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import dev.olshevski.navigation.reimagined.hilt.hiltViewModel
import ru.tanexc.notegraph.domain.model.Note
import ru.tanexc.notegraph.domain.model.ImagePiece
import ru.tanexc.notegraph.presentation.screen.notes.view_model.NoteViewModel
import ru.tanexc.notegraph.presentation.ui.theme.Typography
import ru.tanexc.notegraph.presentation.ui.widgets.NoteField

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteScreen(
    modifier: Modifier
) {

    val viewModel: NoteViewModel = hiltViewModel()

    var showBottomSheet: Boolean by remember { mutableStateOf(false) }

    Column(Modifier.fillMaxSize()) {
        CenterAlignedTopAppBar(
            navigationIcon = {
                IconButton(onClick = { /*TODO*/ }) {
                    Icon(Icons.Outlined.Menu, null)
                }
            },
            title = {},
            actions = {
                if (viewModel.note != null) {
                    IconButton(onClick = { showBottomSheet = !showBottomSheet }) {
                        Icon(
                            if (showBottomSheet) Icons.Filled.Build else Icons.Outlined.Build,
                            null
                        )
                    }
                }
            }
        )
    }

    AnimatedVisibility(
        visible = showBottomSheet && viewModel.focusedPiece != null,
        enter = slideInVertically { it },
        exit = slideOutVertically { it }
    ) {
        ModalBottomSheet(
            onDismissRequest = { showBottomSheet = !showBottomSheet }
        ) {

            Text(
                "Test",
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth(),
                style = Typography.labelLarge
            )
            
            Spacer(modifier = Modifier.size(48.dp))

            Row(Modifier.padding(16.dp)) {
                OutlinedTextField(
                    modifier = Modifier.weight(0.5f),
                    value = "",
                    onValueChange = {
                        if (it.toIntOrNull() != null) {
                            
                        }
                    }
                )
                Spacer(modifier = Modifier.size(16.dp))
                OutlinedTextField(
                    modifier = Modifier.weight(0.5f),
                    value = "",
                    onValueChange = {
                        if (it.toIntOrNull() != null) {

                        }
                    }
                )
            }

        }
    }
}