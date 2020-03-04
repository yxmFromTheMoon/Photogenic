package com.yxm.lib_pullalive

import android.annotation.TargetApi
import android.app.Service
import android.app.job.JobInfo
import android.app.job.JobParameters
import android.app.job.JobScheduler
import android.app.job.JobService
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.util.Log

/**
 *Created by yxm on 2020/3/3
 *@function 保活服务
 */
@TargetApi(value = Build.VERSION_CODES.LOLLIPOP)
class AliveJobService: JobService() {

    private val TAG = this.javaClass.simpleName

    private lateinit var mJobScheduler: JobScheduler

    private val mHandler: Handler = Handler(Looper.getMainLooper()){
        when(it.what){
            0x01 -> {
                Log.d(TAG,"PullAlive")
                jobFinished(it.obj as JobParameters,true)
            }
        }
        false
    }

    override fun onCreate() {
        super.onCreate()
        mJobScheduler = getSystemService(Context.JOB_SCHEDULER_SERVICE) as JobScheduler
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val job = initJobInfo(startId)
        if(mJobScheduler.schedule(job!!) <= 0){
            Log.d(TAG,"failure")
        }else{
            Log.d(TAG,"success")
        }
        return Service.START_STICKY
    }

    /**
     * 初始化jobInfo
     */
    private fun initJobInfo(startId: Int):JobInfo?{
        val builder = JobInfo.Builder(startId,
                ComponentName(packageName,AliveJobService::class.java.name))
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
            builder.setMinimumLatency(JobInfo.DEFAULT_INITIAL_BACKOFF_MILLIS)
                    .setOverrideDeadline(JobInfo.DEFAULT_INITIAL_BACKOFF_MILLIS)
                    .setBackoffCriteria(JobInfo.DEFAULT_INITIAL_BACKOFF_MILLIS
                    ,JobInfo.BACKOFF_POLICY_LINEAR)
        }else{
            builder.setPeriodic(JobInfo.DEFAULT_INITIAL_BACKOFF_MILLIS)
        }
        builder.setPersisted(false)//是否持久化
                .setRequiredNetworkType(JobInfo.NETWORK_TYPE_NONE)
                .setRequiresCharging(false)
        return builder.build()
    }
    override fun onStopJob(params: JobParameters?): Boolean {
        return true
    }

    override fun onStartJob(params: JobParameters?): Boolean {
        mHandler.sendMessage(Message.obtain(mHandler,1,params))
        return true
    }

    companion object{
        fun start(context: Context){
            context.startActivity(Intent(context,AliveJobService::class.java))
        }
    }
}