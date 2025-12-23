package com.example.userselectionapp.data.source

import com.example.userselectionapp.domain.model.User
import kotlinx.coroutines.delay

class FakeDataSource {
    suspend fun fetchUsers(): List<User>{
        delay(1500)
        return listOf(
            User(1, "Divya Basatwar", "divya@gmail.com"),
            User(2, "Jyoti Debadwar", "Jyoti@gmail.com"),
            User(3, "Swati Dhumshetwar", "swati@gmail.com"),
            User(4, "Kashikant Basatwar", "kashi@gmail.com"),
            User(5, "Atharv Debadwar", "atharv@gmail.com"),
            User(6, "Asmita Debadwar", "asmita@gmail.com"),
            User(7, "Vedashri Dhumshetwar", "vedshri@gmail.com"),
            User(8, "Ishwari Dhumshetwar", "ishwari@gmail.com"),
            User(9, "Omakar Dhumshetwar", "omkar@gmail.com"),
            User(10, "Shakuntala Basatwar", "shakuntala@gmail.com"),
            User(11, "Shivajirao Basatwar", "shivaji@gmail.com")
        )
    }
}