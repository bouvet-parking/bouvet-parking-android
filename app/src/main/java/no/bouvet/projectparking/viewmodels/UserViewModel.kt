package no.bouvet.projectparking.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import no.bouvet.projectparking.models.User

class UserViewModel(uid: String) : ViewModel(){

    lateinit var userLiveList : MutableLiveData<HashMap<String, User>>

    init{
        userLiveList = MutableLiveData()
        loadUser(uid)
    }

    private fun loadUser(uid : String){

    }


}