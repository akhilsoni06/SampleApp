package resturantapp.com.resturantapp;

import android.location.Location;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;

import java.util.Collections;
import java.util.Comparator;

import resturantapp.com.resturantapp.dto.RestaurantItem;
import resturantapp.com.resturantapp.utils.Network;
import resturantapp.com.resturantapp.utils.Utility;

/**
 * Created by Akhil Soni on 22-12-2015.
 */
public class ResturantListFragment extends Fragment implements RestaurantManager.RestaurantsDetailDownloadListener, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    private ProgressBar mProgressbar;
    private RecyclerView mResturantList;

    private GoogleApiClient mGoogleApiClient;
    private Location mLastLocation;

    private double mCurrentLatitude;
    private double mCurrentLongitude;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_resturant_list, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        mProgressbar = (ProgressBar) view.findViewById(R.id.progressBar);
        mResturantList = (RecyclerView) view.findViewById(R.id.recycler_view);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(getActivity())
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }
        if (!Network.isAvailable(getActivity())) {
            Utility.showNoNetworkAlertForHome(getActivity());
            return;
        }

        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        mResturantList.setLayoutManager(manager);

        String baseUrl = getResources().getString(R.string.base_url);
        RestaurantManager.downloadNewsDetail(getActivity(), baseUrl, this);
    }

    @Override
    public void onStart() {
        mGoogleApiClient.connect();
        super.onStart();
    }

    @Override
    public void onDownloadSuccess(RestaurantItem restaurantItem) {
        if (!isAdded() && restaurantItem == null)
            return;

        mProgressbar.setVisibility(View.GONE);

        if (restaurantItem.resturantList != null && restaurantItem.resturantList.size() > 0)
            for (int i = 0; i < restaurantItem.resturantList.size(); i++) {
                restaurantItem.resturantList.get(i).setNearestLocation(distance(mCurrentLatitude, mCurrentLongitude, restaurantItem.resturantList.get(i).Latitude, restaurantItem.resturantList.get(i).Longitude));
            }

        if (restaurantItem.resturantList != null && restaurantItem.resturantList.size() > 0)
            mResturantList.setAdapter(new ResturantListAdapter(getActivity(), restaurantItem.resturantList));

        Collections.sort(restaurantItem.resturantList, new Comparator<RestaurantItem.Restaurant>() {
            @Override
            public int compare(RestaurantItem.Restaurant c1, RestaurantItem.Restaurant c2) {
                return Double.compare(c1.getNearestLocation(), c2.getNearestLocation());
            }
        });
        mResturantList.getAdapter().notifyDataSetChanged();
    }

    @Override
    public void onDownloadFailure() {
        if (!isAdded())
            return;
        mProgressbar.setVisibility(View.GONE);
    }

    @Override
    public void onConnected(Bundle bundle) {
        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(
                mGoogleApiClient);
        if (mLastLocation != null) {
            mCurrentLatitude = mLastLocation.getLatitude();
            mCurrentLongitude = mLastLocation.getLongitude();
        }
    }

    @Override
    public void onStop() {
        mGoogleApiClient.disconnect();
        super.onStop();
    }

    @Override
    public void onConnectionSuspended(int i) {
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
    }


    private double distance(double lat1, double lon1, double lat2, double lon2) {
        // haversine great circle distance approximation, returns meters
        double theta = lon1 - lon2;
        double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2))
                + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2))
                * Math.cos(deg2rad(theta));
        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60; // 60 nautical miles per degree of seperation
        dist = dist * 1852; // 1852 meters per nautical mile
        return (dist);
    }

    private double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }

    private double rad2deg(double rad) {
        return (rad * 180.0 / Math.PI);
    }
}
