package com.sjani.usnationalparkguide.Models.Weather;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class CurrentWeather implements Parcelable {

    public final static Parcelable.Creator<CurrentWeather> CREATOR = new Creator<CurrentWeather>() {


        @SuppressWarnings({
                "unchecked"
        })
        public CurrentWeather createFromParcel(Parcel in) {
            return new CurrentWeather(in);
        }

        public CurrentWeather[] newArray(int size) {
            return (new CurrentWeather[size]);
        }

    };
    @SerializedName("coord")
    @Expose
    private Coord coord;
    @SerializedName("weather")
    @Expose
    private List<Weather> weather = null;
    @SerializedName("base")
    @Expose
    private String base;
    @SerializedName("main")
    @Expose
    private Main main;
    @SerializedName("visibility")
    @Expose
    private Integer visibility;
    @SerializedName("wind")
    @Expose
    private Wind wind;
    @SerializedName("clouds")
    @Expose
    private Clouds clouds;
    @SerializedName("dt")
    @Expose
    private Integer dt;
    @SerializedName("sys")
    @Expose
    private Sys sys;
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("cod")
    @Expose
    private Integer cod;

    protected CurrentWeather(Parcel in) {
        this.coord = in.readByte() == 0x00 ? null : ((Coord) in.readValue((Coord.class.getClassLoader())));
        if (in.readByte() == 0x01) {
            this.weather = new ArrayList<>();
            in.readList(this.weather, (com.sjani.usnationalparkguide.Models.Weather.Weather.class.getClassLoader()));
        } else {
            this.weather = null;
        }
        this.base = in.readByte() == 0x00 ? null : ((String) in.readValue((String.class.getClassLoader())));
        this.main = in.readByte() == 0x00 ? null : ((Main) in.readValue((Main.class.getClassLoader())));
        this.visibility = in.readByte() == 0x00 ? null : ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.wind = in.readByte() == 0x00 ? null : ((Wind) in.readValue((Wind.class.getClassLoader())));
        this.clouds = in.readByte() == 0x00 ? null : ((Clouds) in.readValue((Clouds.class.getClassLoader())));
        this.dt = in.readByte() == 0x00 ? null : ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.sys = in.readByte() == 0x00 ? null : ((Sys) in.readValue((Sys.class.getClassLoader())));
        this.id = in.readByte() == 0x00 ? null : ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.name = in.readByte() == 0x00 ? null : ((String) in.readValue((String.class.getClassLoader())));
        this.cod = in.readByte() == 0x00 ? null : ((Integer) in.readValue((Integer.class.getClassLoader())));
    }

    public CurrentWeather() {
    }

    public Coord getCoord() {
        return coord;
    }

    public void setCoord(Coord coord) {
        this.coord = coord;
    }

    public List<Weather> getWeather() {
        return weather;
    }

    public void setWeather(List<Weather> weather) {
        this.weather = weather;
    }

    public String getBase() {
        return base;
    }

    public void setBase(String base) {
        this.base = base;
    }

    public Main getMain() {
        return main;
    }

    public void setMain(Main main) {
        this.main = main;
    }

    public Integer getVisibility() {
        return visibility;
    }

    public void setVisibility(Integer visibility) {
        this.visibility = visibility;
    }

    public Wind getWind() {
        return wind;
    }

    public void setWind(Wind wind) {
        this.wind = wind;
    }

    public Clouds getClouds() {
        return clouds;
    }

    public void setClouds(Clouds clouds) {
        this.clouds = clouds;
    }

    public Integer getDt() {
        return dt;
    }

    public void setDt(Integer dt) {
        this.dt = dt;
    }

    public Sys getSys() {
        return sys;
    }

    public void setSys(Sys sys) {
        this.sys = sys;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getCod() {
        return cod;
    }

    public void setCod(Integer cod) {
        this.cod = cod;
    }

    public void writeToParcel(Parcel dest, int flags) {
        if (coord == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeValue(coord);
        }
        if (weather == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(weather);
        }
        if (base == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeValue(base);
        }
        if (main == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeValue(main);
        }
        if (visibility == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeValue(visibility);
        }
        if (wind == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeValue(wind);
        }
        if (clouds == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeValue(clouds);
        }
        if (dt == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeValue(dt);
        }
        if (sys == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeValue(sys);
        }
        if (id == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeValue(id);
        }
        if (name == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeValue(name);
        }
        if (cod == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeValue(cod);
        }

    }

    public int describeContents() {
        return 0;
    }

}
