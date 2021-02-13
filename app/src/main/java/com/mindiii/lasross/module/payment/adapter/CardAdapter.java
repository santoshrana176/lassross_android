package com.mindiii.lasross.module.payment.adapter;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.daimajia.swipe.SwipeLayout;
import com.mindiii.lasross.R;
import com.mindiii.lasross.base.ClickListener;
import com.mindiii.lasross.module.payment.model.StripeSaveCardResponce;

import java.util.List;
import java.util.Objects;

public class CardAdapter extends RecyclerView.Adapter<CardAdapter.ViewHolder> {

    private List<StripeSaveCardResponce.DataBean> cardList;
    private Context context;
    private ClickListener.CardClickListener cardClickListener;
    private int selectedPosition = -1;
    private String cardSelection;
    private String crd_id = "";
    private long mLastClickTime = 0;
    private ViewHolder mHolder = null;

    public CardAdapter(Context context, List<StripeSaveCardResponce.DataBean> cardList, ClickListener.CardClickListener cardClickListener, String cardSelection) {
        this.cardList = cardList;
        this.context = context;
        this.cardClickListener = cardClickListener;
        this.cardSelection = cardSelection;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_list_view, parent, false);
        return new ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        StripeSaveCardResponce.DataBean dataBean = cardList.get(position);

        holder.tvCardNumberShow.setText(dataBean.getBrand());
        holder.tvExpDateShow.setText("**** **** **** " + dataBean.getLast4());
        holder.swipe.addDrag(SwipeLayout.DragEdge.Right, holder.swipe.findViewById(R.id.delete_post));
        holder.swipe.addSwipeListener(new SwipeLayout.SwipeListener() {
            @Override
            public void onStartOpen(SwipeLayout layout) {

               /* if (mHolder != holder && mHolder != null) {
                    mHolder.itemView.findViewById(R.id.swipe).animate();
                }
                mHolder = holder;*/
              //  SwipeLayout.Status st = holder.swipe.getOpenStatus();

            }

            @Override
            public void onOpen(SwipeLayout layout) {

            }

            @Override
            public void onStartClose(SwipeLayout layout) {

            }

            @Override
            public void onClose(SwipeLayout layout) {

            }

            @Override
            public void onUpdate(SwipeLayout layout, int leftOffset, int topOffset) {

            }

            @Override
            public void onHandRelease(SwipeLayout layout, float xvel, float yvel) {
                if (mHolder !=null && mHolder.swipe.getOpenStatus() == SwipeLayout.Status.Open) {
                    mHolder.swipe.close();
                }
                mHolder = holder;
            }
        });
        holder.radioCardCheckBtn.setTag(position);
        holder.radioCardCheckBtn.setChecked(position == selectedPosition);
        if (cardSelection.equals("CardSelected")) {
            holder.radioCardCheckBtn.setVisibility(View.VISIBLE);
        } else {
            holder.radioCardCheckBtn.setVisibility(View.GONE);
        }


    }

    @Override
    public int getItemCount() {
        return cardList.size();
    }

    @SuppressLint("SetTextI18n")
    private void showCustomAlert(Context context, final int position) {
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        dialog.setContentView(R.layout.custom_dilog);
        TextView tvMessages = dialog.findViewById(R.id.tvMessages);
        tvMessages.setText("Are you sure you want to delete card?");
        TextView tvPopupNo = dialog.findViewById(R.id.tvPopupNo);
        tvPopupNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        TextView tvPopupOk = dialog.findViewById(R.id.tvPopupOk);
        tvPopupOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cardClickListener.deleteOnClick(position);
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView tvCardNumberShow, tvExpDateShow;
        private SwipeLayout swipe;
        private LinearLayout delete_post;
        private RelativeLayout rlClickCheck;
        private CheckBox radioCardCheckBtn;

        ViewHolder(View itemView) {
            super(itemView);
            swipe = itemView.findViewById(R.id.swipe);
            tvCardNumberShow = itemView.findViewById(R.id.tvCardNumberShow);
            tvExpDateShow = itemView.findViewById(R.id.tvExpDateShow);
            delete_post = itemView.findViewById(R.id.delete_post);
            rlClickCheck = itemView.findViewById(R.id.rlClickCheck);
            radioCardCheckBtn = itemView.findViewById(R.id.radioCardCheckBtn);
            delete_post.setOnClickListener(this);
            rlClickCheck.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.delete_post:
                    if (SystemClock.elapsedRealtime() - mLastClickTime < 1000) {
                        return;
                    }
                    mLastClickTime = SystemClock.elapsedRealtime();
                    showCustomAlert(context, getAdapterPosition());
                    break;
                case R.id.rlClickCheck:
                    String cId = cardList.get(getAdapterPosition()).getId();
                    selectedPosition = getAdapterPosition();
                    notifyDataSetChanged();
                    cardClickListener.setCardDataOnClick(getAdapterPosition(), cId);
                    break;
            }
        }
    }
}