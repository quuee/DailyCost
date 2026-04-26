package cn.x.dailycost.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

val lineHeightMultiplier = 1.2f // 行高小点

val Typography = Typography(
    // 小标签（8sp）
    labelSmall = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 8.sp,
        lineHeight = (8 * lineHeightMultiplier).sp,
        letterSpacing = 0.5.sp
    ),

    // 辅助文字（10sp）
    labelMedium = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 10.sp,
        lineHeight = (10 * lineHeightMultiplier).sp,
        letterSpacing = 0.25.sp
    ),

    // 正文小（12sp）
    bodySmall = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp,
        lineHeight = (12 * lineHeightMultiplier).sp,
        letterSpacing = 0.4.sp
    ),

    // 正文标准（14sp）
    bodyMedium = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        lineHeight = (14 * lineHeightMultiplier).sp,
        letterSpacing = 0.25.sp
    ),

    // 正文大（15sp）
    bodyLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Medium,
        fontSize = 15.sp,
        lineHeight = (15 * lineHeightMultiplier).sp,
        letterSpacing = 0.sp
    ),

    // 小标题（16sp）
    titleSmall = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Medium,
        fontSize = 16.sp,
        lineHeight = (16 * lineHeightMultiplier).sp,
        letterSpacing = 0.15.sp
    ),

    // 中标题（17sp）
    titleMedium = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.SemiBold,
        fontSize = 17.sp,
        lineHeight = (17 * lineHeightMultiplier).sp,
        letterSpacing = 0.sp
    ),

    // 大标题（18sp）
    titleLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.SemiBold,
        fontSize = 18.sp,
        lineHeight = (18 * lineHeightMultiplier).sp,
        letterSpacing = 0.sp
    ),

    // 保留 Material 默认样式
    displayLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Bold,
        fontSize = 34.sp,
        lineHeight = (34 * lineHeightMultiplier).sp
    ),
    displayMedium = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Bold,
        fontSize = 28.sp,
        lineHeight = (28 * lineHeightMultiplier).sp
    ),
    displaySmall = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Bold,
        fontSize = 22.sp,
        lineHeight = (22 * lineHeightMultiplier).sp
    ),
    headlineLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.SemiBold,
        fontSize = 20.sp,
        lineHeight = (20 * lineHeightMultiplier).sp
    )
)