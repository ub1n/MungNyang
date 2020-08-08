package kr.ac.smu.cs.mungnyang

import android.content.Intent
import android.os.Bundle
import com.google.android.material.floatingactionbutton.FloatingActionButton
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import android.view.Menu
import android.widget.Toast
import kotlinx.android.synthetic.main.content_home.*

class HomeActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private var userDatabase:UserDatabase?=null
    private var userList=mutableListOf<User>()
    var user=User()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        val id:Int=intent.getIntExtra("user_id",0)
        val check:Int=intent.getIntExtra("level_check",0)
        userDatabase=UserDatabase.getInstance(this)
        Thread{userList = userDatabase?.userDao?.getUser(id)!!}.start()

        var a=R.drawable.back0

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)


        /*user.id=id
        user.name=intent.getStringExtra("name")
        user.gender=intent.getStringExtra("gender")
        user.birth=intent.getStringExtra("birth")
        user.type=intent.getStringExtra("type")
        user.num= intent.getStringExtra("num")
        user.color=intent.getStringExtra("color")
        user.dday=intent.getStringExtra("dday")
        user.image=intent.getByteArrayExtra("image")*/

        user.id=id
        user.name=userList[0].name
        user.gender=userList[0].gender
        user.birth=userList[0].birth
        user.type=userList[0].type
        user.num= userList[0].num
        user.color=userList[0].color
        user.dday=userList[0].dday
        user.image=userList[0].image
        user.backPath=userList[0].backPath
        user.maruPath=userList[0].maruPath
        user.myLevel=userList[0].myLevel
        if(check==1){
            user.myLevel=Com.mlevel
            Thread{userDatabase?.userDao?.update(user)!!}.start()
        }else{
            Com.setLevel(user.myLevel)
        }
        if(Com.backPath!=0){
            user.backPath=Com.backPath
            Thread{userDatabase?.userDao?.update(user)!!}.start()
            Com.setbackimage(0)
        }
        if(Com.maruPath!=0){
            user.maruPath=Com.maruPath
            Thread{userDatabase?.userDao?.update(user)!!}.start()
            Com.setmaruimage(0)
        }
        if(user.backPath!=0)
            backimage.setImageResource(user.backPath)
        if(user.maruPath!=0)
            groundimage.setImageResource(user.maruPath)
       /* if(user.type=="고양이")
            Com.setType(1)
        else if(user.type=="강아지")
            Com.setType(0)*/
        if(user.type=="고양이")
            dogimage.setImageResource(R.drawable.cat)
       // Toast.makeText(this,user.birth,Toast.LENGTH_LONG).show()

        //db에 배경정보 넣어서 업데이트해주자!
        //Thread{userDatabase?.userDao?.update(user)}.start()

        val fab: FloatingActionButton = findViewById(R.id.fab)
        fab.setOnClickListener { view ->
            Thread { userDatabase?.userDao?.delete(id) }.start()
            intent=Intent(this,StartActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)
            finish()
        }

        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.nav_view)
        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow,
                R.id.nav_tools, R.id.nav_share, R.id.nav_send
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
        var intent= Intent(this,StartActivity::class.java)
        navView.setNavigationItemSelectedListener {
            when(it.itemId){
                R.id.nav_today->{
                    intent=Intent(this,TodayActivity::class.java)
                    intent.putExtra("user_id",id)
                    startActivityForResult(intent,3)}
                //Toast.makeText(this,"setting", Toast.LENGTH_LONG).show()
                R.id.nav_record->{
                    intent=Intent(this,RecordActivity::class.java)
                    intent.putExtra("user_id",id)
                    startActivityForResult(intent,4)
                }
                R.id.nav_cal->{
                    intent=Intent(this,CalendarActivity::class.java)
                    intent.putExtra("user_id",id)
                    startActivityForResult(intent,5)
                }
                R.id.nav_setting->{
                    intent=Intent(this,StampActivity::class.java)
                    intent.putExtra("user_id",id)
                    startActivityForResult(intent,6)
                }
            }

            return@setNavigationItemSelectedListener false

        }
        groundimage.setOnClickListener {
            intent=Intent(this,BackActivity::class.java)
            intent.putExtra("user_id",id)
            intent.putExtra("num",1)
            startActivityForResult(intent,10)
        }
        backimage.setOnClickListener {
            intent=Intent(this,BackActivity::class.java)
            intent.putExtra("user_id",id)
            intent.putExtra("num",0)
            startActivityForResult(intent,20)
        }

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
       // menuInflater.inflate(R.menu.home, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }



}
