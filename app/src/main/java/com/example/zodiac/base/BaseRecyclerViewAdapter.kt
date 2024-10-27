package com.example.zodiac.base

import androidx.viewbinding.ViewBinding
import com.example.core.adapters.CoreRecyclerViewAdapter

abstract class BaseRecyclerViewAdapter<T, V : ViewBinding> : CoreRecyclerViewAdapter<T, V>()