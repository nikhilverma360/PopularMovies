package com.nikhilverma360.udacitypopularmovies.api.model;

import com.nikhilverma360.udacitypopularmovies.database.entity.CastEntity;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CastResponse {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("cast")
    @Expose
    private List<CastEntity> cast;

    public CastResponse(Integer id, List<CastEntity> cast) {
        this.id = id;
        this.cast = cast;
    }

    public Integer getId() {
        return id;
    }

    public List<CastEntity> getCast() {
        return cast;
    }

}
