package com.github.chimney

import android.content.res.Resources
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp

sealed class VectorViewType(
    open val vectorResource: Int,
    var gradientColors: List<Color> = listOf(
        Color(0xFF5851D8),
        Color(0xFF833AB4),
        Color(0xFFC13584),
        Color(0xFFE1306C),
        Color(0xFFFD1D1D),
        Color(0xFFF56040),
        Color(0xFFF77737),
        Color(0xFFFCAF45),
        Color(0xFFFFDC80),
        Color(0xFF5851D8),
    ),
) {
    val gradientBrush: Brush by lazy {
        if (gradientColors.size > 1) {
            Brush.sweepGradient(gradientColors)
        } else {
            val colors = listOf(gradientColors.first(), Color.Black)
            Brush.sweepGradient(colors)
        }
    }

    data class PlainVector(
        override val vectorResource: Int = 0,
        val aspectRatio: Float = 1F,
        val backgroundColor: Color = Color.Black,
        val isGradient: Boolean = false,
    ) : VectorViewType(vectorResource)

    data class TextVector(
        override val vectorResource: Int = 0,
        val aspectRatio: Float = 1F,
        val text: String = Empty,
        val isGradient: Boolean = false,
        val backgroundColor: Color = Color.Black,
        val padding: Int = 0,
        val fontFamily: FontFamily = FontFamily.Default,
        val fontColor: Color = Color.Unspecified,
        val fontSize: TextUnit = TextUnit.Unspecified,
    ) : VectorViewType(vectorResource)

    data class ImageVector(
        override val vectorResource: Int = 0,
        val aspectRatio: Float = 1F,
        val imageSrc: Int = 0,
        val padding: Int = 0,
        val borderEnabled: Boolean = false,
        val borderColor: Color = Color.Unspecified,
        val gradientEnabled: Boolean = false,
        val borderStroke: Int = 2,
    ) : VectorViewType(vectorResource)
}

fun getVectorFromResource(
    res: Resources,
    vectorViewType: VectorViewType,
): VectorDrawable {
    return VectorDrawableParser.toVectorDrawable(
        res,
        vectorViewType.vectorResource,
    )
}

@Composable
fun VectorComposeView(vectorViewType: VectorViewType) {
    val res = LocalContext.current.resources
    val vectorDrawable = getVectorFromResource(res, vectorViewType)
    val vectorDrawableShape = DrawShape(vectorDrawable)
    when (vectorViewType) {
        is VectorViewType.ImageVector -> {
            with(vectorViewType) {
                val borderType = when(gradientEnabled){
                    true -> {
                        BorderStroke(
                            borderStroke.dp,
                            gradientBrush,
                        )
                    }
                    false -> {
                        BorderStroke(
                            borderStroke.dp,
                            borderColor,
                        )
                    }
                }


                val modifier = when (borderEnabled) {
                    true ->
                        Modifier
                            .aspectRatio(aspectRatio)
                            .padding(padding.dp)
                            .clip(
                                vectorDrawableShape,
                            ).border(
                                border = borderType,
                                shape = vectorDrawableShape,
                            )

                    false ->
                        Modifier
                            .aspectRatio(aspectRatio)
                            .padding(padding.dp)
                            .clip(
                                vectorDrawableShape,
                            )
                }
                Image(
                    modifier = modifier,
                    painter = painterResource(id = imageSrc),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                )
            }
        }

        is VectorViewType.PlainVector -> {
            with(vectorViewType) {
                val modifier = when (isGradient) {
                    true -> Modifier.background(brush = gradientBrush)
                    false -> Modifier.background(color = backgroundColor)
                }
                Box(
                    modifier = Modifier
                        .aspectRatio(aspectRatio)
                        .clip(
                            vectorDrawableShape,
                        )
                        .then(modifier),
                )
            }
        }

        is VectorViewType.TextVector -> {
            with(vectorViewType) {
                val modifier = when (isGradient) {
                    true -> Modifier.background(brush = gradientBrush)
                    false -> Modifier.background(color = backgroundColor)
                }
                Text(
                    text = text,
                    color = fontColor,
                    fontFamily = fontFamily,
                    fontSize = fontSize,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .aspectRatio(aspectRatio)
                        .padding(padding.dp)
                        .clip(
                            vectorDrawableShape,
                        )
                        .then(modifier)
                        .wrapContentHeight(),
                )
            }
        }
    }
}
