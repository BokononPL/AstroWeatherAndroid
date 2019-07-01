package com.astroweather;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "locations")
public class Location {
    @NonNull
    private String city;
    @NonNull
    private String country;
    @PrimaryKey(autoGenerate = true)
    @NonNull
    private Integer id;

    private String data;

    @Override
    public String toString() {
        return "Location{" +
                "city='" + city + '\'' +
                ", country='" + country + '\'' +
                ", id=" + id +
                ", data='" + data + '\'' +
                '}';
    }

    public Location(@NonNull String city, @NonNull String country, String data) {
        this.city = city;
        this.country = country;
        this.data = data;
    }

    @NonNull
    public String getCity() {
        return city;
    }

    public void setCity(@NonNull String city) {
        this.city = city;
    }

    @NonNull
    public String getCountry() {
        return country;
    }

    public void setCountry(@NonNull String country) {
        this.country = country;
    }

    @NonNull
    public Integer getId() {
        return id;
    }

    public void setId(@NonNull Integer id) {
        this.id = id;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
