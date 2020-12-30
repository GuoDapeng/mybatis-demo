package icu.guodapeng.mybatisdemo.domain

import icu.guodapeng.mybatisdemo.domain.entity.Role
import icu.guodapeng.mybatisdemo.domain.entity.UserBase
import icu.guodapeng.mybatisdemo.domain.entity.UserRoles
import icu.guodapeng.mybatisdemo.domain.mapper.RoleMapper
import icu.guodapeng.mybatisdemo.domain.mapper.UserMapper
import icu.guodapeng.mybatisdemo.domain.mapper.UserRolesMapper
import icu.guodapeng.mybatisdemo.utils.Snowflake
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import javax.annotation.Resource

@SpringBootTest
class UserRolesTests {
    @Resource
    lateinit var snowflake: Snowflake

    @Resource
    lateinit var userMapper: UserMapper

    @Resource
    lateinit var roleMapper: RoleMapper

    @Resource
    lateinit var userRolesMapper: UserRolesMapper

    @Test
    fun testGetUserAndRolesLazyLoading() {
        val userId = snowflake.next()
        val account = "account_${snowflake.next()}"
        userMapper.insert(
            UserBase(
                id = userId,
                account = account
            )
        )

        for (i in 1..2) {
            val roleId = snowflake.next()
            roleMapper.insert(
                Role(
                    id = roleId,
                    role = "role_${snowflake.next()}"
                )
            )

            userRolesMapper.insert(
                UserRoles(
                    user_id = userId,
                    roles_id = roleId
                )
            )
        }

        val user = userMapper.getUserAndRolesLazyLoading(account)
        Thread.sleep(3)
        println("user: ------")
        println("user: $user")
    }
}
