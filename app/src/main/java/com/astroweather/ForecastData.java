package com.astroweather;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ForecastData {

    @Override
    public String toString() {
        return "\n###------ForecastData------###" +
                "\nmessage=" + message +
                "  cnt=" + cnt +
                "  cod=" + cod +
                "\n------city------\n" + city +
                "\n------list------\n" + list
                ;
    }

    public ForecastData() {
    }

    public City city;
    public Integer cnt;
    public String cod;
    public List<X> list = null;
    public Double message;

    public class City {
        public City() {
        }

        @Override
        public String toString() {
            return "City{" +
                    "coord=" + coord +
                    ", country='" + country + '\'' +
                    ", id=" + id +
                    ", name='" + name + '\'' +
                    ", population=" + population +
                    ", timezone=" + timezone +
                    '}';
        }

        public Coord coord;
        public String country;
        public Integer id;
        public String name;
        public Integer population;
        public Integer timezone;

        public class Coord {
            public Coord() {
            }

            @Override
            public String toString() {
                return "Coord{" +
                        "lat=" + lat +
                        ", lon=" + lon +
                        '}';
            }

            public Double lat;
            public Double lon;

        }
    }

    public class X {
        public X(){
        }

        @Override
        public String toString() {
            return "\nX{" +
                    "clouds=" + clouds +
                    ", dt=" + dt +
                    ", dt_txt='" + dt_txt + '\'' +
                    ", main=" + main +
                    ", rain=" + rain +
                    ", snow=" + snow +
                    ", sys=" + sys +
                    ", weather=" + weather +
                    ", wind=" + wind +
                    '}';
        }

        public Clouds clouds;
        public Long dt;
        public String dt_txt;
        public Main main;
        public Rain rain;
        public Snow snow;
        public Sys sys;
        public List<Weather> weather;
        public Wind wind;
    }

    public class Main {
        public Main() {
        }

        @Override
        public String toString() {
            return "Main{" +
                    "grnd_level=" + grnd_level +
                    ", humidity=" + humidity +
                    ", pressure=" + pressure +
                    ", sea_level=" + sea_level +
                    ", temp=" + temp +
                    ", temp_kf=" + temp_kf +
                    ", temp_max=" + temp_max +
                    ", temp_min=" + temp_min +
                    '}';
        }

        public Double grnd_level;
        public Double humidity;
        public Double pressure;
        public Double sea_level;
        public Double temp;
        public Double temp_kf;
        public Double temp_max;
        public Double temp_min;
    }

    public class Weather {
        public Weather(){
        }

        @Override
        public String toString() {
            return "Weather{" +
                    "description='" + description + '\'' +
                    ", icon='" + icon + '\'' +
                    ", id=" + id +
                    ", main='" + main + '\'' +
                    '}';
        }

        public String description;
        public String icon;
        public Double id;
        public String main;
    }

    public class Clouds{
        public Clouds(){
        }

        @Override
        public String toString() {
            return "Clouds{" +
                    "all=" + all +
                    '}';
        }

        public Double all;
    }

    public class Rain{
        public Rain(){
        }

        @Override
        public String toString() {
            return "Rain{" +
                    "3h=" + _3h +
                    '}';
        }

        @SerializedName("3h")
        public Double _3h;
    }

    public class Sys{
        public Sys(){
        }

        @Override
        public String toString() {
            return "Sys{" +
                    "pod='" + pod + '\'' +
                    '}';
        }

        public String pod;
    }

    public class Wind{
        public Wind(){
        }

        @Override
        public String toString() {
            return "Wind{" +
                    "deg=" + deg +
                    ", speed=" + speed +
                    '}';
        }

        public Double deg;
        public Double speed;
    }

    public class Snow{
        public Snow(){
        }

        @Override
        public String toString() {
            return "Snow{" +
                    "3h=" + _3h +
                    '}';
        }

        @SerializedName("3h")
        public Double _3h;
    }

}
