package io.krice.myapplication

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.webkit.WebView
import kotlinx.android.synthetic.main.activity_website.*

class WebsiteActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_website)

        toolbar.title = "OK"
        val webview = findViewById<WebView>(R.id.website_webview)
        webview.settings.javaScriptEnabled = true
        webview.loadUrl("https://www.igen.fr")
    }
}