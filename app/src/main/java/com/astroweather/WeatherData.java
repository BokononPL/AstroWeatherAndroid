package com.astroweather;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class WeatherData {

    public Coord coord;
    public List<Weather> weather = null;
    public String base;
    public Main main;
    public Integer visibility;
    public Wind wind;
    public Rain rain;
    public Snow snow;
    public Clouds clouds;
    public Integer dt;
    public Sys sys;
    public Integer id;
    public String name;
    public Integer cod;
    public WeatherData() {
    }

    public class Clouds {

        public Integer all;
        public Clouds() {
        }

        @Override
        public String toString() {
            return "Clouds{" +
                    "all=" + all +
                    '}';
        }
    }

    public class Coord {

        public Double lon;
        public Double lat;

        public Coord() {
        }

        @Override
        public String toString() {
            return "Coord{" +
                    "lon=" + lon +
                    ", lat=" + lat +
                    '}';
        }
    }

    public class Main {

        public Double temp;
        public Integer pressure;
        public Integer humidity;
        public Double tempMin;
        public Double tempMax;

        public Main() {
        }

        @Override
        public String toString() {
            return "Main{" +
                    "temp=" + temp +
                    ", pressure=" + pressure +
                    ", humidity=" + humidity +
                    ", tempMin=" + tempMin +
                    ", tempMax=" + tempMax +
                    '}';
        }
    }

    public class Rain {

        @SerializedName("3h")
        public Double _3h;
        @SerializedName("1h")
        public Double _1h;

        public Rain() {
        }

        @Override
        public String toString() {
            return "Rain{" +
                    "_3h=" + _3h +
                    ", _1h=" + _1h +
                    '}';
        }
    }

    public class Snow {

        @SerializedName("3h")
        public Double _3h;
        public Snow() {
        }

        @Override
        public String toString() {
            return "Snow{" +
                    "_3h=" + _3h +
                    '}';
        }
    }

    public class Sys {

        public Integer type;
        public Integer id;
        public Double message;
        public String country;
        public Integer sunrise;
        public Integer sunset;

        public Sys() {
        }

        @Override
        public String toString() {
            return "Sys{" +
                    "type=" + type +
                    ", id=" + id +
                    ", message=" + message +
                    ", country='" + country + '\'' +
                    ", sunrise=" + sunrise +
                    ", sunset=" + sunset +
                    '}';
        }
    }

    public class Weather {

        public Integer id;
        public String main;
        public String description;
        public String icon;

        public Weather() {
        }

        @Override
        public String toString() {
            return "Weather{" +
                    "id=" + id +
                    ", main='" + main + '\'' +
                    ", description='" + description + '\'' +
                    ", icon='" + icon + '\'' +
                    '}';
        }
    }

    public class Wind {

        public Double speed;
        public Integer deg;

        public Wind() {
        }

        @Override
        public String toString() {
            return "Wind{" +
                    "speed=" + speed +
                    ", deg=" + deg +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "\n###------WeatherData------###" +
                "\nbase=" + base +
                "  visibility=" + visibility +
                "  id=" + id +
                "  name=" + name +
                "  cod=" + cod +
                "  dt=" + dt +
                "\n------coord------\n" + coord +
                "\n------weather------\n" + weather.get(0) +
                "\n------main------\n" + main +
                "\n------wind------\n" + wind +
                "\n------rain------\n" + rain +
                "\n------snow------\n" + snow +
                "\n------clouds------\n" + clouds +
                "\n------sys------\n" + sys
                ;
    }
}
