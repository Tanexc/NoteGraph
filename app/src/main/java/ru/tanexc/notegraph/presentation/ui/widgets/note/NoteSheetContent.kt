package ru.tanexc.notegraph.presentation.ui.widgets.note

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import ru.tanexc.notegraph.R
import ru.tanexc.notegraph.domain.model.note.Note

@Composable
fun ColumnScope.NoteSheetContent(
    note: Note,
    onValueChanged: (Note) -> Unit
) {
    var labelText: String by remember { mutableStateOf(note.label) }

    LazyColumn(Modifier.fillMaxSize(), horizontalAlignment = CenterHorizontally) {
        item {
            OutlinedTextField(
                modifier = Modifier
                    .align(CenterHorizontally)
                    .fillMaxWidth(0.8f),
                value = labelText,
                onValueChange = {
                    if (it.length <= 64) {
                        labelText = it
                        onValueChanged(note.copy(label = labelText))
                    }
                },
                supportingText = {
                    Text("${labelText.length} / 64")
                },
                label = { Text(stringResource(R.string.note_label)) },
            )
            Spacer(modifier = Modifier.size(48.dp))
        }
    }
}