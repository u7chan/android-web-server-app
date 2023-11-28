package com.example.webserverapp.lib

import android.content.Context
import fi.iki.elonen.NanoHTTPD

class WebServer(private val context: Context, port: Int) : NanoHTTPD(port) {
    override fun serve(session: IHTTPSession?): Response {
        return when (val uri = session?.uri ?: "") {
            "" -> super.serve(session)
            "/" -> {
                responseFromAssets("index.html")
            }
            else -> {
                responseFromAssets(uri.substring(1)) // 先頭の / スラッシュを除去する
            }
        }
    }

    private fun responseFromAssets(fileName: String): Response {
        val supportMimeType = mapOf(
            "html" to "text/html",
            "svg" to "image/svg+xml",
            "js" to "text/javascript",
            "css" to "text/css",
        )
        try {
            val fileExtension = fileName.split(".")[1]
            val mimeType = supportMimeType[fileExtension] ?: return newFixedLengthResponse(
                Response.Status.UNSUPPORTED_MEDIA_TYPE,
                "text/plain",
                "Unsupported Media Type: $fileExtension"
            )
            return newChunkedResponse(Response.Status.OK, mimeType, context.assets.open(fileName))
        } catch (e: Exception) {
            e.printStackTrace()
            throw e
        }
    }
}

