package cn.x.dailycost.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import cn.x.dailycost.data.dao.CategoryDao
import cn.x.dailycost.data.dao.GoodsItemDao
import cn.x.dailycost.data.entity.CategoryEntity
import cn.x.dailycost.data.entity.GoodsItemEntity
import java.util.Date

@Database(
    entities = [
        CategoryEntity::class,
        GoodsItemEntity::class,
    ],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class DailyCostData : RoomDatabase() {
    abstract fun categoryDao(): CategoryDao
    abstract fun goodItemsDao(): GoodsItemDao

}

// 类型转换器
class Converters {
    @TypeConverter
    fun fromTimestamp(value: Long?): Date? = value?.let { Date(it) }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? = date?.time
}