package kr.ac.smu.cs.mungnyang.alarm

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.util.Log
import androidx.work.*
import kotlinx.coroutines.coroutineScope
import kr.ac.smu.cs.mungnyang.MainActivity
import java.net.SocketException
import java.time.Duration
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.temporal.ChronoUnit
import java.util.concurrent.TimeUnit

class ReminderWorker(appContext: Context, workerParams: WorkerParameters):
    CoroutineWorker(appContext,workerParams) {
    lateinit var notificationManager: NotificationManager
    lateinit var notificationChannel: NotificationChannel
    lateinit var builder: Notification.Builder
    private val channelId="kr.ac.smu.cs.mungnyang.alarm"
    private val description="Test notification"

    companion object{
        private const val REMINDER_WORK_NAME="reminder"
        private const val PARAM_NAME="name"

        fun runAt(){
            val workManager= WorkManager.getInstance()

            //API 레벨 26이상을 요구. app수준의 gradle에서 변경함
            //저녁 6시로 설정
            val alarmTime= LocalTime.of(13,40)
            var now= LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES)
            val nowTime=now.toLocalTime()

            if(nowTime==alarmTime ||nowTime.isAfter(alarmTime)){
                now=now.plusDays(1)
            }
            now=now.withHour(alarmTime.hour).withMinute(alarmTime.minute)
            val duration= Duration.between(LocalDateTime.now(),now)

            val data= workDataOf(PARAM_NAME to "Timer 01")

            val workRequest= OneTimeWorkRequestBuilder<ReminderWorker>()
                .setInitialDelay(duration.seconds, TimeUnit.SECONDS)
                .setInputData(data)
                .build()

            workManager.enqueueUniqueWork(REMINDER_WORK_NAME, ExistingWorkPolicy.REPLACE,workRequest)
        }

        fun cancel(){
            val workManager = WorkManager.getInstance()
            workManager.cancelUniqueWork(REMINDER_WORK_NAME)
        }
    }
    override suspend fun doWork(): Result = coroutineScope {
        val worker=this@ReminderWorker
        val context=applicationContext

        val name=inputData.getString(PARAM_NAME)
        var isScheduleNext=true

        try{
            //푸시 알람 오도록 설정
            notificationManager=applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationChannel= NotificationChannel(channelId,description, NotificationManager.IMPORTANCE_HIGH )
            notificationChannel.enableLights(true)
            notificationChannel.lightColor= Color.GREEN
            notificationChannel.enableVibration(true)
            notificationManager.createNotificationChannel(notificationChannel)
            val intent= Intent(applicationContext, MainActivity::class.java)
            val pendingIntent= PendingIntent.getActivity(applicationContext,0,intent, PendingIntent.FLAG_UPDATE_CURRENT)


            builder=
                Notification.Builder(applicationContext,channelId)
                    .setContentTitle("멍냥멍냥")
                    .setContentText("오늘의 할일 다 하셨나요? 멍냥멍냥을 통해 할일을 확인해 보세요!")
                    .setSmallIcon(kr.ac.smu.cs.mungnyang.R.drawable.stamp1)
                    .setContentIntent(pendingIntent)
                    .setAutoCancel(true)
                    .setColor(Color.parseColor("#AB7A4C"))
            notificationManager.notify(1234,builder.build())
            Result.success()
        }
        catch (e:Exception){
            //3번 재시도
            if(runAttemptCount>3){
                return@coroutineScope Result.success()
            }
            //네트워크 문제
            when(e.cause) {
                is SocketException -> {
                    //Timber.e(e.toString(), e.message)
                    Log.d("time","에러"+e.message)
                    isScheduleNext = false
                    Result.retry()
                }
                else -> {
                    Log.d("time","에러2"+e)
                    //Timber.e(e)
                    Result.failure()
                }
            }
        }
        finally {
            // only schedule next day if not retry, else it will overwrite the retry attempt
            // - because we use uniqueName with ExistingWorkPolicy.REPLACE
            if (isScheduleNext) {
                runAt() // schedule for next day
            }
        }
    }

}