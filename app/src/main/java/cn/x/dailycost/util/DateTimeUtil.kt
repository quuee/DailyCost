package cn.x.dailycost.util

import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit

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

fun getDaysDifference(givenTimeMillis: Long): Long {
    // 1. 获取当前时间戳和给定的时间戳的 Instant
    val now = Instant.now()
    val givenInstant = Instant.ofEpochMilli(givenTimeMillis)

    // 2. 转换为系统默认时区的 LocalDate（只保留日期，去掉时分秒，这样计算的是“天数”）
    val zone = ZoneId.systemDefault()
    val nowDate = now.atZone(zone).toLocalDate()
    val givenDate = givenInstant.atZone(zone).toLocalDate()

    // 3. 计算两个日期之间的天数差
    // 如果 givenTimeMillis 是过去的时间，结果为正数
    return ChronoUnit.DAYS.between(givenDate, nowDate)
}

fun getDaysDifference(oldTime:Long,newTime:Long): Long {
    val oldTimeInstant = Instant.ofEpochMilli(oldTime)
    val newTimeInstant = Instant.ofEpochMilli(newTime)

    // 2. 转换为系统默认时区的 LocalDate（只保留日期，去掉时分秒，这样计算的是“天数”）
    val zone = ZoneId.systemDefault()
    val nowDate = newTimeInstant.atZone(zone).toLocalDate()
    val givenDate = oldTimeInstant.atZone(zone).toLocalDate()

    // 3. 计算两个日期之间的天数差
    // 如果 givenTimeMillis 是过去的时间，结果为正数
    return ChronoUnit.DAYS.between(givenDate, nowDate)
}