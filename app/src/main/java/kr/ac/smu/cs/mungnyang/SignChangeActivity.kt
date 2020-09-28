package kr.ac.smu.cs.mungnyang

import android.annotation.SuppressLint
import android.app.Activity
import android.app.DatePickerDialog
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import com.theartofdev.edmodo.cropper.CropImage
import kotlinx.android.synthetic.main.activity_sign.*
import java.io.ByteArrayOutputStream
import java.util.*

class SignChangeActivity : AppCompatActivity() {

    var user=User()
    var PICK_IMAGE_REQUEST: Int = 2;
    private var userList=mutableListOf<User>()
    var imgStatus:Boolean=false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign)
        val id:Int=intent.getIntExtra("user_id",0)
        val database: UserDatabase = UserDatabase.getInstance(applicationContext) //application context
        val userDao: UserDao=database.userDao
        Thread{userList = database?.userDao?.getUser(id)!!}.start()
        Thread.sleep(300)
        user.id=id
        user.name=userList[0].name
        user.gender=userList[0].gender
        user.birth=userList[0].birth
        user.type=userList[0].type
        user.num= userList[0].num
       // user.color=userList[0].color
        user.dday=userList[0].dday
        user.image=userList[0].image
        user.backPath=userList[0].backPath
        user.maruPath=userList[0].maruPath
        user.myLevel=userList[0].myLevel
        user.met_year=userList[0].met_year
        user.met_month=userList[0].met_month
        user.met_day=userList[0].met_day
        user.birth_day=userList[0].birth_day
        user.birth_month=userList[0].birth_month
        user.birth_year=userList[0].birth_year
        user.cdday=userList[0].cdday

        edit_birth.setText("${user.birth_year}년 "+"${user.birth_month}월 "+"${user.birth_day}일")
       // edit_color.setText(user.color)
        edit_dday.setText("${user.birth_year}년 "+"${user.birth_month}월 "+"${user.birth_day}일")
        edit_gender.setText(user.gender)
        if(user.image!=null) {
            val byteArray = user.image
            val picture = BitmapFactory.decodeByteArray(byteArray, 0, byteArray!!.size)

            edit_image.setImageBitmap(picture)
        }
        edit_kind.setText(user.type)
        edit_num.setText(user.num)
        dday_button.setText(user.cdday)

        edit_image.setOnClickListener {
            val intent = Intent()
            intent.type = "image/*"
            intent.action = Intent.ACTION_GET_CONTENT
            intent.putExtra("crop", "true")
            startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST)
        }
        add_button.setOnClickListener {
            try{
                user.name=name_edit.text.toString()
                user.birth=edit_birth.text.toString()
            //    user.color=edit_color.text.toString()
                user.dday=edit_dday.text.toString()
                // user.gender=edit_gender.text.toString()
                user.num=edit_num.text.toString()
                user.type=edit_kind.text.toString()


                Thread { database.userDao.update(user) }.start()


                val intent = Intent(this, StartActivity::class.java) //로그인 액티비티로 돌아가기
                this.startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                this.finish()}catch(e:Exception){
                Toast.makeText(this,"$e", Toast.LENGTH_LONG).show()
            }
        }
        dday_button.setOnClickListener {
            val listItems = arrayOf("생일", "만난 날")
            val mBuilder = AlertDialog.Builder(this@SignChangeActivity)
            mBuilder.setTitle("종류를 골라주세요")
            mBuilder.setSingleChoiceItems(listItems, -1) { dialogInterface, i ->

                user.cdday=listItems[i]
                dialogInterface.dismiss()
                dday_button.setText(listItems[i])
            }
            // Set the neutral/cancel button click listener
            mBuilder.setNeutralButton("Cancel") { dialog, which ->
                // Do something when click the neutral button
                dialog.cancel()
            }

            val mDialog = mBuilder.create()
            mDialog.show()
        }

        edit_kind.setOnClickListener {

            val listItems = arrayOf("강아지", "고양이")
            val mBuilder = AlertDialog.Builder(this@SignChangeActivity)
            mBuilder.setTitle("종류를 골라주세요")
            mBuilder.setSingleChoiceItems(listItems, -1) { dialogInterface, i ->

                user.type=listItems[i]
                dialogInterface.dismiss()
                edit_kind.setText(listItems[i])
            }
            // Set the neutral/cancel button click listener
            mBuilder.setNeutralButton("Cancel") { dialog, which ->
                // Do something when click the neutral button
                dialog.cancel()
            }

            val mDialog = mBuilder.create()
            mDialog.show()

        }
        edit_gender.setOnClickListener {
            val listItems = arrayOf("암컷", "수컷")
            val mBuilder = AlertDialog.Builder(this@SignChangeActivity)
            mBuilder.setTitle("성별을 골라주세요")
            mBuilder.setSingleChoiceItems(listItems, -1) { dialogInterface, i ->

                user.gender=listItems[i]
                dialogInterface.dismiss()
                edit_gender.setText(listItems[i])
            }
            // Set the neutral/cancel button click listener
            mBuilder.setNeutralButton("Cancel") { dialog, which ->
                // Do something when click the neutral button
                dialog.cancel()
            }

            val mDialog = mBuilder.create()
            mDialog.show()
        }

    }
    @RequiresApi(Build.VERSION_CODES.N)
    fun clickDataPicker(view: View) {  //datepicker 눌렀을때 선택창 띄워주고 정보받음
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        val dpd = DatePickerDialog(this, DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->

            user.birth_day=dayOfMonth
            user.birth_month=monthOfYear+1
            user.birth_year=year
            edit_birth.text="${user.birth_year}년 "+"${user.birth_month}월 "+"${user.birth_day}일"  //선택한 정보로 버튼수정
        }, year, month, day)

        dpd.show()
    }
    @RequiresApi(Build.VERSION_CODES.N)
    fun clickDataPicker2(view: View) {  //datepicker 눌렀을때 선택창 띄워주고 정보받음
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        val dpd = DatePickerDialog(this, DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->

            user.met_day=dayOfMonth
            user.met_month=monthOfYear+1
            user.met_year=year
            edit_dday.text="${user.birth_year}년 "+"${user.birth_month}월 "+"${user.birth_day}일"  //선택한 정보로 버튼수정
        }, year, month, day)

        dpd.show()
    }
    @SuppressLint("MissingSuperCall")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        /*if (resultCode == RESULT_OK) {
            //이미지 선택
            if (requestCode == PICK_IMAGE_REQUEST) {
                picUri = data?.getData()
                Log.d("uriGallery", picUri.toString())
                performCrop()
            }
            //이미지 자르기
            else if (requestCode == PIC_CROP) {
                //get the returned data
                val extras = data?.getExtras()
                //get the cropped bitmap
                val thePic = extras?.get("data") as Bitmap
                //자른 이미지 보여주기
                imageView_add.setImageBitmap(thePic)

                var bitmapToUri = getImageUri(this, thePic)
                val filePathColumn =
                    arrayOf(MediaStore.Images.Media.DATA)
                val cursor: Cursor? =
                    contentResolver.query(bitmapToUri!!, filePathColumn, null, null, null)
                cursor!!.moveToFirst()
                val columnIndex: Int = cursor.getColumnIndex(filePathColumn[0])
                //이게 파일경로+파일명
                //저장하기 위해서 변수에 경로 넣기
                imgDecodableString = cursor.getString(columnIndex)

            }
        }*/
        try{
            super.onActivityResult(requestCode, resultCode, data)
            if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK) {
                if(data == null || data.data == null){
                    return
                }
                CropImage.activity(data.data!!).setAspectRatio(3,2).start(this)
            }else if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
                val result = CropImage.getActivityResult(data)
                if (resultCode == RESULT_OK) {
                    var resultUri = result.getUri();
                    imgStatus = true
                    edit_image.setImageURI(resultUri)
                    var bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), resultUri);
                    var stream= ByteArrayOutputStream()
                    bitmap?.compress(Bitmap.CompressFormat.JPEG,10,stream)
                    var byteArray=stream.toByteArray()
                    user.image=byteArray
                    /*var bitmapToUri = getImageUri(this, bitmap)
                    val filePathColumn =
                        arrayOf(MediaStore.Images.Media.DATA)
                    val cursor: Cursor? =
                        contentResolver.query(bitmapToUri!!, filePathColumn, null, null, null)
                    cursor!!.moveToFirst()
                    val columnIndex: Int = cursor.getColumnIndex(filePathColumn[0])
                    //이게 파일경로+파일명
                    //저장하기 위해서 변수에 경로 넣기
                    imgDecodableString = cursor.getString(columnIndex)*/
                } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                    val error = result.getError()
                    Toast.makeText(this, error.message, Toast.LENGTH_SHORT).show()
                }
            }}catch(e:Exception){
            Toast.makeText(this,"$e", Toast.LENGTH_LONG).show()
        }
    }

}
