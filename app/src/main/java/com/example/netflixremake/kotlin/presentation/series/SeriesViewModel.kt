package com.example.netflixremake.kotlin.presentation.series

import MarvelApi
import androidx.lifecycle.ViewModel
import com.example.netflixremake.kotlin.data.model.Character
import io.reactivex.Observable

class SeriesViewModel() : ViewModel() {

    fun loadCharacters(characterURI: String): Observable<List<Character>> {
        return MarvelApi.getService().allCharactersBySeries(characterURI).map { character -> character.data.results }

    }
    //////////////////data//////////////
//    val loading= Observable(false)
//    val content = ObservableField<String>()
//    val title = ObservableField<String>()
    //////////////////binding//////////////
//
//        remote.getArticleDetail(8773)
//                .subscribeOn(Schedulers.io())
//                .delay(1000,TimeUnit.MILLISECONDS)
//                .observeOn(AndroidSchedulers.mainThread())
//                .doOnSubscribe { loading.set(true) }
//                .doAfterTerminate { loading.set(false) }
//                .subscribe({ t: Article? ->
//                    t?.let {
//                        title.set(it.title)
//                        it.content?.let {
//                            val articleContent=Utils.processImgSrc(it)
//                            content.set(articleContent)
//                        }
//                    }
//                }, { t: Throwable? ->  })
//    }

}