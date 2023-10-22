package ru.tanexc.notegraph.presentation.screen.note_field.components

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DisplayMode
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TimePicker
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberTimePickerState
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ColumnScope.NoteSheetContent(
    note: Note,
    onValueChanged: (Note) -> Unit
) {

    val timePickerState = rememberTimePickerState()
    val dataPickerState = rememberDatePickerState(initialDisplayMode = DisplayMode.Picker)
    var endToEnabled: Boolean by remember { mutableStateOf(false) }
    var labelText: String by remember { mutableStateOf(note.label) }

    LazyColumn(Modifier.fillMaxSize(), horizontalAlignment = CenterHorizontally) {
        item {
            OutlinedTextField(
                modifier = Modifier.align(CenterHorizontally),
                value = labelText,
                onValueChange = {
                    if (it.length <= 64) {
                        labelText = it
                        onValueChanged(note.copy(label = labelText))
                    }
                },
                supportingText = {
                    Text("${labelText.length} / 64")
                }
            )
            Spacer(modifier = Modifier.size(48.dp))
        }
        item {
            Row(Modifier.fillMaxSize()) {
                Checkbox(checked = endToEnabled, onCheckedChange = { endToEnabled = !endToEnabled })
                Text(stringResource(R.string.notify))
            }

            if (endToEnabled) {
                TimePicker(state = timePickerState, Modifier.align(CenterHorizontally))
                DatePicker(state = dataPickerState, Modifier.align(CenterHorizontally))
            }

        }

    }


}