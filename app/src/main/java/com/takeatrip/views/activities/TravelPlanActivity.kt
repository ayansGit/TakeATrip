package com.takeatrip.views.activities

import android.Manifest
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.takeatrip.R
import kotlinx.android.synthetic.main.activity_travel_plan.*
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import com.graphicalab.utils.BaseActivity
import java.util.*
import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.content.ContentValues
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import android.widget.ArrayAdapter
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.itextpdf.io.image.ImageData
import com.itextpdf.io.image.ImageDataFactory
import com.itextpdf.kernel.pdf.PdfDocument
import com.itextpdf.kernel.pdf.PdfWriter
import com.itextpdf.kernel.pdf.canvas.draw.SolidLine
import com.itextpdf.layout.Document
import com.itextpdf.layout.element.Image
import com.itextpdf.layout.element.LineSeparator
import com.itextpdf.layout.element.Paragraph
import com.itextpdf.layout.property.HorizontalAlignment
import com.itextpdf.layout.property.TextAlignment
import com.takeatrip.adapters.AddHotelRoomAdapter
import com.takeatrip.models.location.LocationData
import com.takeatrip.models.meal.MealData
import com.takeatrip.models.room.RoomData
import com.takeatrip.utils.hide
import com.takeatrip.utils.show
import com.takeatrip.viewModels.TravelPlanViewModel
import kotlinx.android.synthetic.main.activity_travel_plan.daySpinner
import kotlinx.android.synthetic.main.activity_travel_plan.locationSpinner
import kotlinx.coroutines.*
import pub.devrel.easypermissions.AfterPermissionGranted
import pub.devrel.easypermissions.EasyPermissions
import java.io.*
import java.text.SimpleDateFormat
import java.util.Locale
import kotlin.collections.HashMap
import kotlin.coroutines.CoroutineContext
import androidx.core.content.FileProvider
import com.itextpdf.kernel.font.PdfFontFactory

import java.io.File
import android.graphics.fonts.Font
import android.view.View
import android.widget.AdapterView
import com.itextpdf.layout.element.List
import com.takeatrip.models.hotel.Hotel
import kotlinx.android.synthetic.main.activity_add_hotel.*
import kotlinx.android.synthetic.main.activity_travel_plan.btSubmit


