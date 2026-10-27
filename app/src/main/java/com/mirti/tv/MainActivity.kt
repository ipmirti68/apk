package com.mirti.tv

import android.net.Uri
import android.os.Bundle
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
        
        // APK olduğunu siteye söyle
        webView.addJavascriptInterface(this, "MirtiApp")

        webView.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
                // index.html içinden gelen video url'lerini yakala
                if (url.contains(".m3u8") || url.contains("output=ts") || url.contains(".mp4")) {
                    playWithExoPlayer(url)
                    return true
                }
                return false
            }
        }

        webView.loadUrl("https://ipmirti68.github.io/-ptv-/")
    }

    @android.webkit.JavascriptInterface
    fun playVideo(url: String) {
        runOnUiThread { playWithExoPlayer(url) }
    }

    private fun playWithExoPlayer(url: String) {
        // PROXY YOK - Direk cihaz IP'si ile oyna (TR kilidi kalkar)
        val mediaItem = MediaItem.fromUri(Uri.parse(url))
        exoPlayer.setMediaItem(mediaItem)
        exoPlayer.prepare()
        exoPlayer.play()
        playerView.visibility = android.view.View.VISIBLE
    }
}
