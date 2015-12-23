package resturantapp.com.resturantapp.dto;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Akhil Soni on 22-12-2015.
 */
public class RestaurantItem {

    @SerializedName("status")
    public Status status;

    @SerializedName("data")
    public List<Restaurant> resturantList;

    public class Status {
        public int rcode;
        public String message;
    }

    public class Restaurant {
        public String SubFranchiseID;
        public String OutletID;
        public String OutletName;
        public String BrandID;
        public String Address;
        public String NeighbourhoodID;
        public String CityID;
        public String Email;
        public String Timings;
        public String CityRank;
        public double Latitude;
        public double Longitude;
        public String Pincode;
        public String Landmark;
        public String Streetname;

        public String OutletURL;
        public String NumCoupons;
        public String NeighbourhoodName;
        public String PhoneNumber;
        public String CityName;
        public String Distance;
        public String LogoURL;
        public String CoverURL;


        public double nearestLocation;

        public double getNearestLocation() {
            return nearestLocation;
        }

        public void setNearestLocation(double nearestLocation) {
            this.nearestLocation = nearestLocation;
        }


        @SerializedName("Categories")
        public List<Categories> categoryList;

        public class Categories {
            public String OfflineCategoryID;
            public String Name;
            public String ParentCategoryID;
            public String CategoryType;
        }
    }


}
