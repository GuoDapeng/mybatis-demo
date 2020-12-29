package icu.guodapeng.mybatisdemo.domain.mapper

import icu.guodapeng.mybatisdemo.domain.entity.UserBase
import org.apache.ibatis.annotations.Insert
import org.apache.ibatis.annotations.Mapper
import org.apache.ibatis.annotations.Param

@Mapper
interface UserMapper {
    @Insert("insert into user_base (id,account) values (#{id},#{account})")
    fun insert(userBase: UserBase)

    fun getUserAndRolesLazyLoading(@Param("account") account: String): UserBase
}
