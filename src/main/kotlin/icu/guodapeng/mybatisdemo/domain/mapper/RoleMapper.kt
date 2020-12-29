package icu.guodapeng.mybatisdemo.domain.mapper

import icu.guodapeng.mybatisdemo.domain.entity.Role
import org.apache.ibatis.annotations.Insert
import org.apache.ibatis.annotations.Mapper

@Mapper
interface RoleMapper {
    @Insert("insert into role (id,role) values (#{id},#{role})")
    fun insert(role: Role)
}
