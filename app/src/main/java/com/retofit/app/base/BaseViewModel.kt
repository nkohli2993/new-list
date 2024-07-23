package com.retofit.app.base

import androidx.lifecycle.ViewModel
import com.retofit.app.errors.ErrorManager
import javax.inject.Inject

/**
 * Created by Nikita kohli
 */

abstract class BaseViewModel : ViewModel() {
    /**Inject Singleton ErrorManager
     * Use this errorManager to get the Errors
     */
    @Inject
    lateinit var errorManager: ErrorManager
}
