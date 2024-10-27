package com.example.zodiac.base

import androidx.viewbinding.ViewBinding
import com.example.core.dialogs.CoreDialogFragment

abstract class BaseDialogFragment<T : ViewBinding> : CoreDialogFragment<T>()