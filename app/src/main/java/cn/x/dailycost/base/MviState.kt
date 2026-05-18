package cn.x.dailycost.base

/**
 * MVI State - UI 状态的单一数据源
 * 所有状态都是不可变的 data class
 */
interface MviState {
    val isLoading: Boolean
    val error: String?
}

/**
 * MVI Intent - 用户意图/事件
 * 表示用户与 UI 的交互操作
 */
interface MviIntent

/**
 * MVI Effect - 一次性副作用
 * 用于处理单次事件（如导航、Toast、Snackbar）
 */
interface MviEffect