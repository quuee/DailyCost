package cn.x.dailycost.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.RawQuery
import androidx.room.Update
import androidx.sqlite.db.SupportSQLiteQuery
import cn.x.dailycost.data.entity.GoodsItemEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface GoodsItemDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    suspend fun insert(goodsItem: List<GoodsItemEntity>)
    suspend fun insert(goodsItem: GoodsItemEntity)

    @Update(onConflict = OnConflictStrategy.REPLACE) // 发生冲突时替换旧数据
    suspend fun update(goodsItem: GoodsItemEntity)

    @Delete
    suspend fun delete(goodsItem: GoodsItemEntity)

    @RawQuery(observedEntities = [GoodsItemEntity::class])
    fun query(query: SupportSQLiteQuery): Flow<List<GoodsItemEntity>>
}