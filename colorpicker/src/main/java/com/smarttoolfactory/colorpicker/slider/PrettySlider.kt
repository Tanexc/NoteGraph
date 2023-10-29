package com.smarttoolfactory.colorpicker.slider

import androidx.annotation.FloatRange
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.requiredSizeIn
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.minimumInteractiveComponentSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.geometry.lerp
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PointMode
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.PointerInputChange
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import com.smarttoolfactory.colorpicker.ui.brush.sliderAlphaHSLGradient
import com.smarttoolfactory.colorpicker.ui.brush.sliderHueHSLGradient
import com.smarttoolfactory.colorpicker.widget.drawChecker
import com.smarttoolfactory.extendedcolors.util.roundToTwoDigits
import com.smarttoolfactory.slider.MaterialSliderColors
import com.smarttoolfactory.slider.MaterialSliderDefaults
import com.smarttoolfactory.slider.SliderBrushColor
import com.smarttoolfactory.slider.calculateFraction
import kotlin.math.abs

@Composable
fun PrettySliderHueHSL(
    modifier: Modifier = Modifier,
    @FloatRange(from = 0.0, to = 360.0) hue: Float,
    @FloatRange(from = 0.0, to = 1.0) saturation: Float,
    @FloatRange(from = 0.0, to = 1.0) lightness: Float,
    thumbColor: Color,
    trackHeight: Dp = 28.dp,
    borderColor: Color,
    borderStroke: BorderStroke? = null,
    onValueChange: (Float) -> Unit
) {

    val sliderHueSelectionHSLGradient = sliderHueHSLGradient(
        saturation = saturation,
        lightness = lightness,
    )

    PrettyCheckeredColorfulSlider(
        modifier = modifier,
        value = hue,
        valueRange = 0f..360f,
        onValueChange = onValueChange,
        brush = sliderHueSelectionHSLGradient,
        drawChecker = false,
        trackHeight = trackHeight,
        thumbColor = thumbColor,
        borderColor = borderColor,
        borderStroke = borderStroke
    )
}

@Composable
fun PrettySliderAlphaHSL(
    modifier: Modifier = Modifier,
    @FloatRange(from = 0.0, to = 360.0) hue: Float,
    @FloatRange(from = 0.0, to = 1.0) alpha: Float,
    trackHeight: Dp = 28.dp,
    onValueChange: (Float) -> Unit,
    thumbColor: Color,
    borderColor: Color,
    borderStroke: BorderStroke? = null
) {
    val sliderAlphaHSLGradient = sliderAlphaHSLGradient(hue = hue)
    PrettyCheckeredColorfulSlider(
        modifier = modifier,
        trackHeight = trackHeight,
        value = animateFloatAsState(targetValue = alpha, label = "").value,
        onValueChange = onValueChange,
        brush = sliderAlphaHSLGradient,
        drawChecker = false,
        thumbColor = thumbColor,
        borderColor = borderColor,
        borderStroke = borderStroke
    )
}


@Composable
fun PrettyCheckeredColorfulSlider(
    modifier: Modifier = Modifier,
    value: Float,
    valueRange: ClosedFloatingPointRange<Float> = 0f..1f,
    onValueChange: (Float) -> Unit,
    brush: Brush,
    drawChecker: Boolean = false,
    trackHeight: Dp = 12.dp,
    thumbColor: Color,
    borderColor: Color,
    borderStroke: BorderStroke?
) {
    BoxWithConstraints(
        modifier = modifier,
        contentAlignment = Alignment.CenterStart
    ) {

        if (drawChecker) {
            Box(
                modifier = Modifier
                    .width(maxWidth)
                    .height(trackHeight - 1.dp)
                    .drawChecker(shape = CircleShape)
            )
        }

        PrettyColorfulSlider(
            value = value,
            modifier = Modifier,
            thumbRadius = 12.dp,
            trackHeight = trackHeight,
            onValueChange = { value ->
                onValueChange(value.roundToTwoDigits())
            },
            valueRange = valueRange,
            coerceThumbInTrack = true,
            colors = MaterialSliderDefaults.materialColors(
                activeTrackColor = SliderBrushColor(brush = brush),
                inactiveTrackColor = SliderBrushColor(color = Color.Transparent),
                thumbColor = SliderBrushColor(MaterialTheme.colorScheme.inverseSurface)
            ),
            drawInactiveTrack = false,
            thumbColor = thumbColor,
            borderColor = borderColor,
            borderStroke = borderStroke
        )
    }
}

