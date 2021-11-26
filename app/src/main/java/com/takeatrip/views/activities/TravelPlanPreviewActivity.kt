package com.takeatrip.views.activities

import android.content.Intent
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.FileProvider
import androidx.core.net.toFile
import com.takeatrip.R
import com.takeatrip.utils.show
import kotlinx.android.synthetic.main.activity_travel_plan_preview.*
import kotlinx.android.synthetic.main.header.*
import java.io.File

class TravelPlanPreviewActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_travel_plan_preview)

        ivShare.show()

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                val file = intent.extras?.getSerializable("FILE") as File
                val uri = FileProvider.getUriForFile(
                    this,
                    "$packageName.fileProvider",
                    file
                )
                pdfView.fromUri(uri)
            } else {
                val uri = intent.extras?.getParcelable<Uri>("URI")
                pdfView.fromUri(uri)
            }


    }
}