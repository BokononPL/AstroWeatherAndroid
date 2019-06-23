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
            StringBuilder msg = new StringBuilder();
            msg.append("\nall: ");
            msg.append(all);
            return msg.toString();
        }
    }

    public class Coord {

        public Double lon;
        public Double lat;

        public Coord() {
        }

        @Override
        public String toString() {
            StringBuilder msg = new StringBuilder();
            msg.append("\nlon: ");
            msg.append(lon);
            msg.append("\nlat: ");
            msg.append(lat);
            return msg.toString();
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
            StringBuilder msg = new StringBuilder();
            msg.append("\ntemp: ");
            msg.append(temp);
            msg.append("\npressure: ");
            msg.append(pressure);
            msg.append("\nhumidity: ");
            msg.append(humidity);
            msg.append("\ntempMin: ");
            msg.append(tempMin);
            msg.append("\ntempMax: ");
            msg.append(tempMax);
            return msg.toString();
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
            StringBuilder msg = new StringBuilder();
            msg.append("\n3h: ");
            msg.append(_3h);
            msg.append("\n1h: ");
            msg.append(_1h);
            return msg.toString();
        }
    }

    public class Snow {

        @SerializedName("3h")
        public Double _3h;
        public Snow() {
        }

        @Override
        public String toString() {
            StringBuilder msg = new StringBuilder();
            msg.append("\n3h: ");
            msg.append(_3h);
            return msg.toString();
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
            StringBuilder msg = new StringBuilder();
            msg.append("\ntype: ");
            msg.append(type);
            msg.append("\nid: ");
            msg.append(id);
            msg.append("\nmessage: ");
            msg.append(message);
            msg.append("\ncountry: ");
            msg.append(country);
            msg.append("\nsunrise: ");
            msg.append(sunrise);
            msg.append("\nsunset: ");
            msg.append(sunset);
            return msg.toString();
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
            StringBuilder msg = new StringBuilder();
            msg.append("\nid: ");
            msg.append(id);
            msg.append("\nmain: ");
            msg.append(main);
            msg.append("\ndescription: ");
            msg.append(description);
            msg.append("\nicon: ");
            msg.append(icon);
            return msg.toString();
        }
    }

    public class Wind {

        public Double speed;
        public Integer deg;

        public Wind() {
        }

        @Override
        public String toString() {
            StringBuilder msg = new StringBuilder();
            msg.append("\nspeed: ");
            msg.append(speed);
            msg.append("\ndeg: ");
            msg.append(deg);
            return msg.toString();
        }
    }

    @Override
    public String toString() {
        StringBuilder msg = new StringBuilder();
        msg.append("\nbase: ");
        msg.append(base);
        msg.append("\nvisibility: ");
        msg.append(visibility);
        msg.append("\ndt: ");
        msg.append(dt);
        msg.append("\nid: ");
        msg.append(id);
        msg.append("\nname: ");
        msg.append(name);
        msg.append("\ncod: ");
        msg.append(cod);
        msg.append("\n------Coord------");
        msg.append(coord);
        msg.append("\n------Weather------");
        msg.append(weather.get(0));
        msg.append("\n------Main------");
        msg.append(main);
        msg.append("\n------Wind------");
        msg.append(wind);
        msg.append("\n------Rain------");
        msg.append(rain);
        msg.append("\n------Snow------");
        msg.append(snow);
        msg.append("\n------Clouds------");
        msg.append(clouds);
        msg.append("\n------Sys------");
        msg.append(sys);
        return msg.toString();
    }
}
