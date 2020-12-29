package icu.guodapeng.mybatisdemo.utils

import java.text.SimpleDateFormat
import java.util.*

/**
 * Snowflake 64bit
 * +--------------+-----------------+-----------------+----------------------------------+
 * | 符号位 0 1bit | 时间戳 41bit     | 数据中心ID 5bit   | 机器ID 5bit     | 序号 12bit      |
 * +--------------+-----------------+-----------------+----------------------------------+
 * | bit 63       | bit 62 - bit 22 | bit 21 - bit 17 | bit 16 - bit 12 | bit 11 - bit 0 |
 * +--------------+-----------------+-----------------+----------------------------------+
 * 12 位计数器，到下一毫秒计数器归零
 * @param dataCenterId  数据中心id
 * @param workerId      当前的机器id
 */
class Snowflake(
    private val dataCenterId: Long,
    private val workerId: Long
) {

    // ID中41位时间戳的起点 (2020-01-01 00:00:00.00)
    private val startPoint = 1577808000000L

    // 保留字符长度(符号位)
    private val retainLength = 1

    // 时间戳长度
    private val timeLength = 41

    // 数据中心id长度、最大值(31)
    private val dataCenterIdLength = 5
    private val dataCenterIdMax = (-1).ushr(32 - dataCenterIdLength)

    // 机器id长度、最大值(31)
    private val workIdLength = 5
    private val workIdMax = (-1).ushr(32 - workIdLength)

    // 计数器长度、最大值(4095)
    private val sequenceLength = 12
    private val sequenceMax = (-1).ushr(32 - sequenceLength)

    // 当前毫秒的计数
    private var sequence: Long = 0

    fun getSequence() = sequence

    // 上一个生成ID的时间戳
    private var lastTimeStamp: Long = -1L

    fun getLastTimeStamp() = lastTimeStamp

    init {
        if (dataCenterId > dataCenterIdMax || workerId > workIdMax) {
            throw IllegalArgumentException("dataCenterId or workId is bigger than the max value.")
        }
    }

    @Synchronized
    operator fun next(): Long {
        // 判断毫秒和计数
        val currentTimeStamp = System.currentTimeMillis()

        if (currentTimeStamp < lastTimeStamp) {
            throw RuntimeException("system clock is dialed back.")
        }

        if (currentTimeStamp == lastTimeStamp) {
            sequence++
            // 判断count是否到最大值
            if (sequence > sequenceMax) {
                // 则自旋，直到下一毫秒
                while (currentTimeStamp == lastTimeStamp) {
                    lastTimeStamp = System.currentTimeMillis()
                }
                sequence = 0
            }
        } else {
            lastTimeStamp = currentTimeStamp
            sequence = 0
        }

        return (lastTimeStamp - startPoint shl dataCenterIdLength + workIdLength + sequenceLength
                or (dataCenterId shl workIdLength + sequenceLength)
                or (workerId shl sequenceLength)
                or sequence)
    }

    fun decodeTimeStamp(id: Long): Long {
        return ((id shl retainLength) ushr (64 - timeLength)) + startPoint
    }

    fun decodeTimeFormat(id: Long, pattern: String = "yyyy-MM-dd HH:mm:ss.SSS"): String {
        return SimpleDateFormat(pattern).format(Date(decodeTimeStamp(id)))
    }

    fun decodeDataCenterId(id: Long): Long {
        return (id shl (retainLength + timeLength)) ushr (64 - dataCenterIdLength)
    }

    fun decodeWorkId(id: Long): Long {
        return (id shl (retainLength + timeLength + dataCenterIdLength)) ushr (64 - workIdLength)
    }

    fun decodeSequence(id: Long): Long {
        return (id shl (64 - sequenceLength)) ushr (64 - sequenceLength)
    }

    fun decode(id: Long): Map<String, Any> {
        return mapOf(
            "id" to id,
            "lastTimeStamp" to decodeTimeStamp(id),
            "dataCenterId" to decodeDataCenterId(id),
            "workId" to decodeWorkId(id),
            "sequence" to decodeSequence(id)
        )
    }
}
