package com.smarttoolfactory.colorpicker.widget

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.smarttoolfactory.colorpicker.selector.SelectorRectSaturationLightnessHSL
import com.smarttoolfactory.colorpicker.slider.SliderAlphaHSL
import com.smarttoolfactory.colorpicker.slider.SliderHueHSL
import com.smarttoolfactory.extendedcolors.util.ColorUtil

@OptIn(ExperimentalStdlibApi::class)
@Composable
fun PrettyColorPicker(
    modifier: Modifier = Modifier,
    initialColor: Color = Color.Black,
    colorScheme: ColorScheme,
    onValueChanged: (Color) -> Unit
    ) {
    val hslArray = ColorUtil.colorToHSL(initialColor)

    var hue by remember { mutableStateOf(hslArray[0]) }
    var saturation by remember { mutableStateOf(hslArray[1]) }
    var lightness by remember { mutableStateOf(hslArray[2]) }
    var alpha by remember { mutableStateOf(initialColor.alpha) }

    var currentColor = Color.hsl(hue = hue, saturation = saturation, lightness = lightness, alpha = alpha)
    
    LaunchedEffect(hue, saturation, lightness, alpha) {
        currentColor = Color.hsl(hue = hue, saturation = saturation, lightness = lightness, alpha = alpha)
        onValueChanged(currentColor)
    }

    Box(modifier = modifier) {
        Column(
            Modifier
                .padding(22.dp)
                .fillMaxWidth()
        ) {
            Row(
                Modifier
                    .fillMaxWidth()
            ) {
                Box(
                    Modifier
                        .size(56.dp)
                        .background(currentColor, RoundedCornerShape(8.dp))
                        .padding(8.dp)
                )

                Spacer(modifier = Modifier.size(8.dp))

                Box(modifier = Modifier
                    .weight(1f)
                    .height(56.dp)
                    .border(1.dp, colorScheme.outline, RoundedCornerShape(8.dp))) {
                    Text(
                        text = "#${currentColor.toArgb().toHexString()}",
                        modifier = Modifier.align(Alignment.Center),
                        textAlign = TextAlign.Center,
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

            }
            
            Spacer(modifier = Modifier.size(8.dp))

            SelectorRectSaturationLightnessHSL(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(8.dp))
                    .aspectRatio(4 / 3f),
                hue = hue,
                saturation = saturation,
                lightness = lightness,
                selectionRadius = 8.dp
            ) { s, l ->
                saturation = s
                lightness = l
            }

            SliderHueHSL(
                hue = hue,
                saturation = saturation,
                lightness = lightness,
                onValueChange = { value ->
                    hue = value
                }
            )

            SliderAlphaHSL(
                hue = hue,
                alpha = alpha,
                onValueChange = {value ->
                    alpha = value
                }
            )
        }

    }
}