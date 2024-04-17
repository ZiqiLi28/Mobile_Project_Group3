package com.example.accounting.db

class AccountBean {
    var id: Int = 0
    var typename: String? = null
    var sImageId: Int = 0
    var note: String? = null
    var money: Float = 0.toFloat()
    var time: String? = null
    var year: Int = 0
    var month: Int = 0
    var day: Int = 0
    var kind: Int = 0

    constructor()

    constructor(id: Int, typename: String?, sImageId: Int, note: String?, money: Float,
                time: String?, year: Int, month: Int, day: Int, kind: Int) {
        this.id = id
        this.typename = typename
        this.sImageId = sImageId
        this.note = note
        this.money = money
        this.time = time
        this.year = year
        this.month = month
        this.day = day
        this.kind = kind
    }
}
