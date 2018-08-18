package thiengo.com.br.relgiomundialanimado

import android.app.Application
import android.content.Intent
import android.os.SystemClock
import android.support.v4.content.LocalBroadcastManager
import com.instacart.library.truetime.TrueTimeRx
import io.reactivex.schedulers.Schedulers


class TrueTimeApplication: Application() {

    override fun onCreate() {
        super.onCreate()

        /*Thread{
            kotlin.run {
                SystemClock.sleep(2000)*/

                /*
                 * Sempre iniciaremos a busca por alguma data TrueTime, pois
                 * como o tick do aparelho é que será utilizado, ainda temos
                 * a possibilidade de ter uma data não atualizada se não houver
                 * uma nova conexão com a Internet. Mas lembrando que uma nova
                 * invocação a servidor NTP não remove da cache a TrueTime já
                 * salva.
                 * */
                TrueTimeRx
                    .build()
                        .withSharedPreferences(this)
                        .initializeRx("time.apple.com")
                        .subscribeOn(Schedulers.io())
                        .subscribe(
                                {
                                    /*
                                     * Tudo certo com a obtenção de uma data
                                     * TrueTime de servidor NTP, agora é preciso
                                     * atualizar o horário em tela, logo uma
                                     * "mensagem" broadcast é utilizada para
                                     * comunicar a atividade ClockActivity sobre
                                     * isso, a comunicação será por meio de
                                     * BroadcastApplication e LocalBroadcastManager.
                                     * */
                                    val intent = Intent( BroadcastApplication.FILTER )

                                    LocalBroadcastManager
                                            .getInstance( this@TrueTimeApplication )
                                            .sendBroadcast( intent )
                                },
                                {
                                    /* Pilha de erro, caso ocorra. */
                                    /* it.printStackTrace(); */
                                }
                        )
            /*}
        }.start()*/


    }
}