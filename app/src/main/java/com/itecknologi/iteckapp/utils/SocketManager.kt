package com.itecknologi.iteckapp.utils

import android.util.Log
import androidx.annotation.Nullable
import io.socket.client.Ack
import io.socket.client.IO
import io.socket.client.Socket
import io.socket.emitter.Emitter
import java.net.URI

object SocketManager {
    private var mSocket: Socket? = null


    init {
        try {

            val url = Constants.socket_url
            val uri = URI.create(url)
            val options = IO.Options.builder()
                    .build()

//            mSocket = IO.socket(uri)
            mSocket = IO.socket(uri, options)
        } catch (e: Exception) {
            Log.e("Socket init", e.message!!)
        }

    }

    fun connect(listener: Emitter.Listener) {
        on(Socket.EVENT_CONNECT, listener)
//        on(Helper.SocketChannels.GetUsers.channel, listener)
//        on(Helper.SocketChannels.GetMessage.channel, listener)
//        on(Helper.SocketChannels.SendMessage.channel, listener)
//        on(Helper.SocketChannels.StartCall.channel, listener)
//        on(Helper.SocketChannels.DeclineCall.channel, listener)
//        on(Helper.SocketChannels.EndCall.channel, listener)
//        on(Helper.SocketChannels.UserLeft.channel, listener)
//        on(Helper.SocketChannels.AgentLeft.channel, listener)

        mSocket?.connect()
    }

    fun emitTakeUser(event: String, obj: Any?, obj2: Any?, ack: Ack?) {
        mSocket?.emit(event, obj, obj2, ack)

    }

    fun emitRemoveUser(event: String, obj: Any?, obj2: Any?, ack: Ack?) {
        mSocket?.emit(event, obj, obj2, ack)

    }

    fun emit(event: String, obj: Any?, ack: Ack?) {
        mSocket?.emit(event, obj, ack)
    }

    fun listenTo(event: String, listener: Emitter.Listener) {
        mSocket?.on(event, listener)
    }

    private fun on(event: String, listener: Emitter.Listener) {
        mSocket?.on(event, listener)
    }

    fun clearSession() {
        mSocket?.off(Socket.EVENT_CONNECT)
        mSocket?.disconnect()
    }

    fun isConnected(): Boolean {
        return mSocket?.connected() ?: false
    }

    fun socket(): Socket? {
        return mSocket
    }

}
