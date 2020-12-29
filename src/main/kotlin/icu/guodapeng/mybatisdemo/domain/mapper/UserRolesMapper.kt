package icu.guodapeng.mybatisdemo.domain.mapper

import icu.guodapeng.mybatisdemo.domain.entity.UserRoles
import org.apache.ibatis.annotations.Insert
import org.apache.ibatis.annotations.Mapper

@Mapper
interface UserRolesMapper {
    @Insert("insert into user_roles (user_id,roles_id) values (#{user_id},#{roles_id})")
    fun insert(userRoles: UserRoles)
}
