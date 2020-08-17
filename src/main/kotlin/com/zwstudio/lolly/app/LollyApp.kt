package com.zwstudio.lolly.app

import com.zwstudio.lolly.data.SettingsViewModel
import com.zwstudio.lolly.view.MainView
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.subjects.ReplaySubject
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import tornadofx.App

class LollyApp: App(MainView::class, Styles::class) {

    companion object {
        lateinit var retrofitJson: Retrofit
        lateinit var retrofitSP: Retrofit
        lateinit var retrofitHtml: Retrofit
        val compositeDisposable = CompositeDisposable()
        val initializeObject = ReplaySubject.createWithSize<Unit>(1)
    }

    val vm: SettingsViewModel by inject()

    init {
        retrofitJson = Retrofit.Builder().baseUrl("https://zwvista.tk/lolly/api.php/records/")
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        retrofitSP = Retrofit.Builder().baseUrl("https://zwvista.tk/lolly/sp.php/")
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        // https://futurestud.io/tutorials/retrofit-2-receive-plain-string-responses
        retrofitHtml = Retrofit.Builder().baseUrl("https://www.google.com")
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .addConverterFactory(ScalarsConverterFactory.create())
            .build()
        compositeDisposable.add(vm.getData().subscribe {
            initializeObject.onNext(Unit)
            initializeObject.onComplete()
        })
    }

}
