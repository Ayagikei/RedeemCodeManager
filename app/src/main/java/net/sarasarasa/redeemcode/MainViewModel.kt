package net.sarasarasa.redeemcode

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import net.sarasarasa.redeemcode.db.MainDataRepository


class MainViewModel(private val mainDataRepository: MainDataRepository) : ViewModel() {

    private val _codeState = MutableLiveData<MainCodeState>(
        MainCodeState(
            mainDataRepository.getCurrentCode(),
            mainDataRepository.getCurrentCountStatus()
        )
    )
    val codeState: LiveData<MainCodeState> = _codeState

    fun checkInit() {
        mainDataRepository.initData()
        getNextCode()
    }

    fun getPreviousCode() {
        _codeState.value = MainCodeState(
            mainDataRepository.getPreviousCode(),
            mainDataRepository.getCurrentCountStatus()
        )
    }

    fun getNextCode() {
        _codeState.value = MainCodeState(
            mainDataRepository.getNextCode(),
            mainDataRepository.getCurrentCountStatus()
        )
    }

    fun switchMarkStateOfCurrentCode() {
        _codeState.value = MainCodeState(
            mainDataRepository.switchMarkStateOfRedeemCode(codeState.value?.redeemCode),
            mainDataRepository.getCurrentCountStatus()
        )
    }
}