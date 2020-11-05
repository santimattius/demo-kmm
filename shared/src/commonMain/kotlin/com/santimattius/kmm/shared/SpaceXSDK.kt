package com.santimattius.kmm.shared

import com.santimattius.kmm.shared.cache.Database
import com.santimattius.kmm.shared.cache.DatabaseDriverFactory
import com.santimattius.kmm.shared.entity.RocketLaunch
import com.santimattius.kmm.shared.network.SpaceXApi

class SpaceXSDK (databaseDriverFactory: DatabaseDriverFactory) {
    private val database = Database(databaseDriverFactory)
    private val api = SpaceXApi()

    @Throws(Exception::class) suspend fun getLaunches(forceReload: Boolean): List<RocketLaunch> {
        val cachedLaunches = database.getAllLaunches()
        return if (cachedLaunches.isNotEmpty() && !forceReload) {
            cachedLaunches
        } else {
            api.getAllLaunches().also {
                database.clearDatabase()
                database.createLaunches(it)
            }
        }
    }
}