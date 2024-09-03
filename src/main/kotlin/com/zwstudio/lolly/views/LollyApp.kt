package com.zwstudio.lolly.views

import io.reactivex.rxjava3.subjects.ReplaySubject
import javafx.scene.input.DataFormat
import javafx.stage.Stage
import org.hildan.fxgson.FxGson
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import tornadofx.*
import java.io.File
import java.security.GeneralSecurityException
import java.security.SecureRandom
import java.security.cert.X509Certificate
import javax.net.ssl.HttpsURLConnection
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager


class LollyApp: App(MainView::class, Styles::class) {

    companion object {
        lateinit var retrofitJson: Retrofit
        lateinit var retrofitSP: Retrofit
        lateinit var retrofitHtml: Retrofit
        val initializeObject = ReplaySubject.createWithSize<Unit>(1)
        val SERIALIZED_MIME_TYPE = DataFormat("application/x-java-serialized-object")
        val configFile = File("config.xml")
    }

    init {
        super.init()
        retrofitJson = Retrofit.Builder().baseUrl("https://zwvista.com/lolly/api.php/records/")
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(FxGson.create()))
            .build()
        retrofitSP = Retrofit.Builder().baseUrl("https://zwvista.com/lolly/sp.php/")
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        // https://futurestud.io/tutorials/retrofit-2-receive-plain-string-responses
        retrofitHtml = Retrofit.Builder().baseUrl("https://www.google.com")
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .addConverterFactory(ScalarsConverterFactory.create())
            .build()

        // https://stackoverflow.com/questions/22605701/javafx-webview-not-working-using-a-untrusted-ssl-certificate
        // Create a trust manager that does not validate certificate chains
        val trustAllCerts = arrayOf<TrustManager>(
            object : X509TrustManager {
                override fun checkClientTrusted(certs: Array<X509Certificate?>?, authType: String?) {}
                override fun checkServerTrusted(certs: Array<X509Certificate?>?, authType: String?) {}
                override fun getAcceptedIssuers() = null
            }
        )
        // Install the all-trusting trust manager
        try {
            val sc: SSLContext = SSLContext.getInstance("SSL")
            sc.init(null, trustAllCerts, SecureRandom())
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.socketFactory)
        } catch (e: GeneralSecurityException) {
        }
    }

    override fun start(stage: Stage) {
        super.start(stage)
        stage.isMaximized = true
    }
}

fun main(args: Array<String>) {
    launch<LollyApp>(args)
}
