@JavascriptInterface
fun playVideo(url: String, title: String) {
    runOnUiThread {
        val mediaItem = MediaItem.fromUri(Uri.parse(url))
        exoPlayer.setMediaItem(mediaItem)
        exoPlayer.prepare()
        exoPlayer.play()
        playerView.visibility = View.VISIBLE
        // title'ı notification'da göster
    }
}