@Composable
fun PrettyColorfulSlider(
    value: Float,
    onValueChange: (Float) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    valueRange: ClosedFloatingPointRange<Float> = 0f..1f,
    steps: Int = 0,
    onValueChangeFinished: (() -> Unit)? = null,
    trackHeight: Dp = TrackHeight,
    thumbRadius: Dp = ThumbRadius,
    colors: MaterialSliderColors = MaterialSliderDefaults.defaultColors(),
    borderStroke: BorderStroke? = null,
    drawInactiveTrack: Boolean = true,
    coerceThumbInTrack: Boolean = false,
    thumbColor: Color,
    borderColor: Color
) {
    ColorfulSlider(
        modifier = modifier,
        value = value,
        onValueChange = { progress, _ ->
            onValueChange(progress)
        },
        enabled = enabled,
        valueRange = valueRange,
        steps = steps,
        onValueChangeFinished = onValueChangeFinished,
        trackHeight = trackHeight,
        thumbRadius = thumbRadius,
        colors = colors,
        borderStroke = borderStroke,
        drawInactiveTrack = drawInactiveTrack,
        coerceThumbInTrack = coerceThumbInTrack,
        thumbColor = thumbColor,
        borderColor = borderColor
    )
}


@Composable
fun ColorfulSlider(
    value: Float,
    onValueChange: (Float, Offset) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    valueRange: ClosedFloatingPointRange<Float> = 0f..1f,
    steps: Int = 0,
    onValueChangeFinished: (() -> Unit)? = null,
    trackHeight: Dp = TrackHeight,
    thumbRadius: Dp = ThumbRadius,
    colors: MaterialSliderColors = MaterialSliderDefaults.defaultColors(),
    borderStroke: BorderStroke? = null,
    drawInactiveTrack: Boolean = true,
    coerceThumbInTrack: Boolean = false,
    thumbColor: Color,
    borderColor: Color
) {

    require(steps >= 0) { "steps should be >= 0" }
    val onValueChangeState = rememberUpdatedState(onValueChange)
    val tickFractions = remember(steps) {
        stepsToTickFractions(steps)
    }
    BoxWithConstraints(
        modifier = modifier
            .minimumInteractiveComponentSize()
            .requiredSizeIn(
                minWidth = ThumbRadius * 2,
                minHeight = ThumbRadius * 2
            ),
        contentAlignment = Alignment.CenterStart
    ) {

        val isRtl = LocalLayoutDirection.current == LayoutDirection.Rtl

        val width = constraints.maxWidth.toFloat()
        val thumbRadiusInPx: Float

        // Start of the track used for measuring progress,
        // it's line + radius of cap which is half of height of track
        // to draw this on canvas starting point of line
        // should be at trackStart + trackHeightInPx / 2 while drawing
        val trackStart: Float
        // End of the track that is used for measuring progress
        val trackEnd: Float
        val strokeRadius: Float
        with(LocalDensity.current) {
            thumbRadiusInPx = thumbRadius.toPx()
            strokeRadius = trackHeight.toPx() / 2
            trackStart = thumbRadiusInPx.coerceAtLeast(strokeRadius)
            trackEnd = width - trackStart
        }

        // Sales and interpolates from offset from dragging to user value in valueRange
        fun scaleToUserValue(offset: Float) =
            scale(trackStart, trackEnd, offset, valueRange.start, valueRange.endInclusive)

        // Scales user value using valueRange to position on x axis on screen
        fun scaleToOffset(userValue: Float) =
            scale(valueRange.start, valueRange.endInclusive, userValue, trackStart, trackEnd)

        val rawOffset = remember { mutableStateOf(scaleToOffset(value)) }

        CorrectValueSideEffect(
            ::scaleToOffset,
            valueRange,
            trackStart..trackEnd,
            rawOffset,
            value
        )

        val coerced = value.coerceIn(valueRange.start, valueRange.endInclusive)
        val fraction = calculateFraction(valueRange.start, valueRange.endInclusive, coerced)

        val dragModifier = Modifier
            .pointerInput(Unit) {
                detectDragGestures(
                    onDrag = { change: PointerInputChange, _: Offset ->
                        if (enabled) {
                            rawOffset.value =
                                if (!isRtl) change.position.x else trackEnd - change.position.x
                            val offsetInTrack = rawOffset.value.coerceIn(trackStart, trackEnd)
                            onValueChangeState.value.invoke(
                                scaleToUserValue(offsetInTrack),
                                Offset(rawOffset.value.coerceIn(trackStart, trackEnd), strokeRadius)
                            )
                        }

                    },
                    onDragEnd = {
                        if (enabled) {
                            onValueChangeFinished?.invoke()
                        }
                    }
                )
            }
            .pointerInput(Unit) {
                detectTapGestures { position: Offset ->
                    if (enabled) {
                        rawOffset.value =
                            if (!isRtl) position.x else trackEnd - position.x
                        val offsetInTrack = rawOffset.value.coerceIn(trackStart, trackEnd)
                        onValueChangeState.value.invoke(
                            scaleToUserValue(offsetInTrack),
                            Offset(rawOffset.value.coerceIn(trackStart, trackEnd), strokeRadius)
                        )
                        onValueChangeFinished?.invoke()
                    }
                }
            }

        SliderImpl(
            enabled = enabled,
            fraction = fraction,
            trackStart = trackStart,
            trackEnd = trackEnd,
            tickFractions = tickFractions,
            colors = colors,
            trackHeight = trackHeight,
            thumbRadius = thumbRadiusInPx,
            coerceThumbInTrack = coerceThumbInTrack,
            drawInactiveTrack = drawInactiveTrack,
            borderStroke = borderStroke,
            modifier = dragModifier,
            borderColor = borderColor,
            thumbColor = thumbColor
        )
    }
}

