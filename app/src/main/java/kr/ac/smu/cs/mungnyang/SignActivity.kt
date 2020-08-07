package kr.ac.smu.cs.mungnyang

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.database.Cursor
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.theartofdev.edmodo.cropper.CropImage
import kotlinx.android.synthetic.main.activity_calendar__selected.*
import kotlinx.android.synthetic.main.activity_sign.*
import java.io.ByteArrayOutputStream

class SignActivity : AppCompatActivity() {
    var user=User()
    var PICK_IMAGE_REQUEST: Int = 2;
    var imgStatus:Boolean=false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign)

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
            user.color=edit_color.text.toString()
            user.dday=edit_dday.text.toString()
            user.gender=edit_gender.text.toString()
            user.num=edit_num.text.toString()
            user.type=edit_kind.text.toString()
            val database: UserDatabase = UserDatabase.getInstance(applicationContext) //application context
            val userDao: UserDao=database.userDao

            Thread { database.userDao.insert(user) }.start()


            val intent = Intent(this, StartActivity::class.java) //로그인 액티비티로 돌아가기
            this.startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
            this.finish()}catch(e:Exception){
                Toast.makeText(this,"$e",Toast.LENGTH_LONG).show()
            }
        }

        edit_kind.setOnClickListener {

                val listItems = arrayOf("강아지", "고양이")
                val mBuilder = AlertDialog.Builder(this@SignActivity)
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
                bitmap?.compress(Bitmap.CompressFormat.JPEG,100,stream)
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
            Toast.makeText(this,"$e",Toast.LENGTH_LONG).show()
        }
    }

}
