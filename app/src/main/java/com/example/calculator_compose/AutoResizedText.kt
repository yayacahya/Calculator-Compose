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

//    val defaultFontSize = MaterialTheme.typography.h1.fontSize

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
//                if (style.fontSize.isUnspecified) {
//                    resizedTextStyle = resizedTextStyle.copy(
//                        fontSize = defaultFontSize
//                    )
//                    Log.i("defaultFontSize xxx", "$defaultFontSize")
//                }
                resizedTextStyle = resizedTextStyle.copy(
                    fontSize = resizedTextStyle.fontSize * 0.95
                )
            } else {
                shouldDraw = true
            }
        }
    )
}