@Composable
private fun SliderImpl(
    enabled: Boolean,
    fraction: Float,
    trackStart: Float,
    trackEnd: Float,
    tickFractions: List<Float>,
    colors: MaterialSliderColors,
    trackHeight: Dp,
    thumbRadius: Float,
    coerceThumbInTrack: Boolean,
    drawInactiveTrack: Boolean,
    borderStroke: BorderStroke? = null,
    modifier: Modifier,
    thumbColor: Color,
    borderColor: Color
) {

    val trackStrokeWidth: Float
    val thumbSize: Dp

    var borderWidth = 0f
    val borderBrush: Brush? = borderStroke?.brush

    with(LocalDensity.current) {
        trackStrokeWidth = trackHeight.toPx()
        thumbSize = (2 * thumbRadius).toDp()

        if (borderStroke != null) {
            borderWidth = borderStroke.width.toPx()
        }
    }

    Box(
        // Constraint max height of Slider to max of thumb or track or minimum touch 48.dp
        modifier
            .heightIn(
                max = trackHeight
                    .coerceAtLeast(thumbSize)
                    .coerceAtLeast(TrackHeight)
            )
    ) {

        // Position that corresponds to center of this slider's thumb
        val thumbCenterPos = (trackStart + (trackEnd - trackStart) * fraction)

        Track(
            modifier = Modifier.fillMaxSize(),
            fraction = fraction,
            tickFractions = tickFractions,
            thumbRadius = thumbRadius,
            trackStart = trackStart,
            trackHeight = trackStrokeWidth,
            coerceThumbInTrack = coerceThumbInTrack,
            colors = colors,
            enabled = enabled,
            borderBrush = borderBrush,
            borderWidth = borderWidth,
            drawInactiveTrack = drawInactiveTrack
        )

        Thumb(
            modifier = Modifier.align(Alignment.CenterStart),
            offset = thumbCenterPos - thumbRadius,
            thumbSize = thumbSize,
            colors = colors,
            enabled = enabled,
            borderColor = borderColor,
            thumbColor = thumbColor
        )
    }
}

/**
 * Draws active and if [drawInactiveTrack] is set to true inactive tracks on Canvas.
 * If inactive track is to be drawn it's drawn between start and end of canvas. Active track
 * is drawn between start and current value.
 *
 * Drawing both tracks use [SliderBrushColor] to draw a nullable [Brush] first. If it's not then
 * [SliderBrushColor.solidColor] is used to draw with solid colors provided by [MaterialSliderColors]
 */
