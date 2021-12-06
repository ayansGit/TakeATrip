package com.takeatrip.views.activities

import android.Manifest
import android.app.Activity
import android.os.Bundle
import com.takeatrip.R
import kotlinx.android.synthetic.main.activity_travel_plan.*
import android.widget.EditText
import com.graphicalab.utils.BaseActivity
import java.util.*
import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.util.Log
import android.widget.ArrayAdapter
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.itextpdf.io.image.ImageData
import com.itextpdf.io.image.ImageDataFactory
import com.itextpdf.kernel.pdf.PdfDocument
import com.itextpdf.kernel.pdf.PdfWriter
import com.itextpdf.layout.Document
import com.itextpdf.layout.element.Image
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
import kotlinx.android.synthetic.main.activity_travel_plan.nightSpinner
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

import java.io.File
import android.view.View
import android.widget.AdapterView
import com.itextpdf.layout.element.List
import com.takeatrip.models.hotel.Hotel
import com.takeatrip.models.transport.Transport
import com.takeatrip.models.transport.TransportLocation
import com.takeatrip.utils.StoragePreference
import kotlinx.android.synthetic.main.activity_travel_plan.btSubmit
import kotlinx.android.synthetic.main.activity_vehicle.*
import kotlinx.android.synthetic.main.header.*
import kotlin.collections.ArrayList


