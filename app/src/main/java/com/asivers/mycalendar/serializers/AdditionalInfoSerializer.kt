package com.asivers.mycalendar.serializers

import android.content.Context
import androidx.datastore.core.CorruptionException
import androidx.datastore.core.DataStore
import androidx.datastore.core.Serializer
import androidx.datastore.dataStore
import com.asivers.mycalendar.data.proto.AdditionalInfoOuterClass
import com.google.protobuf.InvalidProtocolBufferException
import java.io.InputStream
import java.io.OutputStream

object AdditionalInfoSerializer : Serializer<AdditionalInfoOuterClass.AdditionalInfo> {
    override val defaultValue: AdditionalInfoOuterClass.AdditionalInfo = AdditionalInfoOuterClass.AdditionalInfo.getDefaultInstance()

    override suspend fun readFrom(input: InputStream): AdditionalInfoOuterClass.AdditionalInfo {
        try {
            return AdditionalInfoOuterClass.AdditionalInfo.parseFrom(input)
        } catch (exception: InvalidProtocolBufferException) {
            throw CorruptionException("Cannot read proto.", exception)
        }
    }

    override suspend fun writeTo(
        t: AdditionalInfoOuterClass.AdditionalInfo,
        output: OutputStream
    ) = t.writeTo(output)
}

val Context.additionalInfoDataStore: DataStore<AdditionalInfoOuterClass.AdditionalInfo> by dataStore(
    fileName = "additionalInfo.pb",
    serializer = AdditionalInfoSerializer
)