@Composable
private fun Track(
    modifier: Modifier,
    fraction: Float,
    tickFractions: List<Float>,
    thumbRadius: Float,
    trackStart: Float,
    trackHeight: Float,
    coerceThumbInTrack: Boolean,
    colors: MaterialSliderColors,
    enabled: Boolean,
    borderBrush: Brush?,
    borderWidth: Float,
    drawInactiveTrack: Boolean,
) {

    val debug = false

    // Colors for drawing track and/or ticks
    val activeTrackColor: Brush =
        colors.trackColor(enabled = enabled, active = true).value
    val inactiveTrackColor: Brush =
        colors.trackColor(enabled = enabled, active = false).value
    val inactiveTickColor = colors.tickColor(enabled, active = false).value
    val activeTickColor = colors.tickColor(enabled, active = true).value

    // stroke radius is used for drawing length it adds this radius to both sides of the line
    val strokeRadius = trackHeight / 2

    // Start of drawing in Canvas
    // when not coerced set start of drawing line at trackStart + strokeRadius
    // to limit drawing start edge at track start end edge at track end

    // When coerced move edges of drawing by thumb radius to cover thumb edges in drawing
    // it needs to move to right as stroke radius minus thumb radius to match track start
    val drawStart =
        if (coerceThumbInTrack) trackStart - thumbRadius + strokeRadius else trackStart

    Canvas(modifier = modifier) {
        val width = size.width
        val height = size.height
        val isRtl = layoutDirection == LayoutDirection.Rtl

        val centerY = center.y

        // left side of the slider that is drawn on canvas, left tip of stroke radius on left side
        val sliderLeft = Offset(drawStart, centerY)
        // right side of the slider that is drawn on canvas, right tip of stroke radius on left side
        val sliderRight = Offset((width - drawStart).coerceAtLeast(drawStart), centerY)

        val sliderStart = if (isRtl) sliderRight else sliderLeft
        val sliderEnd = if (isRtl) sliderLeft else sliderRight

        val sliderValue = Offset(
            sliderStart.x + (sliderEnd.x - sliderStart.x) * fraction,
            center.y
        )

        // InActive Track
        drawLine(
            brush = inactiveTrackColor,
            start = sliderStart,
            end = sliderEnd,
            strokeWidth = trackHeight,
            cap = StrokeCap.Round
        )

        // Active Track
        drawLine(
            brush = activeTrackColor,
            start = sliderStart,
            end = if (drawInactiveTrack) sliderValue else sliderEnd,
            strokeWidth = trackHeight,
            cap = StrokeCap.Round
        )

        if (debug) {
            drawLine(
                color = Color.Yellow,
                start = sliderStart,
                end = sliderEnd,
                strokeWidth = strokeRadius / 4
            )
        }

        borderBrush?.let { brush ->
            drawRoundRect(
                brush = brush,
                topLeft = Offset(sliderStart.x - strokeRadius - borderWidth, (height - trackHeight) / 2),
                size = Size(width = sliderEnd.x - sliderStart.x + trackHeight + borderWidth * 2, trackHeight),
                cornerRadius = CornerRadius(strokeRadius, strokeRadius),
                style = Stroke(width = borderWidth)
            )
        }

        if (drawInactiveTrack) {
            tickFractions.groupBy { it > fraction }
                .forEach { (outsideFraction, list) ->
                    drawPoints(
                        points = list.map {
                            Offset(lerp(sliderStart, sliderEnd, it).x, center.y)
                        },
                        pointMode = PointMode.Points,
                        brush = if (outsideFraction) inactiveTickColor
                        else activeTickColor,
                        strokeWidth = strokeRadius.coerceAtMost(thumbRadius / 2),
                        cap = StrokeCap.Round
                    )
                }
        }
    }
}

@Composable
private fun Thumb(
    modifier: Modifier,
    offset: Float,
    thumbSize: Dp,
    colors: MaterialSliderColors,
    enabled: Boolean,
    borderColor: Color,
    thumbColor: Color
) {
    Box(
        modifier = modifier
            .offset { IntOffset(offset.toInt(), 0) }
            .size(thumbSize)
            .background(thumbColor, CircleShape)
            .border(2.5.dp, thumbColor, CircleShape)
    ) {
        Box(
            modifier = modifier
                .fillMaxSize()
                .background(thumbColor, CircleShape)
                .border(5.dp, borderColor, CircleShape)
        )
    }
}

@Composable
internal fun CorrectValueSideEffect(
    scaleToOffset: (Float) -> Float,
    valueRange: ClosedFloatingPointRange<Float>,
    trackRange: ClosedFloatingPointRange<Float>,
    valueState: MutableState<Float>,
    value: Float
) {
    SideEffect {
        val error = (valueRange.endInclusive - valueRange.start) / 1000
        val newOffset = scaleToOffset(value)
        if (abs(newOffset - valueState.value) > error) {
            if (valueState.value in trackRange) {
                valueState.value = newOffset
            }
        }
    }
}

internal fun stepsToTickFractions(steps: Int): List<Float> {
    return if (steps == 0) emptyList() else List(steps + 2) { it.toFloat() / (steps + 1) }
}

internal fun scale(start1: Float, end1: Float, pos: Float, start2: Float, end2: Float) =
    lerp(start2, end2, calculateFraction(start1, end1, pos))

internal fun lerp(start: Float, end: Float, amount: Float): Float {
    return (1 - amount) * start + amount * end
}

internal val ThumbRadius = 10.dp
internal val TrackHeight = 4.dp
internal val SliderHeight = 48.dp