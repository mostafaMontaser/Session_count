package com.challenge.patagonian.dispatcher.session

import com.challenge.patagonian.repository.local.BaseLocalRepo
import com.challenge.patagonian.repository.remote.BaseRemoteRepo

class SessionDispatcherImpl(
    override val localRepo: BaseLocalRepo,
    override val remoteRepo: BaseRemoteRepo
) :
    SessionDispatcher

