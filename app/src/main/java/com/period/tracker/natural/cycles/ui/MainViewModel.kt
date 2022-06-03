package com.period.tracker.natural.cycles.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseUser
import com.period.tracker.natural.cycles.domain.usecase.firebase.days.DownloadDaysFromFirebaseUseCase

class MainViewModel(
    private val downloadDaysFromFirebaseUseCase: DownloadDaysFromFirebaseUseCase
) : ViewModel() {


    private val _user = MutableLiveData<FirebaseUser?>(null)
    val user: LiveData<FirebaseUser?> = _user

    fun setUser(user: FirebaseUser?) {
        _user.postValue(user)
    }


}