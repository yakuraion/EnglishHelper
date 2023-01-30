package pro.yakuraion.englishhelper.common.coroutines.operators

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine

fun <T1, T2, T3, R> flowCombine(
    flow1: Flow<T1>,
    flow2: Flow<T2>,
    flow3: Flow<T3>,
    transform: suspend (T1, T2, T3) -> R
): Flow<R> {
    return flow1
        .combine(flow2) { first, second -> Pair(first, second) }
        .combine(flow3) { (first, second), third -> transform(first, second, third) }
}

fun <T1, T2, T3> flowCombine(
    flow1: Flow<T1>,
    flow2: Flow<T2>,
    flow3: Flow<T3>
): Flow<Triple<T1, T2, T3>> {
    return flowCombine(flow1, flow2, flow3) { t1, t2, t3 -> Triple(t1, t2, t3) }
}
