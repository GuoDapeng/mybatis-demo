package icu.guodapeng.mybatisdemo.component

import icu.guodapeng.mybatisdemo.config.SnowflakeConfig
import icu.guodapeng.mybatisdemo.utils.Snowflake
import org.springframework.context.annotation.Bean
import org.springframework.stereotype.Component
import javax.annotation.Resource

@Component
class SnowflakeFactory {
    @Resource
    lateinit var snowflakeConfig: SnowflakeConfig

    @Bean
    fun snowflake(): Snowflake {
        return Snowflake(snowflakeConfig.dataCenterId, snowflakeConfig.workerId)
    }
}
