package cn.x.dailycost.ui.components

import android.Manifest
import android.content.ContentValues
import android.content.Context
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

/**
 * 封装好的系统相机拍照组件
 * @param modifier 布局修饰符
 * @param onPhotoCaptured 拍照成功后的回调，返回照片的 Uri
 */
@Composable
fun CameraCaptureComponent(
    modifier: Modifier = Modifier,
    onPhotoCaptured: (Uri) -> Unit = {}
) {
    val context = LocalContext.current
    var photoUri by remember { mutableStateOf<Uri?>(null) }
    var showPhoto by remember { mutableStateOf(false) }

    // 1. 拍照结果回调的启动器
    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture()
    ) { success ->
        if (success) {
            showPhoto = true
            photoUri?.let { onPhotoCaptured(it) }
        }
    }

    // 2. 权限申请的启动器
    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            // 权限通过，创建 Uri 并启动相机
            photoUri = createPrivateImageUri(context)
            cameraLauncher.launch(photoUri!!)
        }
    }

    // 触发拍照的主逻辑
    fun startCamera() {
        val hasPermission = ContextCompat.checkSelfPermission(
            context, Manifest.permission.CAMERA
        ) == PackageManager.PERMISSION_GRANTED

        if (hasPermission) {
            photoUri = createPrivateImageUri(context)
            cameraLauncher.launch(photoUri!!)
        } else {
            permissionLauncher.launch(Manifest.permission.CAMERA)
        }
    }

    Surface(
        modifier = modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        if (showPhoto && photoUri != null) {
            // 拍照成功，显示照片预览
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                val bitmap = remember(photoUri) {
                    context.contentResolver.openInputStream(photoUri!!)?.use {
                        android.graphics.BitmapFactory.decodeStream(it)
                    }
                }
                bitmap?.let {
                    Image(
                        bitmap = it.asImageBitmap(),
                        contentDescription = "Captured Photo",
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                            .aspectRatio(1f) // 保持正方形预览，可按需修改
                    )
                }
                Spacer(modifier = Modifier.height(20.dp))
                Button(onClick = {
                    // 重新拍照
                    showPhoto = false
                    photoUri = null
                    startCamera()
                }) {
                    Text("重新拍摄")
                }
            }
        } else {
            // 初始状态，显示拍照按钮
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Icon(
                    imageVector = Icons.Filled.CameraAlt,
                    contentDescription = null,
                    modifier = Modifier.clickable(onClick = { startCamera() })
                )
            }
        }
    }
}


fun createPrivateImageUri(context: Context): Uri {
    // 1. 获取 App 在外部的私有 Pictures 目录
    // 路径通常为：/storage/emulated/0/Android/data/你的包名/files/Pictures/
    val storageDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)

    // 2. 创建图片文件
    val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
    val imageFile = File(storageDir, "IMG_$timeStamp.jpg")

    // 3. 通过 FileProvider 将私有 File 转换为安全的 content:// Uri
    // 这个 Uri 就是你后续要存入数据库的值
    return FileProvider.getUriForFile(
        context,
        "${context.packageName}.fileprovider",
        imageFile
    )
}

fun createPicturesImageUri(context: Context): Uri? {
    val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
    val imageFileName = "IMG_$timeStamp.jpg"

    // 设置照片的元数据
    val contentValues = ContentValues().apply {
        put(MediaStore.Images.Media.DISPLAY_NAME, imageFileName)
        put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
        // 将照片存入公共的 Pictures 目录下（如果想存到相机胶卷，可改为 Environment.DIRECTORY_DCIM）
        put(MediaStore.Images.Media.RELATIVE_PATH, Environment.DIRECTORY_PICTURES)

    }

    val contentResolver = context.contentResolver
    // 在媒体库中插入记录并获取 Uri
    val imageUri = contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)
    return imageUri
}