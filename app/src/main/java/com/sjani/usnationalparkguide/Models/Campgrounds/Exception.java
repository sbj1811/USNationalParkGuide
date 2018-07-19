
package com.sjani.usnationalparkguide.Models.Campgrounds;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Exception implements Parcelable
{

    @SerializedName("exceptionHours")
    @Expose
    private ExceptionHours exceptionHours;
    @SerializedName("startDate")
    @Expose
    private String startDate;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("endDate")
    @Expose
    private String endDate;
    public final static Parcelable.Creator<Exception> CREATOR = new Creator<Exception>() {


        @SuppressWarnings({
            "unchecked"
        })
        public Exception createFromParcel(Parcel in) {
            return new Exception(in);
        }

        public Exception[] newArray(int size) {
            return (new Exception[size]);
        }

    }
    ;

    protected Exception(Parcel in) {
        this.exceptionHours = ((ExceptionHours) in.readValue((ExceptionHours.class.getClassLoader())));
        this.startDate = ((String) in.readValue((String.class.getClassLoader())));
        this.name = ((String) in.readValue((String.class.getClassLoader())));
        this.endDate = ((String) in.readValue((String.class.getClassLoader())));
    }

    public Exception() {
    }

    public ExceptionHours getExceptionHours() {
        return exceptionHours;
    }

    public void setExceptionHours(ExceptionHours exceptionHours) {
        this.exceptionHours = exceptionHours;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(exceptionHours);
        dest.writeValue(startDate);
        dest.writeValue(name);
        dest.writeValue(endDate);
    }

    public int describeContents() {
        return  0;
    }

}
