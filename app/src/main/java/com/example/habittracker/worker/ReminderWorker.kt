package com.example.habittracker.worker

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.work.Worker
import androidx.work.WorkerParameters
// Se o R estiver vermelho, verifique se o pacote no topo é o mesmo declarado no AndroidManifest
// import com.example.habittracker.R

class ReminderWorker(context: Context, workerParams: WorkerParameters) : Worker(context, workerParams) {

    override fun doWork(): Result {
        sendNotification()
        return Result.success()
    }

    private fun sendNotification() {
        val notificationManager = applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val channelId = "habit_reminder_channel"

        // CORREÇÃO AQUI: Build.VERSION_CODES.O (letra O, não zero)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                "Lembrete de Hábitos",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            notificationManager.createNotificationChannel(channel)
        }

        val notification = NotificationCompat.Builder(applicationContext, channelId)
            .setSmallIcon(android.R.drawable.ic_lock_idle_alarm)
            .setContentTitle("Hora de focar!")
            .setContentText("Não se esqueça de marcar seus hábitos hoje.")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setAutoCancel(true) // Fecha a notificação ao clicar
            .build()

        notificationManager.notify(1, notification)
    }
}