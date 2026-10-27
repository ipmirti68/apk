package com.mirti.tv

import android.net.Uri
import android.os.Bundle
import android.view.View
import android.webkit.JavascriptInterface
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.ui.PlayerView

class MainActivity : AppCompatActivity() {
    private lateinit var webView: WebView
    private lateinit var playerView: PlayerView
    private lateinit var exoPlayer: ExoPlayer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        webView = findViewById(R.id.webview)
        playerView = findViewById(R.id.playerView)
        
        exoPlayer = ExoPlayer.Builder(this).build()
        playerView.player = exoPlayer

        webView.settings.javaScriptEnabled = true
        webView.settings.domStorageEnabled = true
        webView.settings.allowFileAccess = true
        webView.settings.mediaPlaybackRequiresUserGesture = false

        // APK olduğunu siteye söyle - ÇOKLU İSİM DESTEĞİ
        webView.addJavascriptInterface(this, "MirtiApp")
        webView.addJavascriptInterface(this, "AndroidPlayer")

        webView.webViewClient = WebViewClient()
        webView.loadUrl("https://ipmirti68.github.io/-ptv-/")
    }

    // Yeni versiyon (title'lı) - HTML bunu çağırıyor
    @JavascriptInterface
    fun playVideo(url: String, title: String) {
        runOnUiThread { playWithExoPlayer(url) }
    }

    // Eski versiyon için de destek (tek parametre)
    @JavascriptInterface
    fun playVideo(url: String) {
        runOnUiThread { playWithExoPlayer(url) }
    }

    @JavascriptInterface
    fun play(url: String) {
        runOnUiThread { playWithExoPlayer(url) }
    }

    private fun playWithExoPlayer(url: String) {
        try {
            val cleanUrl = url.trim()
            val mediaItem = MediaItem.fromUri(Uri.parse(cleanUrl))
            exoPlayer.setMediaItem(mediaItem)
            exoPlayer.prepare()
            exoPlayer.play()
            playerView.visibility = View.VISIBLE
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun onBackPressed() {
        if (playerView.visibility == View.VISIBLE) {
            playerView.visibility = View.GONE
            exoPlayer.stop()
        } else if (webView.canGoBack()) {
            webView.goBack()
        } else {
            super.onBackPressed()
        }
    }
}
