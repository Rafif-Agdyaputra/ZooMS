package com.agdyaputra.zooms;

import java.io.Serializable;

public class PlaceModel implements Serializable {

    String place_id, place_name, habitat;

    public PlaceModel(){

    }

    public PlaceModel(String Place_Id,String Place_name, String Habitat) {
        this.place_id = Place_Id;
        this.place_name = Place_name;
        this.habitat = Habitat;
    }

    public String getPlace_id() {
        return place_id;
    }

    public void setPlace_id(String place_id) {
        this.place_id = place_id;
    }

    public String getPlace_name() {
        return place_name;
    }

    public void setPlace_name(String place_name) {
        this.place_name = place_name;
    }

    public String getHabitat() {
        return habitat;
    }

    public void setHabitat(String habitat) {
        this.habitat = habitat;
    }
}
