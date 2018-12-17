package io.krice.myapplication

data class News(var author: String?,
                var content: String?,
                var date: String?,
                var link: String?,
                var summary: String?,
                var picture: String?,
                var title: String?) {

    constructor() : this(null, null, null, null, null, null, null)

}