package com.game.magody.tictactoe;

import android.widget.Toast;

import java.util.Random;

/**
 * Created by User on 20/03/2018.
 */

public class Partida {

    public final int DIFICULTAD;
    public int jugador;

    public int[][] matriz_juego;
    public int[][][] combinaciones = {{{0,0},{0,1},{0,2}},
                                      {{1,0},{1,1},{1,2}},
                                      {{2,0},{2,1},{2,2}},
                                      {{0,0},{1,0},{2,0}},
                                      {{0,1},{1,1},{2,1}},
                                      {{0,2},{1,2},{2,2}},
                                      {{0,0},{1,1},{2,2}},
                                      {{0,2},{1,1},{2,0}}};



    public Partida(int dificultad){
        this.DIFICULTAD = dificultad;
        Random turno = new Random();
        jugador = turno.nextInt(2) + 1; //circulo
        matriz_juego = new int[3][3];

        for (int i = 0; i <= 2; i++) {
            for (int j = 0; j <= 2; j++) {
                matriz_juego[i][j] = 0;
            }
        }

    }


    public int[] jugadaIAEasyNormal(){

        int pos[] = new int[2];
        int info_insta[] = dosEnRaya();
        if(this.DIFICULTAD == 1 && info_insta[0] == 1){
            return info_insta;
        }

        boolean es_correcto = false;
        do {
            //se repite hasta que elija una casilla correcta
            Random random = new Random();

            pos[0] = random.nextInt(3);
            pos[1] = random.nextInt(3);

            if(this.estaCasillaDisponible(pos[0],pos[1])){
                es_correcto = true;
            }


        }while(!es_correcto);


        return pos;

    }

    public int[] jugadaIAInsano(){
        int pos[] = new int[2];
        int info_insta[] = dosEnRaya();

        if(this.DIFICULTAD == 2 && info_insta[0] == 1){
            return info_insta;
        }
        else if(this.matriz_juego[0][0] == 1 &&
                this.matriz_juego[1][1] == 2 &&
                this.matriz_juego[2][2] == 1){

            if(this.estaCasillaDisponible(0,1)){
                pos[0] = 0;
                pos[1] = 1;
                return pos;
            }
            else if(this.estaCasillaDisponible(1,0)){
                pos[0] = 1;
                pos[1] = 0;
                return pos;
            }
            else if(this.estaCasillaDisponible(1,2)){
                pos[0] = 1;
                pos[1] = 2;
                return pos;
            }
            else if(this.estaCasillaDisponible(2,1)){
                pos[0] = 2;
                pos[1] = 1;
                return pos;
            }
        }
        else if(this.matriz_juego[0][2] == 1 &&
                this.matriz_juego[1][1] == 2 &&
                this.matriz_juego[2][0] == 1){

            if(this.estaCasillaDisponible(0,1)){
                pos[0] = 0;
                pos[1] = 1;
                return pos;
            }
            else if(this.estaCasillaDisponible(1,0)){
                pos[0] = 1;
                pos[1] = 0;
                return pos;
            }
            else if(this.estaCasillaDisponible(1,2)){
                pos[0] = 1;
                pos[1] = 2;
                return pos;
            }
            else if(this.estaCasillaDisponible(2,1)){
                pos[0] = 2;
                pos[1] = 1;
                return pos;
            }
        }
        else if(this.estaCasillaDisponible(1,1)){
            pos[0] = 1;
            pos[1] = 1;
            return pos;
        }
        else if(this.estaCasillaDisponible(0,0)){
            pos[0] = 0;
            pos[1] = 0;
            return pos;
        }
        else if(this.estaCasillaDisponible(0,2)){
            pos[0] = 0;
            pos[1] = 2;
            return pos;
        }
        else if(this.estaCasillaDisponible(2,0)){
            pos[0] = 2;
            pos[1] = 0;
            return pos;
        }
        else if(this.estaCasillaDisponible(2,2)){
            pos[0] = 2;
            pos[1] = 2;
            return pos;
        }


        boolean es_correcto = false;
        do {
            //se repite hasta que elija una casilla correcta
            Random random = new Random();

            pos[0] = random.nextInt(3);
            pos[1] = random.nextInt(3);

            if(this.estaCasillaDisponible(pos[0],pos[1])){
                es_correcto = true;
            }


        }while(!es_correcto);



        return pos;
    }

