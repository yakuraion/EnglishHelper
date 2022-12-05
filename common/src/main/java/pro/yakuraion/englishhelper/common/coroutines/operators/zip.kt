package pro.yakuraion.englishhelper.common.coroutines.operators

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.zip

fun <T1, T2, T3, R> flowZip(
    flow1: Flow<T1>,
    flow2: Flow<T2>,
    flow3: Flow<T3>,
    transform: suspend (T1, T2, T3) -> R
): Flow<R> {
    return flow1
        .zip(flow2) { first, second -> Pair(first, second) }
        .zip(flow3) { (first, second), third -> transform(first, second, third) }
}

fun <T1, T2, T3> flowZip(
    flow1: Flow<T1>,
    flow2: Flow<T2>,
    flow3: Flow<T3>
): Flow<Triple<T1, T2, T3>> {
    return flowZip(flow1, flow2, flow3) { t1, t2, t3 -> Triple(t1, t2, t3) }
}
