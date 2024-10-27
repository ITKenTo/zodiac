package com.example.zodiac.extension

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Rect
import android.net.Uri
import android.os.Bundle
import android.os.Parcelable
import android.os.PowerManager
import android.provider.Settings
import android.util.Log
import android.util.TypedValue
import android.view.View
import android.view.Window
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.Toast
import androidx.annotation.DimenRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.IntentCompat
import androidx.core.os.BundleCompat
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.zodiac.BuildConfig
import com.example.zodiac.R
import dagger.hilt.android.EntryPointAccessors
import kotlinx.coroutines.launch
import kotlin.jvm.internal.Intrinsics
import kotlin.math.abs


fun Context.dpToPx(dp: Float) = TypedValue.applyDimension(
    TypedValue.COMPLEX_UNIT_DIP, dp, this.resources.displayMetrics
).toInt()

fun Context.toastMsg(msg: Int) =
    Toast.makeText(this, this.getString(msg), Toast.LENGTH_SHORT).show()

fun AppCompatActivity.showDialogFragment(dialogFragment: DialogFragment) {
    lifecycleScope.launchWhenResumed {
        dialogFragment.show(supportFragmentManager, dialogFragment::class.java.simpleName)
    }
}

fun Context.toastMsg(msg: String) = Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()

inline fun <reified T : Parcelable> Intent.getParcel(key: String): T? =
    IntentCompat.getParcelableExtra(this, key, T::class.java)

inline fun <reified T : Parcelable> Bundle.getParcel(key: String): T? =
    BundleCompat.getParcelable(this, key, T::class.java)

fun Context.linkAppStore(): Boolean {
    val uri: Uri = Uri.parse("market://details?id=${this.packageName}")
    val goToMarket = Intent(Intent.ACTION_VIEW, uri)
    goToMarket.addFlags(
        Intent.FLAG_ACTIVITY_NO_HISTORY or Intent.FLAG_ACTIVITY_NEW_DOCUMENT or Intent.FLAG_ACTIVITY_MULTIPLE_TASK
    )
    try {
        this.startActivity(goToMarket)
        return true
    } catch (e: ActivityNotFoundException) {
        try {
            this.startActivity(
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("http://play.google.com/store/apps/details?id=${this.packageName}")
                )
            )
            return true
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
    return false
}

inline fun <reified T> Context.appInject() =
    EntryPointAccessors.fromApplication(this, T::class.java)

fun AppCompatActivity.launchWhenResumedActivity(function: () -> Unit) {
    lifecycleScope.launch {
        lifecycle.repeatOnLifecycle(Lifecycle.State.RESUMED) {
            function.invoke()
        }
    }
}

fun Fragment.launchWhenResumed(function: () -> Unit) {
    lifecycleScope.launch {
        lifecycle.repeatOnLifecycle(Lifecycle.State.RESUMED) {
            function.invoke()
        }
    }
}

val Activity.statusBarHeight: Int
    get() {
        val rectangle = Rect()
        window.decorView.getWindowVisibleDisplayFrame(rectangle)

        val statusBarHeight: Int = rectangle.top
        val contentViewTop: Int = window.findViewById<View>(Window.ID_ANDROID_CONTENT).top
        return abs(contentViewTop - statusBarHeight)
    }

fun getStatusBarHeight(activity: AppCompatActivity): Int {
    val rectangle = Rect()
    val window: Window = activity.window
    window.decorView.getWindowVisibleDisplayFrame(rectangle)
    val statusBarHeight = rectangle.top
    val contentViewTop = window.findViewById<View>(Window.ID_ANDROID_CONTENT).top
    return abs(contentViewTop - statusBarHeight)
}

fun isDownloaded(id: Int, listStrId: String): Boolean {
    val listId = listStrId.replace(" ", "").split(",").toTypedArray()
    val index = listId.indexOf(id.toString())
    return index > -1
}

fun toastInFragment(context: Context, msg: Int) =
    Toast.makeText(context, context.getString(msg), Toast.LENGTH_SHORT).show()

fun Fragment.toast(msg: Int) = Toast.makeText(requireContext(), msg, Toast.LENGTH_SHORT).show()

fun Context.showSoftKeyboard2(v: EditText) {
    v.requestFocus()
    val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.showSoftInput(v, InputMethodManager.SHOW_IMPLICIT)
}

fun Context.hideKeyboard2(v: EditText) {
    val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(v.windowToken, 0)
}

@SuppressLint("StringFormatInvalid")
fun Context.shareApp() {
    try {
        val shareIntent = Intent(Intent.ACTION_SEND)
        shareIntent.type = "text/plain"
        val shareMessage = getString(
            R.string.description_share_app,
            getString(R.string.app_name),
            getString(R.string.description_share_app_link, BuildConfig.APPLICATION_ID)
        )
        shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage)
        startActivity(
            Intent.createChooser(
                shareIntent, getString(R.string.app_name)
            )
        )
    } catch (e: Exception) {
        e.printStackTrace()
    }
}

fun getPowerManager(context: Context): PowerManager {
    Intrinsics.checkNotNullParameter(context, "<this>")
    val systemService = context.getSystemService("power")
    Intrinsics.checkNotNull(
        systemService, "null cannot be cast to non-null type android.os.PowerManager"
    )
    return systemService as PowerManager
}

fun openSettings(context: Context) {
    try {
        val intent = Intent()
        intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
        intent.addCategory(Intent.CATEGORY_DEFAULT)
        intent.data = Uri.parse("package:" + context.packageName)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY)
        intent.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS)
        context.startActivity(intent)
    } catch (e: Exception) {
        Log.d("naoh", "openSettings: error")
        e.printStackTrace()
    }
}

fun Context.openWriteSetting() {
    val intent = Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS)
    intent.data = Uri.parse("package:" + this.packageName)
    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
    try {
        startActivity(intent)
    } catch (e: Exception) {
        toastMsg(R.string.error_common)
    }
}

fun Context.dimenInt(@DimenRes dimen: Int) = resources.getDimensionPixelSize(dimen)

fun Context.dimenFloat(@DimenRes dimen: Int) = resources.getDimension(dimen)

const val PERMISSIONS_REQUEST_READ_CONTACTS = 100

fun Context.isHaveContactPermission(): Boolean {
    return (ContextCompat.checkSelfPermission(
        this, android.Manifest.permission.READ_CONTACTS
    ) == PackageManager.PERMISSION_GRANTED)
}

fun Activity.requestPermissionsContact() {
    ActivityCompat.requestPermissions(
        this, arrayOf(Manifest.permission.READ_CONTACTS), PERMISSIONS_REQUEST_READ_CONTACTS
    )
}

fun openSettingsApp(context: Context) {
    try {
        val intent = Intent()
        intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
        intent.addCategory(Intent.CATEGORY_DEFAULT)
        intent.data = Uri.parse("package:" + context.packageName)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY)
        intent.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS)
        context.startActivity(intent)
    } catch (e: Exception) {
        Log.d("naoh", "openSettings: error")
        e.printStackTrace()
    }
}
