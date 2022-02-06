package com.emanuel.recyclerviewkotlin.model

data class Email(
        val user: String,
        val subject: String,
        val preview: String,
        val date: String,
        val stared: Boolean,
        val unread: Boolean,
        var selected: Boolean = false
)

class EmailBuilder {
    var user: String = ""
    var subject: String = ""
    var preview: String = ""
    var date: String =  ""
    var stared: Boolean = false
    var unread: Boolean = false

    fun build() : Email = Email(user, subject, preview, date, stared, unread, false)
}

fun email(block: EmailBuilder.() -> Unit): Email = EmailBuilder().apply(block).build()

fun fakeEmails() = mutableListOf(
        email {
            user = "Facebook"
            subject = "Veja nossas três dicas principais para você conseguir"
            preview = "Olá Emanuel, você precisa ver esse site para"
            date = "20 nov"
            stared = false
            unread = true
        },
        email {
            user = "Facebook"
            subject = "Um amigo quer que você curta uma página"
            preview = "Fulano convidou você para curtir a sua página"
            date = "17 nov"
            stared = false
            unread = true
        },
        email {
            user = "Youtube"
            subject = "Emanuel acabou de enviar um vídeo"
            preview = "Emanuel enviou um vídeo Aprendendo Android com Kotlin"
            date = "30 out"
            stared = true
            unread = true
        },
        email {
            user = "Instagram"
            subject = "Veja novas postagens de quem você segue"
            preview = "Veja aqui as pessoas andam postando..."
            date = "10 nov"
            stared = true
        },
        email {
            user = "Facebook"
            subject = "Veja nossas três dicas principais para você conseguir"
            preview = "Olá Emanuel, você precisa ver esse site para"
            date = "20 nov"
            stared = false
        },
        email {
            user = "Youtube"
            subject = "Emanuel acabou de enviar um vídeo"
            preview = "Emanuel enviou um vídeo Aprendendo Android com Kotlin"
            date = "30 out"
            stared = true
            unread = true
        },
        email {
            user = "Facebook"
            subject = "Veja nossas três dicas principais para você conseguir"
            preview = "Olá Emanuel, você precisa ver esse site para"
            date = "20 nov"
            stared = false
        },
        email {
            user = "LinkedIn"
            subject = "Veja novas postagens de quem você segue"
            preview = "Veja aqui as pessoas andam postando..."
            date = "12 nov"
            stared = false
        },
        email {
            user = "Kotlin"
            subject = "Veja novas postagens de quem você segue"
            preview = "Veja aqui as pessoas andam postando..."
            date = "10 nov"
            stared = true
            unread = true
        },
        email {
            user = "Android Developers"
            subject = "Veja novas postagens de quem você segue"
            preview = "Veja aqui as pessoas andam postando..."
            date = "20 nov"
            stared = true
        },
)
