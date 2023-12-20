package com.example.testdelcaimanv2;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Preguntas {
    public List<Pregunta> preguntas;

    public Preguntas(){

    }
    public List<Pregunta> getPreguntas(){
        return preguntas;
    }

    public static class Pregunta{
        @SerializedName("idPregunta")
        public int IDPregunta;
        @SerializedName("enunciado")
        public String enunciado;
        @SerializedName("correcta")
        public int opcionCorrecta;
        public String opcionA,opcionB,opcionC,opcionD;

        public Pregunta(){

        }

        public int getIDPregunta() {
            return IDPregunta;
        }

        public void setIDPregunta(int IDPregunta) {
            this.IDPregunta = IDPregunta;
        }

        public String getEnunciado() {
            return enunciado;
        }

        public void setEnunciado(String enunciado) {
            this.enunciado = enunciado;
        }

        public int getOpcionCorrecta() {
            return opcionCorrecta;
        }

        public void setOpcionCorrecta(int opcionCorrecta) {
            this.opcionCorrecta = opcionCorrecta;
        }

        public String getOpcionA() {
            return opcionA;
        }

        public void setOpcionA(String opcionA) {
            this.opcionA = opcionA;
        }

        public String getOpcionB() {
            return opcionB;
        }

        public void setOpcionB(String opcionB) {
            this.opcionB = opcionB;
        }

        public String getOpcionC() {
            return opcionC;
        }

        public void setOpcionC(String opcionC) {
            this.opcionC = opcionC;
        }

        public String getOpcionD() {
            return opcionD;
        }

        public void setOpcionD(String opcionD) {
            this.opcionD = opcionD;
        }
    }
}
