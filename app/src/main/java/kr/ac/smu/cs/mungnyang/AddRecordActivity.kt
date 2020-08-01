package kr.ac.smu.cs.mungnyang

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import com.theartofdev.edmodo.cropper.CropImage
import kotlinx.android.synthetic.main.activity_add_record.*
import kotlinx.android.synthetic.main.activity_sign.*
import kotlinx.android.synthetic.main.activity_today.*
import java.io.ByteArrayOutputStream

class AddRecordActivity : AppCompatActivity() {
    private var recordDatabase:RecordDatabase?=null
    private var recordList=mutableListOf<Record>()
    private var record=Record()
    var PICK_IMAGE_REQUEST: Int = 2;
    var imgStatus:Boolean=false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_record)

        val userid=intent.getIntExtra("user_id",0)
        Toast.makeText(this,"$userid",Toast.LENGTH_LONG).show()
        rec_add.setOnClickListener{
            record.userid=userid
            record.name=rec_name.text.toString()
            record.rec=rec_editText.text.toString()
            val database: RecordDatabase = RecordDatabase.getInstance(applicationContext) //application context
            val recordDao: RecordDao=database.recordDao
            Thread { database?.recordDao?.insert(record) }.start()

            val intent= Intent(this,RecordActivity::class.java)
            intent.putExtra("user_id",userid)
            //intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
            startActivity(intent)
            finish()


        }

        rec_pic.setOnClickListener {
            val intent = Intent()
            intent.type = "image/*"
            intent.action = Intent.ACTION_GET_CONTENT
            intent.putExtra("crop", "true")
            startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST)
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
                    rec_pic.setImageURI(resultUri)
                    var bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), resultUri);
                    var stream= ByteArrayOutputStream()
                    bitmap?.compress(Bitmap.CompressFormat.JPEG,100,stream)
                    var byteArray=stream.toByteArray()
                    record.image=byteArray
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
