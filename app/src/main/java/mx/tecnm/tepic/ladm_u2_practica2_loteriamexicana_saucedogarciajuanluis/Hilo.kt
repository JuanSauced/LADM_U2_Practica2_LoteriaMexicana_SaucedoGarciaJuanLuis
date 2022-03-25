package mx.tecnm.tepic.ladm_u2_practica2_loteriamexicana_saucedogarciajuanluis

import android.media.MediaPlayer
import android.widget.ImageView
import kotlinx.coroutines.delay

class Hilo(a: MainActivity,carSo:Int,sevaSo:Int):Thread() {
    var a=a
    var Revisar=0
    var sonido=0
    var cartaSonido=carSo
    var carSevaYseCorre=sevaSo
    lateinit var arregloDes: Array<Int>
    private var pausar = false
    private var ejecutar = true

    override fun run() {
        super.run()
        while (ejecutar) {
            if (!pausar) {
                if (sonido == -1) {
                    var mp = MediaPlayer.create(a, carSevaYseCorre)
                    mp.start()
                    sonido = 0
                }
                if (sonido == 1) {
                    var mp = MediaPlayer.create(a, cartaSonido)
                    mp.start()
                    sonido = 0
                }


            }
        }
    }

    fun sonidoCorre(){
        sonido=-1
    }
    fun sonidoCambiaron(){
        sonido=1
    }
    fun terminarHilo(){
        ejecutar=false
    }
    fun pausarHilo(){
        pausar=true
    }
    fun faltaron(falt:Int){
        var mp = MediaPlayer.create(a, falt)
        mp.start()
    }
    fun despausarHilo(){
        pausar=false
    }
    fun estaPausado() :Boolean {
        return pausar
    }
}