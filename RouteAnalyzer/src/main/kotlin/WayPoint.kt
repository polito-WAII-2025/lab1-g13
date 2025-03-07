package org.example

import kotlinx.serialization.Serializable

@Serializable
data class WayPoint(val time: String, val altitude:String, val distance:String)