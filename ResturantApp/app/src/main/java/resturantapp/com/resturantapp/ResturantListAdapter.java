package resturantapp.com.resturantapp;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.android.volley.toolbox.NetworkImageView;
import java.util.List;
import resturantapp.com.resturantapp.dto.RestaurantItem;
import resturantapp.com.resturantapp.utils.Utility;
import resturantapp.com.resturantapp.utils.VolleyHelper;

/**
 * Created by Akhil Soni on 22-12-2015.
 */
public class ResturantListAdapter extends RecyclerView.Adapter<ResturantListAdapter.ItemHolder> {

    private LayoutInflater mInflater;
    private List<RestaurantItem.Restaurant> mResturantList;

    public ResturantListAdapter(Context context, List<RestaurantItem.Restaurant> list) {
        mInflater = LayoutInflater.from(context);
        mResturantList = list;
    }

    @Override
    public ItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.adapter_resturant_item, parent, false);
        return new ItemHolder(view);
    }

    @Override
    public void onBindViewHolder(ItemHolder holder, int position) {
        RestaurantItem.Restaurant resturant = mResturantList.get(position);

        holder.mResturantName.setText(resturant.OutletName);
        holder.mResturantImage.setImageUrl(resturant.LogoURL, VolleyHelper.getInstance(mInflater.getContext()).getImageLoader());
        holder.mTxtLocation.setText(resturant.NeighbourhoodName);
        holder.mTxtDistance.setText(String.format(mInflater.getContext().getResources().getString(R.string.distance_away),Utility.convertKmToMeter(resturant.getNearestLocation())));
    }

    @Override
    public int getItemCount() {
        return mResturantList.size();
    }

    public static class ItemHolder extends RecyclerView.ViewHolder {

        private NetworkImageView mResturantImage;
        private TextView mResturantName;
        private TextView mTxtLocation;
        private TextView mTxtDistance;

        public ItemHolder(View itemView) {
            super(itemView);
            mResturantImage = (NetworkImageView) itemView.findViewById(R.id.img_resturant);
            mResturantName = (TextView) itemView.findViewById(R.id.txt_resturant_name);
            mTxtLocation = (TextView) itemView.findViewById(R.id.txt_location);
            mTxtDistance =(TextView)itemView.findViewById(R.id.txt_distance);

        }
    }
}