class TravelPlanActivity : BaseActivity(), AddHotelRoomAdapter.SelectedRoomListener,
    EasyPermissions.PermissionCallbacks,
    CoroutineScope {

    val daysList = ArrayList<String>()
    val roomList = ArrayList<RoomData>()
    val mealList = ArrayList<MealData>()
    val hotelList = ArrayList<Hotel>()
    private val locationList = ArrayList<LocationData>()
    lateinit var dayAdapter: ArrayAdapter<String>
    lateinit var hotelAdapter: ArrayAdapter<Hotel>
    private lateinit var ad: ArrayAdapter<*>
    private var locationId: String = ""
    var startDate: String = ""
    var endDate: String = ""
    private var path: String = ""
    private var pdfFile: File? = null
    private var fileName: String = ""
    private var pathUri: Uri? = null
    private lateinit var travelPlanViewModel: TravelPlanViewModel
    private lateinit var addHotelRoomAdapter: AddHotelRoomAdapter


    companion object {
        const val STORAGE_PERM = 100
        const val FILE_PATH = "filePath"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_travel_plan)

        travelPlanViewModel = ViewModelProvider(this).get(TravelPlanViewModel::class.java)
        dayAdapter = ArrayAdapter<String>(
            this,
            android.R.layout.simple_spinner_item,
            daysList
        )
        dayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        daySpinner.adapter = dayAdapter

        ad = ArrayAdapter<LocationData>(
            this,
            android.R.layout.simple_spinner_item,
            locationList
        )
        ad.setDropDownViewResource(
            android.R.layout.simple_spinner_dropdown_item
        )
        locationSpinner.adapter = ad
        locationSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                locationId = locationList[p2].locationId
                travelPlanViewModel.getHotelByLocation(locationId)
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {

            }
        }

        hotelAdapter = ArrayAdapter<Hotel>(
            this,
            android.R.layout.simple_spinner_item,
            hotelList
        )
        hotelAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        hotelSpinner.adapter = hotelAdapter

        etStartDate.setOnClickListener {
            showCalendar(etStartDate, (System.currentTimeMillis() - 1000)) {
                val myFormat = "dd/MM/yyyy" //In which you need put here
                val sdf = SimpleDateFormat(myFormat, Locale.getDefault())
                startDate = sdf.format(it)

                etEndDate.setText("")
                daySpinner.hide()
                tvSelectDay.hide()
                margin2.hide()
                tvSelectHotel.hide()
                hotelSpinner.hide()
                llHotel.hide()
                margin3.hide()
                llTransport.hide()

            }
        }

        etEndDate.setOnClickListener {
            if (etStartDate.text.toString().isEmpty()) {
                showToast("Enter start date")
            } else {
                val startDate = SimpleDateFormat(
                    "dd/MM/yyyy",
                    Locale.getDefault()
                ).parse(etStartDate.text.toString())
                val calendar = Calendar.getInstance()
                calendar.time = startDate
                calendar.add(Calendar.DATE, 1)
                showCalendar(etEndDate, calendar.timeInMillis) {
                    val myFormat = "dd/MM/yyyy" //In which you need put here
                    val sdf = SimpleDateFormat(myFormat, Locale.getDefault())
                    endDate = sdf.format(it)

                    val startDate = sdf.parse(this.startDate)

                    val diff = it.time - startDate.time
                    val dayCount = diff.toFloat() / (24 * 60 * 60 * 1000) + 1f
                    for (i in 1..dayCount.toInt()) {
                        daysList.add(i.toString())
                    }
                    daySpinner.show()
                    tvSelectDay.show()
                    margin2.show()
                    tvSelectHotel.show()
                    hotelSpinner.show()
                    llHotel.show()
                    margin3.show()
                    llTransport.show()
                    dayAdapter.notifyDataSetChanged()
                    showToast(dayCount.toInt().toString())
                }
            }
        }

        rvRooms.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        addHotelRoomAdapter = AddHotelRoomAdapter(this, roomList, mealList, this)
        rvRooms.adapter = addHotelRoomAdapter


        observeLocationList()
        observeHotelList()
        observeMealList()
        observeLoader()
        observeToast()

        travelPlanViewModel.getLocation()
        travelPlanViewModel.getMeal()

        btSubmit.setOnClickListener {
            submit()
        }
    }

    @AfterPermissionGranted(STORAGE_PERM)
    private fun submit() {
        val perms = arrayOf<String>(
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE
        )
        if (EasyPermissions.hasPermissions(this, *perms)) {

            launch {
                showProgress()
                createPdf()
                hideProgress()

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    val uri = FileProvider.getUriForFile(
                        this@TravelPlanActivity,
                        "$packageName.fileProvider",
                        pdfFile!!
                    )
                    intent = Intent(Intent.ACTION_VIEW)
                    intent.data = uri
                    intent.flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
                    startActivity(intent)
                } else {
                    intent = Intent(Intent.ACTION_VIEW)
                    intent.setDataAndType(pathUri, "application/pdf")
                    intent = Intent.createChooser(intent, "Open File")
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    startActivity(intent)
                }

                /*val intent = Intent(this@TravelPlanActivity, TravelPlanPreviewActivity::class.java)
                intent.putExtra("URI", pathUri)
                intent.putExtra("FILE", pdfFile)
                startActivity(intent)*/
            }
        } else {
            EasyPermissions.requestPermissions(
                this,
                "This app needs access to your storage",
                STORAGE_PERM,
                *perms
            )
        }
    }


    private fun showCalendar(inputField: EditText, minDate: Long, onDateSet: (date: Date) -> Unit) {
        val myCalendar = Calendar.getInstance();
        val date =
            OnDateSetListener { view, year, monthOfYear, dayOfMonth -> // TODO Auto-generated method stub
                myCalendar[Calendar.YEAR] = year
                myCalendar[Calendar.MONTH] = monthOfYear
                myCalendar[Calendar.DAY_OF_MONTH] = dayOfMonth
                val myFormat = "dd/MM/yyyy" //In which you need put here
                val sdf = SimpleDateFormat(myFormat, Locale.getDefault())
                inputField.setText(sdf.format(myCalendar.time))
                onDateSet(myCalendar.time)
            }

        val dpDialog = DatePickerDialog(
            this, date, myCalendar[Calendar.YEAR],
            myCalendar[Calendar.MONTH],
            myCalendar[Calendar.DAY_OF_MONTH]
        )

        dpDialog.datePicker.minDate = minDate

        dpDialog.show()
    }

    override fun onMealSelected(mealMap: HashMap<String, MealData>) {
    }

    private fun observeLocationList() {
        travelPlanViewModel.observeGetLocation().observe(this, {
            locationList.clear()
            locationList.addAll(it)
            if (locationList.size > 0)
                locationId = locationList[0].locationId
            ad.notifyDataSetChanged()
        })
    }

    private fun observeHotelList() {
        travelPlanViewModel.observeHotel().observe(this, {
            hotelList.clear()
            hotelList.addAll(it)
            hotelAdapter.notifyDataSetChanged()
        })
    }

    private fun observeMealList() {
        travelPlanViewModel.observeGetMeal().observe(this, {
            mealList.clear()
            mealList.addAll(it)
            addHotelRoomAdapter.notifyDataSetChanged()
        })
    }

    private fun observeLoader() {
        travelPlanViewModel.dataLoading.observe(this, {
            if (it)
                showProgress()
            else hideProgress()
        })
    }

    private fun observeToast() {
        travelPlanViewModel.toastMessage.observe(this, {
            showToast(it)
        })
    }


    private suspend fun createPdf() {

        withContext(Dispatchers.IO) {
            val pdfFilePath = createTempFile(
                "TourPlan${
                    SimpleDateFormat("dd_MM_yyyy_HH_mm_ss", Locale.getDefault()).format(
                        Date()
                    )
                }", ".pdf", getExternalFilesDir(
                    Environment.DIRECTORY_DOCUMENTS
                )
            )
            pdfFile = pdfFilePath
            pathUri = Uri.fromFile(pdfFilePath)
            fileName = pdfFilePath.name

            try {

                val pdf = PdfDocument(PdfWriter(FileOutputStream(pdfFile)))
                val document = Document(pdf)
                val line = Paragraph(" ")
                // get input stream
                val ims: InputStream = assets.open("take_a_trip.png")
                val bmp: Bitmap = BitmapFactory.decodeStream(ims)
                val stream = ByteArrayOutputStream()
                bmp.compress(Bitmap.CompressFormat.PNG, 80, stream)
                val imageData: ImageData = ImageDataFactory.create(stream.toByteArray())
                val image: Image = Image(imageData).setHeight(100f)
                document.add(image.setHorizontalAlignment(HorizontalAlignment.CENTER))
                //document.add(pageTitle.setFont(subFont).setFontSize(24f))
//                document.add(LineSeparator(SolidLine(1f)).setMarginTop(4f))
                val header = Paragraph(
                    "Dear Sir, " + "\n" +
                            "Greetings from Take A Trip...!!!" + "\n" +
                            "Please find the below the Suggested itinerary along with the Hotels and the Cost"
                )

                header.setTextAlignment(TextAlignment.LEFT)
                document.add(header)
                document.add(line)
                val dateOfTravel = Paragraph("Date of travel: " + etStartDate.text)
                dateOfTravel.setTextAlignment(TextAlignment.LEFT)
                document.add(dateOfTravel)
                document.add(line)

                val title =
                    Paragraph("PORT BLAIR 3NT + HAVELOCK 1NT + NEIL ISLAND 1NT").setFontSize(16f)
                title.setTextAlignment(TextAlignment.CENTER)
                document.add(title)
                document.add(line)

                for (i in 1..4) {
                    val paragraph = Paragraph("Day$i").setFontSize(15f)
                    val list = List()
                    list.add("Post breakfast excursion trip to Netaji Subhash Chandra Bose Island (Ross Island), It was the Capital of Port Blair during British and Japanese regime, prior to India’s Independence. Later we will visit North Bay, It is also called “The Gate Way to Port Blair”. Overnight in Port Blair. ")
                        .setFontSize(14f)
                    document.add(paragraph)
                    document.add(list)
                    document.add(line)
                }

                val hotel = Paragraph("Hotels Used: ").setFontSize(16f)
                document.add(hotel)

                for (i in 1..4) {
                    val list = List()
                    list.add("Hotel $i(Double Bedroom)-MAP").setFontSize(14f)
                    document.add(list)
                }
                document.add(line)

                val price = Paragraph("Total Cost: Rs.36, 200/- ").setFontSize(16f)
                document.add(price)
                val text2 =
                    Paragraph("The above cost is Nett and Non Commissionable.").setFontSize(12f)
                document.add(text2)

                document.add(line)
                document.add(line)

                val footer =
                    Paragraph("With regards,\nAditiya\nMobile No: 8961006370\nAddress: 15B AJC Bose Road, Kolkata: 700097").setFontSize(
                        14f
                    )
                document.add(footer)
                document.close()
            } catch (ex: Exception) {
                ex.printStackTrace()
            }
        }

    }

    private fun saveFile() {

        /*if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q){
            val contentValues = ContentValues().apply {
                put(MediaStore.MediaColumns.DISPLAY_NAME, fileName)
                put(MediaStore.MediaColumns.MIME_TYPE, ".pdf")
                put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_DOWNLOADS)
            }
            val resolver = contentResolver
            val uri = resolver.insert(MediaStore.Downloads.EXTERNAL_CONTENT_URI, contentValues)

        }else{

        }*/
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    override fun onPermissionsGranted(requestCode: Int, perms: MutableList<String>) {
        launch {

            showProgress()
            createPdf()
            hideProgress()

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                val uri = FileProvider.getUriForFile(
                    this@TravelPlanActivity,
                    "$packageName.fileProvider",
                    pdfFile!!
                )
                intent = Intent(Intent.ACTION_VIEW)
                intent.data = uri
                intent.flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
                startActivity(intent)
            } else {
                intent = Intent(Intent.ACTION_VIEW)
                intent.setDataAndType(pathUri, "application/pdf")
                intent = Intent.createChooser(intent, "Open File")
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
            }
        }
    }

    override fun onPermissionsDenied(requestCode: Int, perms: MutableList<String>) {

    }

    private val job = Job()

    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.Main
}