package servermock

interface IServerHandler {
    suspend fun sendTicketDataToServer(name: String, priceValue: Int)
}