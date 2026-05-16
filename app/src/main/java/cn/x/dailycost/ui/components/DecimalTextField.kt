package cn.x.dailycost.ui.components

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType


@Composable
fun DecimalInputField(
    value: Double,
    onValueChange: (Double) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    error: Boolean = false
) {
    // 格式化显示：整数时不显示小数点，小数时保留2位
    val displayText = remember(value) {
        when {
            value == 0.0 -> ""
            value == value.toLong().toDouble() -> {
                // 整数，不显示小数点
                value.toLong().toString()
            }
            else -> {
                // 小数，最多保留2位，去除末尾的0
                val formatted = String.format("%.2f", value)
                formatted.replace(Regex("\\.?0+$"), "").trimEnd('.')
            }
        }
    }

    var textState by remember(value) { mutableStateOf(displayText) }

    // 输入监听
    val onTextChange = { newText: String ->
        // 处理输入：去除非法字符，只保留数字和小数点
        val filtered = newText.filter { it.isDigit() || it == '.' }

        // 防止多个小数点
        val dotCount = filtered.count { it == '.' }
        var validText = if (dotCount > 1) {
            val firstDotIndex = filtered.indexOfFirst { it == '.' }
            filtered.take(firstDotIndex + 1) + filtered.substring(firstDotIndex + 1).replace(".", "")
        } else {
            filtered
        }

        // 限制小数点后最多2位
        val parts = validText.split(".")
        if (parts.size > 1 && parts[1].length > 2) {
            validText = "${parts[0]}.${parts[1].take(2)}"
        }

        // 限制整数部分长度（防止过长）
        val finalParts = validText.split(".")
        val integerPart = finalParts[0].take(10)

        val finalText = if (finalParts.size > 1) {
            // 有小数点的情况
            if (finalParts[1].isNotEmpty()) {
                "$integerPart.${finalParts[1]}"
            } else {
                "$integerPart."  // 保留小数点，等待用户输入小数部分
            }
        } else {
            // 没有小数点的情况
            integerPart
        }

        textState = finalText

        // 转换为Double
        val newValue = when {
            finalText.isEmpty() -> 0.0
            finalText == "." -> 0.0
            finalText.endsWith(".") -> {
                // 如果以小数点结尾，暂时转换为整数
                finalText.dropLast(1).toDoubleOrNull() ?: 0.0
            }
            else -> finalText.toDoubleOrNull() ?: 0.0
        }

        // 回调
        if (newValue != value) {
            onValueChange(newValue)
        }
    }

    TextField(
        value = textState,
        onValueChange = onTextChange,
        modifier = modifier,
        enabled = enabled,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
        singleLine = true,
        isError = error
    )
}

