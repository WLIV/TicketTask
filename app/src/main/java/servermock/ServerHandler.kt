package servermock

import androidx.datastore.core.DataStore
import com.example.testticketaskkotlin.Ticket
import com.example.testticketaskkotlin.copy
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.collectIndexed
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.toList

class ServerHandler(private val storage : DataStore<Ticket>) : IServerHandler {

    //datastore is used as fake endpoint
    override suspend fun sendTicketDataToServer(name: String, priceValue: Int) {
        storage.updateData {ticket ->
            ticket.copy {
                productName = name
                price = priceValue
                id++
            }
        }
        storage.data.collectLatest {
            println(it.id)
            println(it.productName)
            println(it.price)
        }
    }
}