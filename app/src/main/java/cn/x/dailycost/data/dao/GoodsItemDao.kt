package cn.x.dailycost.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.RawQuery
import androidx.room.Update
import androidx.sqlite.db.SupportSQLiteQuery
import cn.x.dailycost.data.entity.GoodsItemEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface GoodsItemDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(goodsItem: GoodsItemEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(goods: List<GoodsItemEntity>)

    @Update(onConflict = OnConflictStrategy.REPLACE) // 发生冲突时替换旧数据
    suspend fun update(goodsItem: GoodsItemEntity)

    @Delete
    suspend fun delete(goodsItem: GoodsItemEntity)

    @Query("select * from goods_item where gid = :gid")
    suspend fun getById(gid: Long): GoodsItemEntity

    @Query("select * from goods_item")
    fun queryAll(): List<GoodsItemEntity>

    @RawQuery(observedEntities = [GoodsItemEntity::class])
    fun query(query: SupportSQLiteQuery): Flow<List<GoodsItemEntity>>


    /**
     * List = 一次性快照  vs  Flow = 实时观察流（数据库一变，自动重新查询并推送新数据）
     * | 特性 | `List<GoodsItemEntity>` | `Flow<List<GoodsItemEntity>>` |
     * | :--- | :--- | :--- |
     * | 数据性质 | 静态快照，查询时刻的数据副本 | 动态响应式流，持续监听数据变化 |
     * | 自动刷新 | ❌ 不会。数据变更后需手动重新调用 | ✅ 会。Room 检测到相关表变更时自动重查并发射新值 |
     * | 线程要求 | ⚠️ 不能在主线程调用（除非标记 `@MainThread`），否则崩溃 | ✅ 可以在主线程安全收集（Room 内部自动切换到后台线程查询） |
     * | 使用场景 | 一次性读取、导出、后台任务处理 | UI 展示列表、需要实时同步的页面 |
     * | 生命周期 | 无感知，需自行管理协程/线程 | 配合 `lifecycleScope` / `viewModelScope` 自动取消，防止泄漏 |
     * | 性能开销 | 低（单次查询） | 略高（注册了 Invalidator 监听器 + 重复查询） |
     *
     *
     */
}