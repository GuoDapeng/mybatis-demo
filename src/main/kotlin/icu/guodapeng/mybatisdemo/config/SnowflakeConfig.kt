package icu.guodapeng.mybatisdemo.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component

@Component
@ConfigurationProperties(prefix = "snowflake")
object SnowflakeConfig {
    var dataCenterId: Long = 0
    var workerId: Long = 0
}
