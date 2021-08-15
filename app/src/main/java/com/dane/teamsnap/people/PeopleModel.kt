package com.dane.teamsnap.people

import javax.inject.Inject

data class Player(val id: String, val name: String)
data class Owner(val id: String, val name: String, val type: String)

class PeopleModel @Inject constructor() {
    fun getPlayers(): List<Player> {
        return listOf()
    }

    fun getOwners(): List<Owner> {
        return listOf(Owner("c19114fc-68f4-4336-91a4-c54760bb00f5", "Dane Jung", "Owner"))
    }
}
