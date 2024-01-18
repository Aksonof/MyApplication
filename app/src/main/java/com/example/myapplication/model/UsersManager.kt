package com.example.myapplication.model

import com.github.javafaker.Faker

class UsersManager {

    private var users = mutableListOf<User>()

    init {
        val faker = Faker.instance()
        val myRandomUsers = (1..50).map {
            User(
                name = faker.funnyName().name(),
                career = faker.job().title(),
                photo = "...",
                id = it.toLong()
            )
        }
    }




}