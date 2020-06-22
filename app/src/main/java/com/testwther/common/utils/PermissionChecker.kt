package com.testwther.common.utils

import android.Manifest
import android.content.Context
import com.androidisland.ezpermission.EzPermission
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.launch

class PermissionChecker(
    private val context: Context
) {

    suspend fun checkLocationPermission(): Boolean {
        return requestSinglePermission(Manifest.permission.ACCESS_FINE_LOCATION)
    }

    private suspend fun requestSinglePermission(permission: String): Boolean {
        val result = Channel<Boolean>()

        EzPermission.with(context)
            .permissions(
                permission
            )
            .request { granted, _, _ ->
                GlobalScope.launch {
                    result.send(
                        granted.contains(permission)
                    )
                }
            }

        return result.receive()
    }

}