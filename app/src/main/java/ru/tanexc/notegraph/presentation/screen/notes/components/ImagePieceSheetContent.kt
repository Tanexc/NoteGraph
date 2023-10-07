package ru.tanexc.notegraph.presentation.screen.notes.components


import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import ru.tanexc.notegraph.R
import ru.tanexc.notegraph.domain.model.note.ImagePiece


@Composable
fun ImagePieceSheetContent(
    piece: ImagePiece,
    onValueChanged: (ImagePiece) -> Unit
) {

    var labelText: String? by remember { mutableStateOf(piece.label) }
    var contentDescription: String? by remember { mutableStateOf(piece.contentDescription) }

    LazyColumn(
        Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth().padding(16.dp),
                value = labelText ?: "",
                onValueChange = {
                    if (it.length <= 64) {
                        labelText = it
                        onValueChanged(piece.copy(label = labelText))
                    }
                },
                placeholder = {
                    Text(stringResource(R.string.piece_label))
                },
                supportingText = {
                    labelText?.let {
                        Text("${it.length} / 64")
                    }
                }
            )
        }

        item {
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth().padding(16.dp),
                value = contentDescription ?: "",
                onValueChange = {
                    if (it.length <= 512) {
                        contentDescription = it
                        onValueChanged(piece.copy(contentDescription = contentDescription))
                    }
                },
                placeholder = {
                    Text(stringResource(R.string.piece_label))
                },
                supportingText = {
                    contentDescription?.let {
                        Text("${it.length} / 512")
                    }
                }
            )
            Spacer(modifier = Modifier.size(48.dp))
        }

    }

}