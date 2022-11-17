package com.example.movieapps.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "MovieNowPlayingRemoteKeysTable")
data class MovieNowPlayingKeysEntity (
    @PrimaryKey
    @SerializedName("id")
    val id: String,

    @SerializedName("prevKey")
    val prevKey: Int?,

    @SerializedName("nextKey")
    val nextKey: Int?
)