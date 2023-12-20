package com.example.testdelcaimanv2;


import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;


public interface CaimanAPI {

    @GET("/getPreguntas")
    Call<List<Preguntas.Pregunta>> getPreguntas();
    @POST("/insertarCaiman")
    Call<Void> insertCaiman(@Body Caimanes.Caiman caiman);
    @GET("/getCaimanes")
    Call<List<Caimanes.Caiman>> getCaimanes();
}