class TravelPlanActivity : BaseActivity(), AddHotelRoomAdapter.SelectedRoomListener,
    EasyPermissions.PermissionCallbacks,
    CoroutineScope {

    val nightList = ArrayList<String>()
    val dayList = ArrayList<String>()
    val roomList = ArrayList<RoomData>()
    val allRoomList = ArrayList<RoomData>()
    val mealList = ArrayList<MealData>()
    val transportLocationList = ArrayList<TransportLocation>()
    val hotelList = ArrayList<Hotel>()
    private val locationList = ArrayList<LocationData>()
    lateinit var nightAdapter: ArrayAdapter<String>
    lateinit var dayAdapter: ArrayAdapter<String>
    lateinit var hotelAdapter: ArrayAdapter<Hotel>
    private lateinit var ad: ArrayAdapter<*>
    private var locationId: String = ""
    private var hotelId: String = ""
    var startDate: String = ""
    var endDate: String = ""
    private var path: String = ""
    private var pdfFile: File? = null
    private var fileName: String = ""
    private var pathUri: Uri? = null
    private lateinit var travelPlanViewModel: TravelPlanViewModel
    private lateinit var addHotelRoomAdapter: AddHotelRoomAdapter

    private var selectedNight = "1"
    private var selectedDay = "1"
    private var locationName = ""

    private var hotelPriceMap: TreeMap<String, TreeMap<String, MealData>> = TreeMap()
    private var withMattressPriceMap: TreeMap<String, TreeMap<String, MealData>> = TreeMap()
    private var withoutMattressPriceMap: TreeMap<String, TreeMap<String, MealData>> = TreeMap()
//    private val hotelPriceMap: HashMap<String, ArrayList<MealData>> = HashMap()
    private val hotelMap: TreeMap<String, String> = TreeMap()
    private val transportPriceMap: HashMap<String, String> = HashMap()
    private val roomPriceMap: TreeMap<String, MealData> = TreeMap()
    private val roomWithMattressPriceMap: TreeMap<String, MealData> = TreeMap()
    private val roomWithoutMattressPriceMap: TreeMap<String, MealData> = TreeMap()
    private val selMealList: ArrayList<MealData> = ArrayList()
//    private val selHotelList: ArrayList<ArrayList<MealData>> = ArrayList()


    companion object {
        const val STORAGE_PERM = 100
        const val FILE_PATH = "filePath"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_travel_plan)

        travelPlanViewModel = ViewModelProvider(this).get(TravelPlanViewModel::class.java)
        nightAdapter = ArrayAdapter<String>(
            this,
            android.R.layout.simple_spinner_item,
            nightList
        )
        nightAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        nightSpinner.adapter = nightAdapter
        nightSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                selectedNight = nightList[p2]
                hotelSpinner.setSelection(0)
                roomPriceMap.clear()
                selMealList.clear()
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {

            }
        }

        dayAdapter = ArrayAdapter<String>(
            this,
            android.R.layout.simple_spinner_item,
            dayList
        )
        dayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        daySpinner.adapter = dayAdapter
        daySpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                val transport = getTransportDetail(dayList[p2])
                if (transport != null) {
                    transport.also {
                        transportDetail.text = it.description
                        tvTransportCost.text = it.transportPrice
                        tvMiscellaneousCost.text = it.ticketPrice
                    }
                } else {
                    transportDetail.text = ""
                    tvTransportCost.text = ""
                    tvMiscellaneousCost.text = ""
                    showToast("No travel plan is there for day ${dayList[p2]}")
                }


            }

            override fun onNothingSelected(p0: AdapterView<*>?) {

            }
        }

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
                locationName = locationList[p2].name
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
        hotelSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                hotelId = hotelList[p2].hotelId
                if (hotelList[p2].id == 0) {
                    llHotel.hide()
                    roomList.clear()
                    addHotelRoomAdapter.notifyDataSetChanged()
                } else {
                    llHotel.show()
                    rating.rating = hotelList[p2].rating.toFloat()
                    tvDescription.text = hotelList[p2].address
                    hotelMap["night$selectedNight" ]= hotelList[p2].name
                }

            }

            override fun onNothingSelected(p0: AdapterView<*>?) {

            }
        }

        etStartDate.setOnClickListener {
            showCalendar(etStartDate, (System.currentTimeMillis() - 1000)) {
                val myFormat = "dd/MM/yyyy" //In which you need put here
                val sdf = SimpleDateFormat(myFormat, Locale.getDefault())
                startDate = sdf.format(it)

                etEndDate.setText("")
                nightSpinner.hide()
                tvNoOfNights.hide()
                margin2.hide()
                tvSelectHotel.hide()
                hotelSpinner.hide()
                margin3.hide()
                llTransport.hide()
                btSubmit.hide()

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

                    nightList.clear()
                    dayList.clear()
                    for (i in 1..dayCount.toInt()) {
                        dayList.add(i.toString())
                    }
                    for (i in 1 until dayCount.toInt()) {
                        nightList.add(i.toString())
                    }
                    nightSpinner.show()
                    tvNoOfNights.show()
                    margin2.show()
                    tvSelectHotel.show()
                    hotelSpinner.show()
                    margin3.show()
                    llTransport.show()
                    btSubmit.show()
                    nightAdapter.notifyDataSetChanged()
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
        observeGetTransport()
        observeLoader()
        observeToast()

        travelPlanViewModel.getLocation()
        travelPlanViewModel.getTransport()

        btSubmit.setOnClickListener {
            submit()
        }
        ivBack.setOnClickListener {
            onBackPressed()
        }

        btAddMore.setOnClickListener {
            val intent = Intent(this, SelectRoomActivity::class.java)
            intent.putExtra("HOTEL_ID", hotelId)
            startActivityForResult(intent, 1)
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
                var totalPrice = 0
                for ((k, value) in hotelPriceMap) {
                    for ((i,v) in value) {
                        totalPrice += v.price.toInt()
                    }
                    val withMattress = withMattressPriceMap[k]
                    if(withMattress!=null){
                        for ((i,v) in withMattress) {
                            totalPrice += v.price.toInt()
                        }
                    }
                    val withoutMattress = withoutMattressPriceMap[k]
                    if(withoutMattress!=null){
                        for ((i,v) in withoutMattress) {
                            totalPrice += v.price.toInt()
                        }
                    }
                }

                getTransportList()?.also {

                    val noOfPeople = if(etNoOfPeople.text.toString().isNotEmpty())
                        etNoOfPeople.text.toString().toInt()
                    else 1

                    val noOfTransport = if(etNoOfTransport.text.toString().isNotEmpty())
                        etNoOfTransport.text.toString().toInt()
                    else 1

                    if (it.size > dayList.size) {
                        for (i in 0 until dayList.size) {
                            totalPrice += it[i].transportPrice.toInt() * noOfTransport
                            totalPrice += it[i].ticketPrice.toInt() * noOfPeople
                        }
                    } else {
                        for (i in 0 until it.size) {
                            totalPrice += it[i].transportPrice.toInt() * noOfTransport
                            totalPrice += it[i].ticketPrice.toInt() * noOfPeople
                        }
                    }
                }

                Log.d("Total Price: ", totalPrice.toString())
                createPdf(totalPrice)
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

                StoragePreference.saveHotelPriceMap(this@TravelPlanActivity, TreeMap())
                StoragePreference.saveWithMattressPriceMap(this@TravelPlanActivity, TreeMap())
                StoragePreference.saveWithoutMattressPriceMap(this@TravelPlanActivity, TreeMap())

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

    private fun getTransportDetail(selectedDay: String): Transport? {
        try {
            return transportLocationList.first { it.locationId == this.locationId }
                .transports.first { it.day.toString() == selectedDay }
        } catch (exception: Exception) {
            exception.printStackTrace()
        }
        return null
    }

    private fun getTransportList(): ArrayList<Transport>? {
        try {
            return transportLocationList.first { it.locationId == this.locationId }.transports
        } catch (exception: Exception) {
            exception.printStackTrace()
        }
        return null
    }

    override fun onMealSelected(roomCount: Int, mealData: MealData) {

    }

    override fun onMealSelected(roomId: String, mealData: MealData) {
        val preHotelPriceMap = StoragePreference.getHotelPriceMap(this)

        roomPriceMap[roomId] = mealData
        val newHotelPriceMap = TreeMap<String, TreeMap<String, MealData>>()
        newHotelPriceMap["night$selectedNight"] = roomPriceMap

        if(preHotelPriceMap.size>0){
            preHotelPriceMap.putAll(newHotelPriceMap)
            hotelPriceMap = preHotelPriceMap
            StoragePreference.saveHotelPriceMap(this, hotelPriceMap)
        }else{
            hotelPriceMap = newHotelPriceMap
            StoragePreference.saveHotelPriceMap(this, newHotelPriceMap)
        }
    }

    override fun onWithExtraMattressSelected(roomId: String, mealData: MealData) {
        val preWithMattressPriceMap = StoragePreference.getWithMattressPriceMap(this)

        roomWithMattressPriceMap[roomId] = mealData
        val newPriceMap = TreeMap<String, TreeMap<String, MealData>>()
        newPriceMap["night$selectedNight"] = roomWithMattressPriceMap

        if(preWithMattressPriceMap.size>0){
            preWithMattressPriceMap.putAll(newPriceMap)
            withMattressPriceMap = preWithMattressPriceMap
            StoragePreference.saveWithMattressPriceMap(this, withMattressPriceMap)
        }else{
            withMattressPriceMap = newPriceMap
            StoragePreference.saveWithMattressPriceMap(this, newPriceMap)
        }
    }

    override fun onWithoutExtraMattressSelected(roomId: String, mealData: MealData) {
        val preWithoutMattressPriceMap = StoragePreference.getWithoutMattressPriceMap(this)

        roomWithoutMattressPriceMap[roomId] = mealData
        val newPriceMap = TreeMap<String, TreeMap<String, MealData>>()
        newPriceMap["night$selectedNight"] = roomWithoutMattressPriceMap

        if(preWithoutMattressPriceMap.size>0){
            preWithoutMattressPriceMap.putAll(newPriceMap)
            withoutMattressPriceMap = preWithoutMattressPriceMap
            StoragePreference.saveWithoutMattressPriceMap(this, withoutMattressPriceMap)
        }else{
            withoutMattressPriceMap = newPriceMap
            StoragePreference.saveWithoutMattressPriceMap(this, newPriceMap)
        }
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
            hotelList.add(Hotel(0, "", "Select Hotel", 0, "", ArrayList()))
            hotelList.addAll(it)
            hotelAdapter.notifyDataSetChanged()
        })
    }


    private fun observeGetTransport() {
        travelPlanViewModel.observeGetTransport().observe(this, {
            transportLocationList.addAll(it)
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

    private fun getRoomFromId(id: String): RoomData{
        return allRoomList.first { it.roomTypeId == id }
    }

    private fun extractRoomId(value: String): String{
       return value.split("~")[1].toString()
    }


    private suspend fun createPdf(totalPrice: Int) {

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

                locationName = if (etTourName.text.toString().isEmpty())
                    locationName
                else etTourName.text.toString()

                val title =
                    Paragraph(locationName).setFontSize(16f)
                title.setTextAlignment(TextAlignment.CENTER)
                document.add(title)
                document.add(line)

                val plan = Paragraph("TRAVEL ITINERARY: ").setFontSize(16f).setBold()
                document.add(plan)

                getTransportList()?.also {
                    val noOfPeople = if(etNoOfPeople.text.toString().isNotEmpty())
                        etNoOfPeople.text.toString().toInt()
                    else 1

                    val noOfTransport = if(etNoOfTransport.text.toString().isNotEmpty())
                        etNoOfTransport.text.toString().toInt()
                    else 1

                    if (it.size > dayList.size) {
                        for (i in 0 until dayList.size) {
                            val transportPrice = it[i].transportPrice.toInt() * noOfTransport
                            val otherPrice = it[i].ticketPrice.toInt() * noOfPeople

                            val paragraph = Paragraph("Day${i + 1}").setFontSize(15f)
                            val list = List()
                            list.add("${it[i].description} | Transport: Rs$transportPrice | Others: Rs$otherPrice | TOTAL: Rs${transportPrice+otherPrice}")
                                .setFontSize(14f)
                            document.add(paragraph)
                            document.add(list)
                            document.add(line)
                        }
                    } else {
                        for (i in 0 until it.size) {
                            val transportPrice = it[i].transportPrice.toInt() * noOfTransport
                            val otherPrice = it[i].ticketPrice.toInt() * noOfPeople

                            val paragraph = Paragraph("Day${i + 1}").setFontSize(15f)
                            val list = List()
                            list.add("${it[i].description} | Transport: Rs$transportPrice | Others: Rs$otherPrice | TOTAL: Rs${transportPrice+otherPrice}")
                                .setFontSize(14f)
                            document.add(paragraph)
                            document.add(list)
                            document.add(line)
                        }
                    }
                }

                document.add(line)
                val hotel = Paragraph("HOTELS USED: ").setFontSize(16f).setBold()
                document.add(hotel)

                var noOfNights = 1
                for ((k, value) in hotelPriceMap) {
                    var totalPrice = 0
                    val withMattress = withMattressPriceMap[k]
                    val withoutMattress = withoutMattressPriceMap[k]
                    var room = ""
                    for ((i,v) in value) {
                        var withMattressStr = "Extra Mattress for age above 12"
                        var withoutMattressStr = "No Extra Mattress for age below 12"
                        if(withMattress!=null){
                            if(withMattress[i]!=null){
                                withMattressStr = "$withMattressStr(${withMattress[i]?.sortName}:Rs${withMattress[i]?.price})"
                            }else{
                                withMattressStr = ""
                            }
                        }else withMattressStr = ""

                        if(withoutMattress!=null){
                            if(withoutMattress[i]!=null){
                                withoutMattressStr = "$withoutMattressStr(${withoutMattress[i]?.sortName}:Rs${withoutMattress[i]?.price})"
                            }else{
                                withoutMattressStr = ""
                            }
                        }else withoutMattressStr = ""

                        room = "$room ${getRoomFromId(extractRoomId(i)).name}(${v.sortName}:Rs${v.price}) | ${withMattressStr} | ${withoutMattressStr} , "
                        totalPrice += v.price.toInt()
                    }

                    if(withMattress!=null){
                        for ((i,v) in withMattress) {
                            totalPrice += v.price.toInt()
                        }
                    }

                    if(withoutMattress!=null){
                        for ((i,v) in withoutMattress) {
                            totalPrice += v.price.toInt()
                        }
                    }
                    val list = List()
                    list.add("${hotelMap[k]}: $room TOTAL PRICE: Rs$totalPrice").setFontSize(14f)
                    val paragraph = Paragraph("Night${noOfNights}").setFontSize(15f)

                    document.add(paragraph)
                    document.add(list)
                    document.add(line)
                    noOfNights++

                }
                document.add(line)
                document.add(line)

                val price = Paragraph("TOTAL COST: Rs ${totalPrice}/- ").setFontSize(16f).setBold()
                document.add(price)
                val text2 = Paragraph("The above cost is Nett and Non Commissionable.").setFontSize(12f)
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


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    override fun onPermissionsGranted(requestCode: Int, perms: MutableList<String>) {
        submit()
    }

    override fun onPermissionsDenied(requestCode: Int, perms: MutableList<String>) {

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            data?.getSerializableExtra("ROOM")?.let {
                roomList.add(it as RoomData)
                allRoomList.add(it as RoomData)
                addHotelRoomAdapter.notifyDataSetChanged()
            }
        }
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onDestroy() {
        super.onDestroy()
        StoragePreference.saveHotelPriceMap(this, TreeMap())
        StoragePreference.saveWithMattressPriceMap(this, TreeMap())
        StoragePreference.saveWithoutMattressPriceMap(this, TreeMap())
    }

    private val job = Job()

    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.Main
}