package com.mirti.tv;

import android.os.Bundle;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.ui.PlayerView;

public class MainActivity extends AppCompatActivity {

    private WebView webView;
    private PlayerView playerView;
    private ExoPlayer player;
    
    // Resimler
    private static final String MAIN_SCREEN = "https://raw.githubusercontent.com/ipmirti68/-ptv-/refs/heads/main/IMG-20261017-WA0045.jpg";
    public static final String RADIO_BG = "https://raw.githubusercontent.com/ipmirti68/-ptv-/refs/heads/main/file_000000003b08820a96dea4b438a874d6.png";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        webView = findViewById(R.id.webView);
        playerView = findViewById(R.id.playerView);

        WebSettings ws = webView.getSettings();
        ws.setJavaScriptEnabled(true);
        ws.setDomStorageEnabled(true);
        ws.setAllowFileAccess(true);
        ws.setMediaPlaybackRequiresUserGesture(false);

        webView.setWebViewClient(new WebViewClient());
        webView.addJavascriptInterface(new MirtiAppInterface(), "MirtiApp");
        
        // Senin GitHub sayfanı yükle
        webView.loadUrl("https://ipmirti68.github.io/-p/");
    }

    public class MirtiAppInterface {
        
        @JavascriptInterface
        public void playVideo(String url, String title) {
            runOnUiThread(() -> {
                if (url == null) return;
                
                String lowerTitle = title != null ? title.toLowerCase() : "";
                String lowerUrl = url.toLowerCase();
                
                boolean isRadio = lowerTitle.contains("radyo") 
                        || lowerTitle.contains("radio")
                        || lowerUrl.contains("radio=true")
                        || lowerUrl.endsWith(".mp3")
                        || lowerUrl.contains("/radio/")
                        || lowerUrl.contains("radyo");

                if (isRadio) {
                    playRadio(url, title);
                } else {
                    playTv(url, title);
                }
            });
        }
    }

    private void playRadio(String url, String title) {
        // 1. WebView'de radyo resmini göster
        String safeTitle = title != null ? title.replace("'", "").replace("\"", "") : "Radyo";
        String js = "javascript:(function(){"
                + "var img=document.getElementById('idleImg'); if(img) img.src='" + RADIO_BG + "';"
                + "var idle=document.getElementById('idle'); if(idle) idle.style.display='flex';"
                + "var video=document.getElementById('video'); if(video){ video.pause(); video.style.display='none'; }"
                + "var rInfo=document.getElementById('radioInfo'); if(rInfo) rInfo.style.display='block';"
                + "var rTitle=document.getElementById('rTitle'); if(rTitle) rTitle.innerText='" + safeTitle + "';"
                + "var epg=document.getElementById('epgNow'); if(epg) epg.innerText='📻 '+ '" + safeTitle + "';"
                + "})()";
        webView.evaluateJavascript(js, null);

        // 2. ExoPlayer sadece ses çalsın, video view gizli kalsın
        if (player != null) {
            player.release();
        }
        playerView.setVisibility(View.GONE);
        
        player = new ExoPlayer.Builder(this).build();
        MediaItem item = MediaItem.fromUri(url);
        player.setMediaItem(item);
        player.prepare();
        player.setPlayWhenReady(true);
    }

    private void playTv(String url, String title) {
        // 1. WebView idle gizle
        webView.evaluateJavascript("javascript:(function(){var i=document.getElementById('idle'); if(i) i.style.display='none'; var v=document.getElementById('video'); if(v){ v.style.display='none'; }})()", null);
        
        // 2. ExoPlayer video aç
        if (player != null) {
            player.release();
        }
        playerView.setVisibility(View.VISIBLE);
        playerView.setPlayer(null);
        
        player = new ExoPlayer.Builder(this).build();
        playerView.setPlayer(player);
        
        MediaItem item = MediaItem.fromUri(url);
        player.setMediaItem(item);
        player.prepare();
        player.setPlayWhenReady(true);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (player != null) {
            player.release();
            player = null;
        }
        if (webView != null) {
            webView.destroy();
        }
    }

    @Override
    public void onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack();
        } else {
            super.onBackPressed();
        }
    }
}
