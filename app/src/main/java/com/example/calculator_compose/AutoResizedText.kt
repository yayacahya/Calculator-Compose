package com.example.calculator_compose

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit

@Composable
fun AutoResizedText(
    text: String,
    textAlign: TextAlign = TextAlign.End,
    modifier: Modifier,
    fontWeight: FontWeight = FontWeight.Light,
    style: TextStyle = MaterialTheme.typography.h1,
    color: Color = Color.White,
    maxLines: Int = 1
) {
    var resizedTextStyle by remember {
        mutableStateOf(style)
    }
    var shouldDraw by remember {
        mutableStateOf(false)
    }

    val defaultFontSize = MaterialTheme.typography.h1.fontSize

    var startFontSizeChanged by remember {
        mutableStateOf(0)
    }

    val lengthAndFontSizeMap = remember { mutableStateMapOf<Int, TextUnit>() }

    Text(
        text = text,
        textAlign = textAlign,
        color = color,
        modifier = modifier.drawWithContent {
            if (shouldDraw) {
                drawContent()
            }
        },
        fontWeight = fontWeight,
        softWrap = false,
        style = resizedTextStyle,
        maxLines = maxLines,
        onTextLayout = { result ->
            if (result.didOverflowWidth) {
                lengthAndFontSizeMap.filterKeys { it == text.length }
                resizedTextStyle = resizedTextStyle.copy(
                    fontSize = resizedTextStyle.fontSize * 0.95
                )

                if (startFontSizeChanged == 0) {
                    startFontSizeChanged = text.length

                    lengthAndFontSizeMap[startFontSizeChanged] = resizedTextStyle.fontSize
                } else {
                    if (text.length >= startFontSizeChanged) {
                        lengthAndFontSizeMap[text.length] = resizedTextStyle.fontSize
                    }
                }
            } else {
                if (startFontSizeChanged > 0) {
                    resizedTextStyle = when (text.length) {
                        in 1 until startFontSizeChanged -> {
                            resizedTextStyle.copy(
                                fontSize = defaultFontSize
                            )
                        }
                        0 -> return@Text
                        else -> {
                            resizedTextStyle.copy(
                                fontSize = lengthAndFontSizeMap[text.length]
                                    ?: resizedTextStyle.fontSize
                            )
                        }
                    }
                }
                shouldDraw = true
            }
        }
    )
}