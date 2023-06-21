package com.example.testtickettaskkotlin.serialization

import android.content.Context
import androidx.datastore.core.CorruptionException
import androidx.datastore.core.DataStore
import androidx.datastore.core.Serializer
import androidx.datastore.dataStore
import com.example.testticketaskkotlin.Ticket
import com.google.protobuf.InvalidProtocolBufferException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.InputStream
import java.io.OutputStream


object TicketStoreSerializer : Serializer<Ticket> {


    override val defaultValue: Ticket = Ticket.getDefaultInstance()

    override suspend fun readFrom(input: InputStream): Ticket = withContext(Dispatchers.IO) {
        try{
            return@withContext Ticket.parseFrom(input)
        } catch (exception : InvalidProtocolBufferException) {
            throw CorruptionException("Error Reading Data", exception)
        }
    }

    override suspend fun writeTo(t: Ticket, output: OutputStream) = withContext(Dispatchers.IO) {t.writeTo(output)}

}

val Context.ticketDataStore: DataStore<Ticket> by dataStore(
    fileName = "ticket_store.pb",
    serializer = TicketStoreSerializer
)