package mx.tecnm.tepic.ladm_u2_practica2_loteriamexicana_saucedogarciajuanluis

import android.media.MediaPlayer
import android.widget.ImageView
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext
import kotlin.random.Random

class Carta(a:MainActivity ,iView:ImageView, imP :ImageView,carIma:Array<Int>,carSo:Array<Int>) {
     val a=a
     val iView = iView
     val imP=imP
     var posAr = 0
     var Revisar = -1
     var sonido=0
     lateinit var revHilo:Hilo

     lateinit var scope: CoroutineScope
     lateinit var coro : CoroutineContext
     lateinit var rev : CoroutineContext
     private var prim = true
     lateinit var cartaImagen : Array<Int>
     lateinit var cartaSonido : Array<Int>

     init {
          scope = CoroutineScope(Job() + Dispatchers.Main)
          cartaImagen=carIma
          cartaSonido=carSo
         // revHilo=Hilo(a, iView, imP, cartaImagen, cartaSonido)
     }

     fun barajar(arregloDes: Array<Int>):Array<Int>{
          var aux = 0
          for (i in 0 until arregloDes.size ) {
               posAr = Random.nextInt(arregloDes.size - 1)
               aux = arregloDes[i]
               arregloDes[i] = arregloDes[posAr]
               arregloDes[posAr] = aux
          }
          return arregloDes
     }

     fun tirar(arregloDes: Array<Int>) {
          Revisar=-1
           coro=scope.launch(EmptyCoroutineContext,CoroutineStart.LAZY){
              delay(3000)
               for (i in 0 until arregloDes.size ) {
                    iView.setImageResource(cartaImagen[arregloDes[i]])
                    sonido=cartaSonido[arregloDes[i]]
                    var mp=MediaPlayer.create(a,sonido)
                    mp.start()
                    Revisar++
                    if(i!=0) {
                         a.runOnUiThread {
                              imP.setImageResource(cartaImagen[arregloDes[i - 1]])
                         }
                    }
                    delay(2000)
                    mp.release()
               }
                imP.setImageResource(cartaImagen[arregloDes[53]])
                iView.setImageResource(0)
          }
          (coro as Job).start()
     }

     fun pararCarta(){
          coro.cancel()
     }

     fun revisarCarta(arregloDes: Array<Int>){
          rev=scope.launch(EmptyCoroutineContext,CoroutineStart.LAZY){
               delay(2000)
               if(Revisar+1!=arregloDes.size-1)
                    Revisar++
               for (i in Revisar until arregloDes.size) {
                    sonido=cartaSonido[arregloDes[i]]
                    var mp=MediaPlayer.create(a,sonido)
                    mp.start()
                    iView.setImageResource(cartaImagen[arregloDes[i]])
                    if(i!=0) {
                         imP.setImageResource(cartaImagen[arregloDes[i-1]])
                    }
                    delay(2000)
               }
               imP.setImageResource(cartaImagen[arregloDes[53]])
               iView.setImageResource(0)
          }
          (rev as Job).start()
     }
     fun regresarRevisar():Int{
          return ++Revisar
     }
     fun terminarJuego(){
          rev.cancel()
     }
     fun faltaron(audioFaltaron: Int){
          revHilo.faltaron(audioFaltaron)
     }
}