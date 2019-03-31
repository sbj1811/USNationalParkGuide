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
    private Boolean internetConnectivity;
    @SerializedName("showers")
    @Expose
    private List<String> showers = null;
    @SerializedName("cellphonereception")
    @Expose
    private String cellPhoneReception;
    @SerializedName("laundry")
    @Expose
    private Boolean laundry;
    @SerializedName("amphitheater")
    @Expose
    private String amphitheater;
    @SerializedName("dumpstation")
    @Expose
    private Boolean dumpStation;
    @SerializedName("campstore")
    @Expose
    private Boolean campStore;
    @SerializedName("stafforvolunteerhostonsite")
    @Expose
    private Boolean staffOrVolunteerHostOnSite;
    @SerializedName("potableWater")
    @Expose
    private List<String> potableWater = null;
    @SerializedName("iceavailableforsale")
    @Expose
    private Boolean iceAvailableForSale;
    @SerializedName("firewoodforsale")
    @Expose
    private Boolean firewoodForSale;
    @SerializedName("ampitheater")
    @Expose
    private Boolean ampitheater;
    @SerializedName("foodstoragelockers")
    @Expose
    private String foodStorageLockers;

    protected Amenities(Parcel in) {
        this.trashRecyclingCollection = ((String) in.readValue((String.class.getClassLoader())));
        in.readList(this.toilets, (java.lang.String.class.getClassLoader()));
        this.internetConnectivity = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        in.readList(this.showers, (java.lang.String.class.getClassLoader()));
        this.cellPhoneReception = ((String) in.readValue((String.class.getClassLoader())));
        this.laundry = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        this.amphitheater = ((String) in.readValue((String.class.getClassLoader())));
        this.dumpStation = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        this.campStore = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        this.staffOrVolunteerHostOnSite = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        in.readList(this.potableWater, (java.lang.String.class.getClassLoader()));
        this.iceAvailableForSale = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        this.firewoodForSale = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        this.ampitheater = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
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

    public Boolean getInternetConnectivity() {
        return internetConnectivity;
    }

    public void setInternetConnectivity(Boolean internetConnectivity) {
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

    public Boolean getLaundry() {
        return laundry;
    }

    public void setLaundry(Boolean laundry) {
        this.laundry = laundry;
    }

    public String getAmphitheater() {
        return amphitheater;
    }

    public void setAmphitheater(String amphitheater) {
        this.amphitheater = amphitheater;
    }

    public Boolean getDumpStation() {
        return dumpStation;
    }

    public void setDumpStation(Boolean dumpStation) {
        this.dumpStation = dumpStation;
    }

    public Boolean getCampStore() {
        return campStore;
    }

    public void setCampStore(Boolean campStore) {
        this.campStore = campStore;
    }

    public Boolean getStaffOrVolunteerHostOnSite() {
        return staffOrVolunteerHostOnSite;
    }

    public void setStaffOrVolunteerHostOnSite(Boolean staffOrVolunteerHostOnSite) {
        this.staffOrVolunteerHostOnSite = staffOrVolunteerHostOnSite;
    }

    public List<String> getPotableWater() {
        return potableWater;
    }

    public void setPotableWater(List<String> potableWater) {
        this.potableWater = potableWater;
    }

    public Boolean getIceAvailableForSale() {
        return iceAvailableForSale;
    }

    public void setIceAvailableForSale(Boolean iceAvailableForSale) {
        this.iceAvailableForSale = iceAvailableForSale;
    }

    public Boolean getFirewoodForSale() {
        return firewoodForSale;
    }

    public void setFirewoodForSale(Boolean firewoodForSale) {
        this.firewoodForSale = firewoodForSale;
    }

    public Boolean getAmpitheater() {
        return ampitheater;
    }

    public void setAmpitheater(Boolean ampitheater) {
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
