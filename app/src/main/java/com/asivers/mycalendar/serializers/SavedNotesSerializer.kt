package com.asivers.mycalendar.serializers

import android.content.Context
import androidx.datastore.core.CorruptionException
import androidx.datastore.core.DataStore
import androidx.datastore.core.Serializer
import androidx.datastore.dataStore
import com.asivers.mycalendar.data.proto.SavedNotesOuterClass
import com.google.protobuf.InvalidProtocolBufferException
import java.io.InputStream
import java.io.OutputStream

object SavedNotesSerializer : Serializer<SavedNotesOuterClass.SavedNotes> {
    override val defaultValue: SavedNotesOuterClass.SavedNotes = SavedNotesOuterClass.SavedNotes.getDefaultInstance()

    override suspend fun readFrom(input: InputStream): SavedNotesOuterClass.SavedNotes {
        try {
            return SavedNotesOuterClass.SavedNotes.parseFrom(input)
        } catch (exception: InvalidProtocolBufferException) {
            throw CorruptionException("Cannot read proto.", exception)
        }
    }

    override suspend fun writeTo(
        t: SavedNotesOuterClass.SavedNotes,
        output: OutputStream
    ) = t.writeTo(output)
}

val Context.savedNotesDataStore: DataStore<SavedNotesOuterClass.SavedNotes> by dataStore(
    fileName = "savedNotes.pb",
    serializer = SavedNotesSerializer
)
