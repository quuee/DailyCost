package cn.x.dailycost.util

import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter

fun formatTimestamp(timestamp: Long): String {
    // 1. 将时间戳转换为 Instant
    val instant = Instant.ofEpochMilli(timestamp)
    // 2. 指定时区（例如：亚洲/上海），如果不指定会使用系统默认时区
    val zoneId = ZoneId.of("Asia/Shanghai")
    // 3. 定义格式化样式
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd").withZone(zoneId)
    // 4. 格式化并返回
    return formatter.format(instant)
}