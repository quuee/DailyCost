package cn.x.dailycost.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Serializable
@Entity(tableName = "category")
data class CategoryEntity(
    @PrimaryKey(autoGenerate = true)
    val cid: Long,
    val name: String,
    val color: Int,
    val sort: Int,
)