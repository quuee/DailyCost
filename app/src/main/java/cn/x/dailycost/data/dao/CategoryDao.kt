package cn.x.dailycost.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import cn.x.dailycost.data.entity.CategoryEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CategoryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(category: CategoryEntity)

    @Update(onConflict = OnConflictStrategy.REPLACE) // 发生冲突时替换旧数据
    suspend fun update(category: CategoryEntity)

    @Update(onConflict = OnConflictStrategy.REPLACE) // 发生冲突时替换旧数据
    suspend fun updateAll(categories: List<CategoryEntity>)

    @Delete
    suspend fun delete(category: CategoryEntity)

    @Query("SELECT * FROM category order by sort")
    fun getAllCategories(): Flow<List<CategoryEntity>>


}