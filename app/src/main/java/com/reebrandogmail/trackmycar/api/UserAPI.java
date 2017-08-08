package com.reebrandogmail.trackmycar.api;

import com.reebrandogmail.trackmycar.model.User;

import java.util.List;

import retrofit2.http.GET;
import rx.Observable;

/**
 * Created by renanribeirobrando on 25/07/17.
 */

public interface UserAPI {

    @GET("/v2/58b9b1740f0000b614f09d2f")
    //@GET("/v2/5979d2aa110000ab039edce5")
    Observable<List<User>> getUsers();
}
