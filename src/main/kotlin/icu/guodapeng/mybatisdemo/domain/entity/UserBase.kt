package icu.guodapeng.mybatisdemo.domain.entity

data class UserBase(
    var id: Long? = null,
    var account: String? = null,
    var password: String? = null,
    var nickname: String? = null,
    var avatar: String? = null,
    var mobile: String? = null,
    var email: String? = null,
    var birthday: Int? = null,
    var sex: Int? = null,
    var integral: Int? = null,
    var service_level: Int? = null,
    var create_time: Long? = null,
    var update_time: Long? = null,
    var status: Int? = null,

    var roles: List<UserRoles>? = null
)
