package com.asivers.mycalendar.serializers

import android.content.Context
import androidx.datastore.core.CorruptionException
import androidx.datastore.core.DataStore
import androidx.datastore.core.Serializer
import androidx.datastore.dataStore
import com.asivers.mycalendar.data.SavedSettingsOuterClass
import com.google.protobuf.InvalidProtocolBufferException
import java.io.InputStream
import java.io.OutputStream

object SavedSettingsSerializer : Serializer<SavedSettingsOuterClass.SavedSettings> {
    override val defaultValue: SavedSettingsOuterClass.SavedSettings = SavedSettingsOuterClass.SavedSettings.getDefaultInstance()

    override suspend fun readFrom(input: InputStream): SavedSettingsOuterClass.SavedSettings {
        try {
            return SavedSettingsOuterClass.SavedSettings.parseFrom(input)
        } catch (exception: InvalidProtocolBufferException) {
            throw CorruptionException("Cannot read proto.", exception)
        }
    }

    override suspend fun writeTo(
        t: SavedSettingsOuterClass.SavedSettings,
        output: OutputStream
    ) = t.writeTo(output)
}

val Context.savedSettingsDataStore: DataStore<SavedSettingsOuterClass.SavedSettings> by dataStore(
    fileName = "savedSettings.pb",
    serializer = SavedSettingsSerializer
)
