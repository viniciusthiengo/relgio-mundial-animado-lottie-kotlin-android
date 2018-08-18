package thiengo.com.br.relgiomundialanimado

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

/*
 * Classe responsável por permitir a comunicação da
 * TrueTimeApplication para com a ClockActivity, isso
 * utilizando o canal LocalBroadcastManager.
 * */
class BroadcastApplication( val activity: ClockActivity ):
        BroadcastReceiver() {

    companion object {
        const val FILTER = "ba_filter"
    }

    override fun onReceive( context: Context, intent: Intent ) {
        /*
         * Assim que a "mensagem" é enviada de
         * TrueTimeApplication, invoque o método
         * fireSpinnerItemSelected().
         * */
        activity.fireSpinnerItemSelected()
    }
}