package cn.x.dailycost.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/**
 * MVI ViewModel 基类
 * 封装了状态管理和副作用处理的核心逻辑
 *
 * @param S State 类型
 * @param I Intent 类型
 * @param E Effect 类型
 */
abstract class MviViewModel<S : MviState, I : MviIntent, E : MviEffect>(
    initialState: S
) : ViewModel() {

    // 私有状态流 (使用 StateFlow 保证状态一致性和可观察性)
    private val _state = MutableStateFlow(initialState)
    val state: StateFlow<S> = _state.asStateFlow()

    // 私有副作用通道 (Channel 用于处理一次性事件)
    private val _effect = Channel<E>(Channel.BUFFERED)
    val effect: Flow<E> = _effect.receiveAsFlow()

    // 抽象方法：子类实现 Intent 到 State 的转换逻辑
    protected abstract suspend fun handleIntent(intent: I)

    // 发送 Intent
    fun processIntent(intent: I) {
        viewModelScope.launch { handleIntent(intent)  }
    }

    // 更新状态 (不可变)
    protected fun setState(reducer: S.() -> S) {
//        _state.update { currentState ->
//            currentState.reducer()
//        }
        // 新写法
        _state.update { it.reducer() }
    }

    // 发送副作用
    protected fun sendEffect(effect: E) {
//        viewModelScope.launch {
//            _effect.send(effect)
//        }
        // 新写法 使用 trySend 避免不必要的协程挂起开销
        _effect.trySend(effect)
    }

    // 获取当前状态快照
    protected fun currentState(): S = _state.value
}