package com.github.chimney

import android.annotation.SuppressLint
import android.content.res.Resources
import android.graphics.Matrix
import android.graphics.RectF
import androidx.annotation.DrawableRes
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.asComposePath
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection
import androidx.core.graphics.PathParser
import org.xmlpull.v1.XmlPullParser

const val ZERO = 0.0f
const val ONE = 1f
const val Empty = ""

data class VectorDrawable(
    val width: Float = ZERO,
    val height: Float = ZERO,
    val viewportWidth: Float = ZERO,
    val viewportHeight: Float = ZERO,
    val pathData: String = Empty,
) {
    val aspectRatio: Float by lazy {
        if (viewportHeight == ZERO || viewportWidth == ZERO) ONE else viewportWidth / viewportHeight
    }
}

fun String?.toFloatOrZero(): Float {
    return if (this.isNullOrBlank()) {
        ZERO
    } else {
        this.toFloat()
    }
}

object VectorDrawableParser {

    private val digitsOnly = Regex("[^0-9.]")

    @SuppressLint("ResourceType")
    fun toVectorDrawable(
        resources: Resources,
        @DrawableRes drawable: Int,
    ): VectorDrawable {
        var vectorDrawable = VectorDrawable()
        // This is very simple parser, it doesn't support <group> tag, nested tags and other stuff
        resources.getXml(drawable).use { xml ->
            var event = xml.eventType
            while (event != XmlPullParser.END_DOCUMENT) {
                if (event != XmlPullParser.START_TAG) {
                    event = xml.next()
                    continue
                }

                when (xml.name) {
                    "vector" -> {
                        vectorDrawable = vectorDrawable.copy(
                            width = xml.getAttributeValue(getAttrPosition(xml, "width"))
                                .replace(digitsOnly, "")
                                .toFloatOrZero(),
                            height = xml.getAttributeValue(getAttrPosition(xml, "height"))
                                .replace(digitsOnly, "")
                                .toFloatOrZero(),
                            viewportWidth =
                            xml.getAttributeValue(getAttrPosition(xml, "viewportWidth"))
                                .toFloatOrZero(),
                            viewportHeight =
                            xml.getAttributeValue(getAttrPosition(xml, "viewportHeight"))
                                .toFloatOrZero(),
                        )
                    }

                    "path" -> {
                        vectorDrawable = vectorDrawable.copy(
                            pathData = xml.getAttributeValue(getAttrPosition(xml, "pathData"))
                                .orEmpty(),
                        )
                    }
                }
                event = xml.next()
            }
        }

        return vectorDrawable
    }

    private fun getAttrPosition(xml: XmlPullParser, attrName: String): Int =
        (0 until xml.attributeCount)
            .firstOrNull { i -> xml.getAttributeName(i) == attrName }
            ?: -1
}

class DrawShape(private val vectorDrawable: VectorDrawable) : Shape {
    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density,
    ): Outline {
        return Outline.Generic(
            path = drawShape(
                vectorDrawable.pathData,
                size,
            ),
        )
    }
}

fun drawShape(pathData: String, size: Size): Path {
    return Path().apply {
        val path =
            PathParser.createPathFromPathData(pathData)
        val rectF = RectF()
        path.computeBounds(rectF, true)
        val matrix = Matrix()
        val scale = minOf(size.width / rectF.width(), size.height / rectF.height())
        matrix.setScale(scale, scale)
        path.transform(matrix)
        val composePath = path.asComposePath()
        addPath(composePath)
        close()
    }
}
