package com.codex.business.components.user.repo

import com.codex.base.utils.CustomPanacheRepositoryBase
import io.quarkus.hibernate.orm.panache.kotlin.PanacheRepositoryBase
import jakarta.enterprise.context.ApplicationScoped

@ApplicationScoped
class UserRepo : CustomPanacheRepositoryBase<User, String>