    public int[] dosEnRaya(){

        // sePuededosEnraya, posfila,poscolumna -1 por defecto
        int info[] = {0,-1,-1};


        /*
        *            combinaciones = {{{0,0},{0,1},{0,2}},
                                      {{1,0},{1,1},{1,2}},
                                      {{2,0},{2,1},{2,2}},
                                      {{0,0},{1,0},{2,0}},
                                      {{0,1},{1,1},{2,1}},
                                      {{0,2},{1,2},{2,2}},
                                      {{0,0},{1,1},{2,2}},
                                      {{0,2},{1,1},{2,0}}};
        * */



        for (int[][] combinacion: combinaciones) {

            if(matriz_juego[combinacion[0][0]][combinacion[0][1]] == 2 &&
                    matriz_juego[combinacion[1][0]][combinacion[1][1]] == 2 &&
                    matriz_juego[combinacion[2][0]][combinacion[2][1]] == 0){
                info[0] = 1;
                info[1] = combinacion[2][0];
                info[2] = combinacion[2][1];
                break;
            }
            else if(matriz_juego[combinacion[0][0]][combinacion[0][1]] == 2 &&
                    matriz_juego[combinacion[1][0]][combinacion[1][1]] == 0 &&
                    matriz_juego[combinacion[2][0]][combinacion[2][1]] == 2){
                info[0] = 1;
                info[1] = combinacion[1][0];
                info[2] = combinacion[1][1];
                break;
            }
            else if(matriz_juego[combinacion[0][0]][combinacion[0][1]] == 0 &&
                    matriz_juego[combinacion[1][0]][combinacion[1][1]] == 2 &&
                    matriz_juego[combinacion[2][0]][combinacion[2][1]] == 2){
                info[0] = 1;
                info[1] = combinacion[0][0];
                info[2] = combinacion[0][1];
                break;
            }
            //{{2,0},{2,1},{2,2}},
            else if(matriz_juego[combinacion[0][0]][combinacion[0][1]] == 1 &&
                    matriz_juego[combinacion[1][0]][combinacion[1][1]] == 1 &&
                    matriz_juego[combinacion[2][0]][combinacion[2][1]] == 0){
                info[0] = 1;
                info[1] = combinacion[2][0];
                info[2] = combinacion[2][1];
                break;
            }
            else if(matriz_juego[combinacion[0][0]][combinacion[0][1]] == 1 &&
                    matriz_juego[combinacion[1][0]][combinacion[1][1]] == 0 &&
                    matriz_juego[combinacion[2][0]][combinacion[2][1]] == 1){
                info[0] = 1;
                info[1] = combinacion[1][0];
                info[2] = combinacion[1][1];
                break;
            }
            else if(matriz_juego[combinacion[0][0]][combinacion[0][1]] == 0 &&
                    matriz_juego[combinacion[1][0]][combinacion[1][1]] == 1 &&
                    matriz_juego[combinacion[2][0]][combinacion[2][1]] == 1){
                info[0] = 1;
                info[1] = combinacion[0][0];
                info[2] = combinacion[0][1];
                break;
            }
        }

        return info;
    }


    public boolean estaCasillaDisponible(int fila, int columna){

        return matriz_juego[fila][columna] == 0;
    }

    public boolean estaMatrizLlena(){

        for (int i = 0; i <= 2; i++) {
            for (int j = 0; j <= 2; j++) {
                if(matriz_juego[i][j] == 0)
                    return false;
            }
        }
        return true;
    }

    public int comprobarEstado(){

        for (int[][] combinacion: combinaciones) {


                if(matriz_juego[combinacion[0][0]][combinacion[0][1]] == 1 &&
                   matriz_juego[combinacion[1][0]][combinacion[1][1]] == 1 &&
                   matriz_juego[combinacion[2][0]][combinacion[2][1]] == 1){
                    return 1; // gano uno
                }

                else if(matriz_juego[combinacion[0][0]][combinacion[0][1]] == 2 &&
                        matriz_juego[combinacion[1][0]][combinacion[1][1]] == 2 &&
                        matriz_juego[combinacion[2][0]][combinacion[2][1]] == 2){
                    return 2; // gano dos
                }
        }




        return 0;
    }


}
