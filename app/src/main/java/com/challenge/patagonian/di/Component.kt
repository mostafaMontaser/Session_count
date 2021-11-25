package com.challenge.patagonian.di

val appComponents = listOf(
    localRepoModule,
    remoteRepoModule,
    session
)
val networkComponent = listOf(networkModule)