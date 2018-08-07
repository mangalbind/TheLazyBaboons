package rohit.bman.thelazybaboons;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import rohit.bman.thelazybaboons.models.Holiday;

public class HolidayAdapter extends RecyclerView.Adapter<HolidayAdapter.HolidayViewHolder> {

    List<Holiday> holidays;

    public HolidayAdapter(List<Holiday> holidays) {
        this.holidays = holidays;
    }

    public void updateData(List<Holiday> holidays) {
        this.holidays = holidays;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public HolidayViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.holiday_list_item, parent, false);

        return new HolidayViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HolidayViewHolder holder, int position) {

        Holiday holiday = holidays.get(position);

        if (holiday.getImage() != null) {

            Glide.with(holder.ivImage.getContext())
                    .load(holiday.getImage())
                    .into(holder.ivImage);

        }

        holder.tvDate.setText(holiday.getDate());
        holder.tvTitle.setText(holiday.getTitle());
        holder.tvDescription.setText(holiday.getDescription());

    }

    @Override
    public int getItemCount() {
        return holidays.size();
    }

    class HolidayViewHolder extends RecyclerView.ViewHolder {

        TextView tvTitle, tvDescription, tvDate;
        ImageView ivImage;

        public HolidayViewHolder(View itemView) {
            super(itemView);

            tvTitle = itemView.findViewById(R.id.tv_title_holiday_list_item);
            tvDescription = itemView.findViewById(R.id.tv_description_holiday_list_item);
            tvDate = itemView.findViewById(R.id.tv_date_holiday_list_item);
            ivImage = itemView.findViewById(R.id.iv_image_holiday_list_item);

        }
    }
}

