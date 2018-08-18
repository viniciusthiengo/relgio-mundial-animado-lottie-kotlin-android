package thiengo.com.br.relgiomundialanimado

import android.os.AsyncTask
import com.instacart.library.truetime.TrueTimeRx
import java.lang.ref.WeakReference
import java.util.*

class AsyncTrueTime(): AsyncTask<String, Unit, Calendar>() {

    /*
     * Trabalhando com WeakReference (referência fraca) para
     * garantir que não haverá vazamento de memória por
     * causa de uma instância de AsyncTrueTime().
     * */
    lateinit var weakActivity: WeakReference<ClockActivity>

    constructor( activity: ClockActivity ): this(){
        weakActivity = WeakReference( activity )
    }

    override fun doInBackground( vararg args: String? ): Calendar {

        lateinit var date : Date

        /*
         * O bloco try{}catch{} a seguir indica que: caso não
         * haja uma data TrueTime disponível então utilize a
         * data local do aparelho.
         * */
        try{
            date = TrueTimeRx.now() /* Horário servidor NTP */
            weakActivity.get()?.infoDateShow(false) // Esconde info.
        }
        catch (e : Exception){
            date = Date() /* Horário do aparelho */
            weakActivity.get()?.infoDateShow(true) // Apresenta info.
        }

        /*
         * Colocando o Date em um Calendar, juntamente ao GMT,
         * fuso horário escolhido. Isso, pois o trabalho com
         * Calendar é mais simples e eficiente do que com Date.
         * */
        val calendar = Calendar.getInstance(
                TimeZone.getTimeZone(args[0])
        )
        calendar.timeInMillis = date.time

        return calendar
    }

    override fun onPostExecute( calendar: Calendar ) {
        super.onPostExecute( calendar )

        weakActivity.get()?.updateClock( calendar )
    }
}