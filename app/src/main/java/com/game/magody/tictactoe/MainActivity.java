package com.game.magody.tictactoe;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    int numero_jugadores;
    int[][] casillas;
    Partida partida;
    String jugador1;
    String jugador2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //matriz de juego
        casillas = new int[][] {{R.id.a11,R.id.a12,R.id.a13},
                                {R.id.a21,R.id.a22,R.id.a23},
                                {R.id.a31,R.id.a32,R.id.a33}};

    }

    public void jugar(View view){
        limpiar_casillas();
        numero_jugadores = 1;

        if(view.getId() == R.id.dosjugadores){
            numero_jugadores = 2;
        }

        RadioGroup dificultades = (RadioGroup) findViewById(R.id.dificultades);
        int id_dificultad = dificultades.getCheckedRadioButtonId();

        int dificultad = 0;
        if(id_dificultad == R.id.dificultad_normal){
            dificultad = 1;
        }
        else if(id_dificultad == R.id.dificultad_imposible){
            dificultad = 2;
        }

        partida = new Partida(dificultad);

        //busca los botones especificados y los oculta
        ((Button) findViewById(R.id.unjugador)).setEnabled(false);
        ((RadioGroup) findViewById(R.id.dificultades)).setAlpha(0);
        ((Button) findViewById(R.id.dosjugadores)).setEnabled(false);

        this.imprimir("Turno inicial elegido aleatoriamente: " + partida.jugador,0);

        if(partida.jugador == 2 && numero_jugadores == 1){
            String salida = "";
            switch (dificultad){
                case 0:
                    salida = "Dificultad Fácil";
                    break;
                case 1:
                    salida = "Dificultad Normal";
                    break;
                case 2:
                    salida = "Dificultad Difícil";
                    break;
            }
            this.imprimir(salida,0);
            this.jugador1 = "usted";
            this.jugador2 = "máquina";
            this.elegirJugadaIA();
        }

        if(numero_jugadores == 2){
            this.jugador1 = "circulos";
            this.jugador2 = "equis";
        }

    }

    public void toque(View view){

        if(partida != null) {

            int fila = 0;
            int columna = 0;

            for (int i = 0; i <= 2; i++) {
                for (int j = 0; j <= 2; j++) {
                    if (casillas[i][j] == view.getId()) {
                        fila = i;
                        columna = j;
                        break;
                    }
                }
            }

            if(partida.estaCasillaDisponible(fila,columna))
            {
                partida.matriz_juego[fila][columna] = partida.jugador; // nosotros
                marcarCasilla(fila,columna);
                estadoPartida();
                // si gana el usuario partida se convierte en null

                if(partida != null){

                    if(numero_jugadores == 1){
                        if(!partida.estaMatrizLlena()) {

                            partida.jugador = 2;
                            elegirJugadaIA();
                        }
                        else{
                            estadoPartida();
                        }
                    }
                    else if(numero_jugadores == 2){
                        if(partida.jugador == 1)
                            partida.jugador = 2;
                        else
                            partida.jugador = 1;
                    }

                }


            }
            else
                imprimir("Casilla ocupada",0);




            /*
            Toast toast = Toast.makeText(this, "Has pulsado la pos: (" + fila + "," + columna + ")", Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();*/
        }
    }

    public void elegirJugadaIA(){
        int pos[] = new int[2];

        switch (partida.DIFICULTAD){
            case 0:
                pos = partida.jugadaIAEasyNormal(); break;
            case 1:
                int[] aux = partida.jugadaIAEasyNormal();
                if(aux.length == 3){
                    pos[0] = aux[1];
                    pos[1] = aux[2];
                }
                else
                    pos = aux;
                break;
            case 2:
                int[] aux1 = partida.jugadaIAInsano();
                if(aux1.length == 3){
                    pos[0] = aux1[1];
                    pos[1] = aux1[2];
                }
                else
                    pos = aux1;
                break;
        }

        partida.matriz_juego[pos[0]][pos[1]] = 2;
        marcarCasilla(pos[0], pos[1]);

        partida.jugador = 1;
        //imprimirMatrizActual();
        estadoPartida();

    }

    public void estadoPartida(){
        int estado = partida.comprobarEstado();
        if(estado == 0 && partida.estaMatrizLlena())
        {
            imprimir("El juego a terminado en empate",0);
            reiniciarPartida();
        }
        else if(estado!=0){
            String salida = "";
            switch (estado){
                case 1:
                    salida = "El ganador: " + this.jugador1;break;
                case 2:
                    salida = "El ganador: " + this.jugador2; break;
            }

            imprimir(salida,0);
            reiniciarPartida();
        }

    }


    public void marcarCasilla(int fila, int columna){
        ImageView imagen = (ImageView) findViewById(casillas[fila][columna]);

        if(partida.jugador == 1){
            imagen.setImageResource(R.drawable.circulo);
        }
        else{
            imagen.setImageResource(R.drawable.aspa);
        }


    }


    public void limpiar_casillas(){
        ImageView imagen;

        for(int i=0;i<=2;i++){
            for(int j=0;j<=2;j++){
                //asigna una casilla en blanco a cada casilla
                imagen = (ImageView) findViewById(casillas[i][j]);
                imagen.setImageResource(R.drawable.casilla);
            }
        }
    }

    public void reiniciarPartida(){
        partida = null;
        ((Button) findViewById(R.id.unjugador)).setEnabled(true);
        ((RadioGroup) findViewById(R.id.dificultades)).setAlpha(0.8f);
        ((Button) findViewById(R.id.dosjugadores)).setEnabled(true);
    }



    public void imprimirMatrizActual(){

        String salida = "";

        for (int i = 0; i <= 2; i++) {
            for (int j = 0; j <= 2; j++) {
                salida += partida.matriz_juego[i][j] + " ";
            }
            salida += " | ";
        }

        Toast toast = Toast.makeText(this, salida, Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }




    public void imprimir(String msg,int delay){

        Toast toast = Toast.makeText(this,msg,delay);
        toast.setGravity(Gravity.CENTER,0,0);
        toast.show();
    }

}
