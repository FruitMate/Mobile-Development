package com.capstone.tesfirebase.ui.main.scan

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.content.Intent.ACTION_GET_CONTENT
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.capstone.tesfirebase.databinding.FragmentScanBinding
import com.capstone.tesfirebase.ui.ViewModelFactory
import com.capstone.tesfirebase.ui.scanresult.ScanResultActivity
import com.capstone.tesfirebase.ui.camerax.CameraActivity
import com.capstone.tesfirebase.utils.rotateFile
import com.capstone.tesfirebase.utils.uriToFile
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream

class ScanFragment : Fragment() {

    private var _binding: FragmentScanBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private lateinit var scanViewModel: ScanViewModel
    private lateinit var auth: FirebaseAuth
    private var getFile: File? = null
    private var currentPhotoPath: String? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        scanViewModel = obtainViewModel()

        _binding = FragmentScanBinding.inflate(inflater, container, false)
        val root: View = binding.root

        // Restore the image preview if available
        currentPhotoPath = savedInstanceState?.getString(KEY_CURRENT_PHOTO_PATH)
        currentPhotoPath?.let { path ->
            val file = File(path)
            if (file.exists()) {
                binding.ivPreview.setImageBitmap(BitmapFactory.decodeFile(file.path))
            }
        }

        // Get email to send data to API
        auth = Firebase.auth
        val user = auth.currentUser

        binding.btnGallery.setOnClickListener { startGallery() }
        binding.btnCamera.setOnClickListener { startCameraX()}
        binding.btnUpload.setOnClickListener {
            binding.progressBar.visibility = View.VISIBLE
            binding.btnUpload.isEnabled = false
            binding.btnGallery.isEnabled = false
            binding.btnCamera.isEnabled = false
            if (getFile != null) {
                val file = reduceFileImage(getFile as File)
                val requestImageFile = file.asRequestBody("image/jpeg".toMediaTypeOrNull())
                val imageMultipart: MultipartBody.Part = MultipartBody.Part.createFormData(
                    "image",
                    file.name,
                    requestImageFile
                )
                val email = user?.email?.toRequestBody("text/plain".toMediaType())
                email?.let { emailRequestBody ->
                    scanViewModel.uploadImage(imageMultipart, emailRequestBody).observe(requireActivity()) {
                        if (it.code == 200) {
                            binding.progressBar.visibility = View.GONE
                            binding.btnUpload.isEnabled = true
                            binding.btnGallery.isEnabled = true
                            binding.btnCamera.isEnabled = true
                            Toast.makeText(requireActivity(), "${it.message}", Toast.LENGTH_SHORT).show()
                            val intent = Intent(requireActivity(), ScanResultActivity::class.java)
                            intent.putExtra("currentPhotoPath", currentPhotoPath)
                            intent.putExtra("prediction", it.prediction)
                            startActivity(intent)
                        } else {
                            binding.progressBar.visibility = View.GONE
                            binding.btnUpload.isEnabled = true
                            binding.btnGallery.isEnabled = true
                            binding.btnCamera.isEnabled = true
                            Toast.makeText(requireActivity(), "${it.message}", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(KEY_CURRENT_PHOTO_PATH, currentPhotoPath)
    }

    // CameraX Intent
    private val launcherIntentCameraX = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (it.resultCode == CAMERA_X_RESULT) {
            val myFile = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                it.data?.getSerializableExtra("picture", File::class.java)
            } else {
                @Suppress("DEPRECATION")
                it.data?.getSerializableExtra("picture")
            } as? File
            val isBackCamera = it.data?.getBooleanExtra("isBackCamera", true) as Boolean
            myFile?.let { file ->
                rotateFile(file, isBackCamera)
                currentPhotoPath = file.absolutePath
                getFile = file
                binding.ivPreview.setImageBitmap(BitmapFactory.decodeFile(currentPhotoPath))
            }
        }
    }
    private fun startCameraX() {
        val intent = Intent(requireActivity(), CameraActivity::class.java)
        launcherIntentCameraX.launch(intent)
    }

    // Gallery Intent
    private val launcherIntentGallery = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            val selectedImg = result.data?.data as Uri
            selectedImg.let { uri ->
                val myFile = uriToFile(uri, requireActivity())
                getFile = myFile
                currentPhotoPath = myFile.absolutePath
                binding.ivPreview.setImageBitmap(BitmapFactory.decodeFile(currentPhotoPath))
            }
        }
    }
    private fun startGallery() {
        val intent = Intent()
        intent.action = ACTION_GET_CONTENT
        intent.type = "image/*"
        val chooser = Intent.createChooser(intent, "Pilih gambar buah")
        launcherIntentGallery.launch(chooser)
    }

    // Reduce image size
    private fun reduceFileImage(file: File): File {
        val bitmap = BitmapFactory.decodeFile(file.path)
        var compressQuality = 100
        var streamLength: Int
        do {
            val bmpStream = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.JPEG, compressQuality, bmpStream)
            val bmpPicByteArray = bmpStream.toByteArray()
            streamLength = bmpPicByteArray.size
            compressQuality -= 5
        } while (streamLength > MAXIMAL_SIZE)
        bitmap.compress(Bitmap.CompressFormat.JPEG, compressQuality, FileOutputStream(file))
        return file
    }

    private fun obtainViewModel(): ScanViewModel {
        val factory = ViewModelFactory.getInstance(requireActivity())
        return ViewModelProvider(requireActivity(), factory)[ScanViewModel::class.java]
    }

    companion object {
        private const val MAXIMAL_SIZE = 1000000
        const val CAMERA_X_RESULT = 200
        private const val KEY_CURRENT_PHOTO_PATH = "current_photo_path"
    }
}