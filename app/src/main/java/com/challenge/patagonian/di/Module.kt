package com.challenge.patagonian.di


import com.challenge.patagonian.dispatcher.session.SessionDispatcher
import com.challenge.patagonian.dispatcher.session.SessionDispatcherImpl
import com.challenge.patagonian.repository.local.BaseLocalRepo
import com.challenge.patagonian.repository.local.BaseLocalRepolmpl
import com.challenge.patagonian.model.retrofit.Service
import com.challenge.patagonian.repository.remote.BaseRemoteRepo
import com.challenge.patagonian.repository.remote.BaseRemoteRepoImpl
import com.challenge.patagonian.viewmodel.session.SessionViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module



val localRepoModule = module {
    single<BaseLocalRepo> { BaseLocalRepolmpl() }
}

val remoteRepoModule = module {
    single<BaseRemoteRepo> { BaseRemoteRepoImpl() }
}
val networkModule = module {
    single { Service.getService() }
}

val session = module {
    viewModel { SessionViewModel(get()) }
    single<SessionDispatcher> { SessionDispatcherImpl(get(), get()) }
}