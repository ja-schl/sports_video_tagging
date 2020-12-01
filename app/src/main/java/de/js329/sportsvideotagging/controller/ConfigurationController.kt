package de.js329.sportsvideotagging.controller

import de.js329.sportsvideotagging.database.EventDao
import de.js329.sportsvideotagging.database.PlayerDao
import de.js329.sportsvideotagging.database.TeamDao
import de.js329.sportsvideotagging.datamodels.EventAttribute
import de.js329.sportsvideotagging.datamodels.EventType
import de.js329.sportsvideotagging.datamodels.Player
import de.js329.sportsvideotagging.datamodels.Team

class ConfigurationController(
        private val eventDao: EventDao,
        private val playerDao: PlayerDao,
        private val teamDao: TeamDao
) {

    suspend fun addPlayer(name: String, number: Int, teamId: Long): Player? {
        val player = Player(null, number, teamId, name)
        val autoGeneratedId = playerDao.insert(player)
        if (autoGeneratedId != -1L) player.playerId = autoGeneratedId else return null
        return player
    }

    suspend fun deletePlayer(player: Player) {
        playerDao.delete(player)
    }

    suspend fun addTeam(teamName: String): Team? {
        val team = Team(null, teamName)
        val autoGeneratedId = teamDao.insert(team)
        if (autoGeneratedId != -1L) team.uid = autoGeneratedId else return null
        return team
    }

    suspend fun deleteTeam(team: Team) {
        teamDao.delete(team)
    }

    suspend fun addEventAttribute(attributeName: String): EventAttribute? {
        val attribute = EventAttribute(null, attributeName)
        val autoGeneratedId = eventDao.insert(attribute)
        if (autoGeneratedId != -1L) attribute.attributeId = autoGeneratedId else return null
        return attribute
    }

    suspend fun deleteEventAttribute(attribute: EventAttribute) {
        eventDao.delete(attribute)
    }

    suspend fun getAllEventAttributes(): List<EventAttribute> {
        return eventDao.getAllAttributes()
    }

    fun addEventType(eventTitle: String, longTimedEvent: Boolean, timeOffset: Long, playerSelection: Boolean) {
        val eventType = EventType(null, eventTitle, longTimedEvent, timeOffset, playerSelection)
        eventType.uid = eventDao.insert(eventType)
    }

    suspend fun getAllTeams(): List<Team> {
        return teamDao.getAll()
    }

    suspend fun getTeamForId(teamId: Long): Team? {
        return teamDao.getTeamForId(teamId)
    }

    suspend fun getPlayersForTeam(team: Team): List<Player> {
        team.uid?.let {
            return playerDao.getPlayersForTeam(it)
        } ?: return ArrayList()
    }

    suspend fun getPlayersForTeam(teamId: Long): List<Player> {
        return playerDao.getPlayersForTeam(teamId)
    }
}