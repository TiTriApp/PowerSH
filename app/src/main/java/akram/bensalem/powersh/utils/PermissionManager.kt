package akram.bensalem.powersh.utils

import android.Manifest
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import kotlinx.coroutines.*
import timber.log.Timber

class PermissionManager(private val activity: AppCompatActivity) {

    private val errorHandler = CoroutineExceptionHandler { _, throwable ->
        Timber.e("$throwable")
    }

    private val scope = CoroutineScope(Job() + Dispatchers.IO + errorHandler)

    fun requestStoragePermission(onResult: (Boolean) -> Unit) {
        requestPermission(
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Permissions.STORAGE.code,
            onResult
        )
    }

    fun requestPermission(permission: String, permissionCode: Int, onResult: (Boolean) -> Unit) {
        if (
            ContextCompat.checkSelfPermission(activity, permission) == PackageManager.PERMISSION_DENIED
        ) {
            val permissions = arrayOf(permission)

            /*scope.launch {
                activity.requestPermissions(permissions, permissionCode)

                LiveEventBus.listen<EventPermissionResult>().collect {
                    onResult.invoke(it.isGranted)
                }
            }*/
        } else {
            onResult.invoke(true)
        }
    }


    enum class Permissions(val code: Int) {
        STORAGE(1001)
    }
}