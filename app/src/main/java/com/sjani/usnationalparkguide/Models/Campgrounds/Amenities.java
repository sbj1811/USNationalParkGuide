package com.sjani.usnationalparkguide.Models.Campgrounds;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Amenities implements Parcelable {

    public final static Parcelable.Creator<Amenities> CREATOR = new Creator<Amenities>() {


        @SuppressWarnings({
                "unchecked"
        })
        public Amenities createFromParcel(Parcel in) {
            return new Amenities(in);
        }

        public Amenities[] newArray(int size) {
            return (new Amenities[size]);
        }

    };
    @SerializedName("trashrecyclingcollection")
    @Expose
    private String trashRecyclingCollection;
    @SerializedName("toilets")
    @Expose
    private List<String> toilets = null;
    @SerializedName("internetconnectivity")
    @Expose
    private String internetConnectivity;
    @SerializedName("showers")
    @Expose
    private List<String> showers = null;
    @SerializedName("cellphonereception")
    @Expose
    private String cellPhoneReception;
    @SerializedName("laundry")
    @Expose
    private String laundry;
    @SerializedName("amphitheater")
    @Expose
    private String amphitheater;
    @SerializedName("dumpstation")
    @Expose
    private String dumpStation;
    @SerializedName("campstore")
    @Expose
    private String campStore;
    @SerializedName("stafforvolunteerhostonsite")
    @Expose
    private String staffOrVolunteerHostOnSite;
    @SerializedName("potableWater")
    @Expose
    private List<String> potableWater = null;
    @SerializedName("iceavailableforsale")
    @Expose
    private String iceAvailableForSale;
    @SerializedName("firewoodforsale")
    @Expose
    private String firewoodForSale;
    @SerializedName("ampitheater")
    @Expose
    private String ampitheater;
    @SerializedName("foodstoragelockers")
    @Expose
    private String foodStorageLockers;

    protected Amenities(Parcel in) {
        this.trashRecyclingCollection = ((String) in.readValue((String.class.getClassLoader())));
        in.readList(this.toilets, (java.lang.String.class.getClassLoader()));
        this.internetConnectivity = ((String) in.readValue((String.class.getClassLoader())));
        in.readList(this.showers, (java.lang.String.class.getClassLoader()));
        this.cellPhoneReception = ((String) in.readValue((String.class.getClassLoader())));
        this.laundry = ((String) in.readValue((String.class.getClassLoader())));
        this.amphitheater = ((String) in.readValue((String.class.getClassLoader())));
        this.dumpStation = ((String) in.readValue((String.class.getClassLoader())));
        this.campStore = ((String) in.readValue((String.class.getClassLoader())));
        this.staffOrVolunteerHostOnSite = ((String) in.readValue((String.class.getClassLoader())));
        in.readList(this.potableWater, (java.lang.String.class.getClassLoader()));
        this.iceAvailableForSale = ((String) in.readValue((String.class.getClassLoader())));
        this.firewoodForSale = ((String) in.readValue((String.class.getClassLoader())));
        this.ampitheater = ((String) in.readValue((String.class.getClassLoader())));
        this.foodStorageLockers = ((String) in.readValue((String.class.getClassLoader())));
    }

    public Amenities() {
    }

    public String getTrashRecyclingCollection() {
        return trashRecyclingCollection;
    }

    public void setTrashRecyclingCollection(String trashRecyclingCollection) {
        this.trashRecyclingCollection = trashRecyclingCollection;
    }

    public List<String> getToilets() {
        return toilets;
    }

    public void setToilets(List<String> toilets) {
        this.toilets = toilets;
    }

    public String getInternetConnectivity() {
        return internetConnectivity;
    }

    public void setInternetConnectivity(String internetConnectivity) {
        this.internetConnectivity = internetConnectivity;
    }

    public List<String> getShowers() {
        return showers;
    }

    public void setShowers(List<String> showers) {
        this.showers = showers;
    }

    public String getCellPhoneReception() {
        return cellPhoneReception;
    }

    public void setCellPhoneReception(String cellPhoneReception) {
        this.cellPhoneReception = cellPhoneReception;
    }

    public String getLaundry() {
        return laundry;
    }

    public void setLaundry(String laundry) {
        this.laundry = laundry;
    }

    public String getAmphitheater() {
        return amphitheater;
    }

    public void setAmphitheater(String amphitheater) {
        this.amphitheater = amphitheater;
    }

    public String getDumpStation() {
        return dumpStation;
    }

    public void setDumpStation(String dumpStation) {
        this.dumpStation = dumpStation;
    }

    public String getCampStore() {
        return campStore;
    }

    public void setCampStore(String campStore) {
        this.campStore = campStore;
    }

    public String getStaffOrVolunteerHostOnSite() {
        return staffOrVolunteerHostOnSite;
    }

    public void setStaffOrVolunteerHostOnSite(String staffOrVolunteerHostOnSite) {
        this.staffOrVolunteerHostOnSite = staffOrVolunteerHostOnSite;
    }

    public List<String> getPotableWater() {
        return potableWater;
    }

    public void setPotableWater(List<String> potableWater) {
        this.potableWater = potableWater;
    }

    public String getIceAvailableForSale() {
        return iceAvailableForSale;
    }

    public void setIceAvailableForSale(String iceAvailableForSale) {
        this.iceAvailableForSale = iceAvailableForSale;
    }

    public String getFirewoodForSale() {
        return firewoodForSale;
    }

    public void setFirewoodForSale(String firewoodForSale) {
        this.firewoodForSale = firewoodForSale;
    }

    public String getAmpitheater() {
        return ampitheater;
    }

    public void setAmpitheater(String ampitheater) {
        this.ampitheater = ampitheater;
    }

    public String getFoodStorageLockers() {
        return foodStorageLockers;
    }

    public void setFoodStorageLockers(String foodStorageLockers) {
        this.foodStorageLockers = foodStorageLockers;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(trashRecyclingCollection);
        dest.writeList(toilets);
        dest.writeValue(internetConnectivity);
        dest.writeList(showers);
        dest.writeValue(cellPhoneReception);
        dest.writeValue(laundry);
        dest.writeValue(amphitheater);
        dest.writeValue(dumpStation);
        dest.writeValue(campStore);
        dest.writeValue(staffOrVolunteerHostOnSite);
        dest.writeList(potableWater);
        dest.writeValue(iceAvailableForSale);
        dest.writeValue(firewoodForSale);
        dest.writeValue(ampitheater);
        dest.writeValue(foodStorageLockers);
    }

    public int describeContents() {
        return 0;
    }

}
