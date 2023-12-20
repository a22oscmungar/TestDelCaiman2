package com.example.testdelcaimanv2;

import java.util.List;

public class Caimanes{

    private List<Caiman> caimanes;

    public Caimanes(){

    }

    public static class Caiman {
        private String nombre;
        private String rango;
        private int aciertos;

        public Caiman(){

        }

        public String getNombre() {
            return nombre;
        }

        public void setNombre(String nombre) {
            this.nombre = nombre;
        }

        public String getRango() {
            return rango;
        }

        public void setRango(String rango) {
            this.rango = rango;
        }

        public int getAciertos() {
            return aciertos;
        }

        public void setAciertos(int aciertos) {
            this.aciertos = aciertos;
        }
    }

}

