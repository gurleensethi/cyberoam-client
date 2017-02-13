package app.com.thetechnocafe.cyberoamclient.Adapters;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import app.com.thetechnocafe.cyberoamclient.R;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by gurleensethi on 13/02/17.
 */

public class ColorPickerRecyclerAdapter extends RecyclerView.Adapter<ColorPickerRecyclerAdapter.ColorPickerViewHolder> {

    private int[] colorList = {R.color.colorPrimary, R.color.colorPrimaryDark, R.color.md_red_500, R.color.md_blue_500,
            R.color.md_deep_orange_500, R.color.md_orange_500, R.color.md_pink_500, R.color.md_yellow_500, R.color.md_amber_500,
            R.color.md_brown_500, R.color.md_cyan_500, R.color.md_green_500, R.color.md_grey_500, R.color.md_indigo_500, R.color.md_lime_500,
            R.color.md_purple_500, R.color.md_teal_500, R.color.md_light_blue_500, R.color.md_deep_purple_500};
    private Context mContext;

    public ColorPickerRecyclerAdapter(Context context) {
        mContext = context;
    }

    //View Holder
    class ColorPickerViewHolder extends RecyclerView.ViewHolder {
        private int colorCode;
        @BindView(R.id.color_circle_view)
        View mCircleView;

        public ColorPickerViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
        }

        public void bindData(int position) {
            //Save the color
            colorCode = colorList[position];
            //Change the background
            ((GradientDrawable) mCircleView.getBackground()).setColor(mContext.getResources().getColor(colorList[position]));
        }
    }

    @Override
    public ColorPickerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_color_picker, parent, false);
        return new ColorPickerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ColorPickerViewHolder holder, int position) {
        holder.bindData(position);
    }

    @Override
    public int getItemCount() {
        return colorList.length;
    }

}
