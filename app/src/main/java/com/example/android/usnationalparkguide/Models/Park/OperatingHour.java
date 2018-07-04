
package com.example.android.usnationalparkguide.Models.Park;

import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OperatingHour implements Parcelable
{

    @SerializedName("exceptions")
    @Expose
    private List<Exception> exceptions = null;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("standardHours")
    @Expose
    private StandardHours standardHours;
    @SerializedName("name")
    @Expose
    private String name;
    public final static Parcelable.Creator<OperatingHour> CREATOR = new Creator<OperatingHour>() {


        @SuppressWarnings({
            "unchecked"
        })
        public OperatingHour createFromParcel(Parcel in) {
            return new OperatingHour(in);
        }

        public OperatingHour[] newArray(int size) {
            return (new OperatingHour[size]);
        }

    }
    ;

    protected OperatingHour(Parcel in) {
        in.readList(this.exceptions, (com.example.android.usnationalparkguide.Models.Park.Exception.class.getClassLoader()));
        this.description = ((String) in.readValue((String.class.getClassLoader())));
        this.standardHours = ((StandardHours) in.readValue((StandardHours.class.getClassLoader())));
        this.name = ((String) in.readValue((String.class.getClassLoader())));
    }

    public OperatingHour() {
    }

    public List<Exception> getExceptions() {
        return exceptions;
    }

    public void setExceptions(List<Exception> exceptions) {
        this.exceptions = exceptions;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public StandardHours getStandardHours() {
        return standardHours;
    }

    public void setStandardHours(StandardHours standardHours) {
        this.standardHours = standardHours;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeList(exceptions);
        dest.writeValue(description);
        dest.writeValue(standardHours);
        dest.writeValue(name);
    }

    public int describeContents() {
        return  0;
    }

}
