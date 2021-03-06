package com.example.devin.flipper.view;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.devin.flipper.R;

public class alreadySoldAdapter extends RecyclerView.Adapter<alreadySoldAdapter.alreadySoldViewHolder> {

    private alreadySoldAdapter.OnItemClickListener mListener;
    private Context mContext;
    private Cursor mCursor;

    public interface OnItemClickListener {
        void onItemClick( int position );

        void onDeleteClick(int position, String recipeName, String recipeId);
    }

    public void setOnItemClickListener( alreadySoldAdapter.OnItemClickListener listener ) { mListener = listener; }

    public alreadySoldAdapter( Context context, Cursor cursor ) {
        mContext = context;
        mCursor = cursor;
    }

    public static class alreadySoldViewHolder extends RecyclerView.ViewHolder {
        public TextView mRecipeIdView;
        public TextView mTextView1;
//        public ImageView mDeleteImage;
        public TextView mItemNameText, mDateSold, mPriceSold, mPriceProfit;

        public alreadySoldViewHolder( final View itemView, final alreadySoldAdapter.OnItemClickListener listener ) {
            super( itemView );
     //       mRecipeIdView = itemView.findViewById( R.id.recipeId );
     //       mTextView1 = itemView.findViewById( R.id.recipe );
     //       mDeleteImage = itemView.findViewById( R.id.image_delete );
            mItemNameText = itemView.findViewById(R.id.itemNameText);
            mDateSold = itemView.findViewById(R.id.dateSold);
            mPriceSold = itemView.findViewById(R.id.priceSold);
            mPriceProfit = itemView.findViewById(R.id.priceProfit);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick( View view ) {
                    if ( listener != null ) {
                        int position = getAdapterPosition();
                        System.out.println( "position: " + position );
                        if ( position != RecyclerView.NO_POSITION ) {
                            listener.onItemClick( position );
                        }
                    }
                }
            });
            /*
            mDeleteImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick( View view ) {
                    if ( listener != null ) {
                        int position = getAdapterPosition();
                        String recipeName = mTextView1.getText().toString();
                        String recipeId = mRecipeIdView.getText().toString();
                        listener.onDeleteClick( position, recipeName, recipeId );
                    }
                }
            });
            */
        }
    }

    @Override
    public alreadySoldAdapter.alreadySoldViewHolder onCreateViewHolder(ViewGroup parent, int viewType ) {
        LayoutInflater inflater = LayoutInflater.from( mContext );
        View view = inflater.inflate( R.layout.activity_already_sold_item, parent, false );
        return new alreadySoldAdapter.alreadySoldViewHolder( view, mListener );
    }

    @Override
    public void onBindViewHolder( alreadySoldAdapter.alreadySoldViewHolder holder, int position ) {
        if ( !mCursor.moveToPosition( position ) ) {
            return;
        }

        final String itemName = mCursor.getString( mCursor.getColumnIndex("itemName"));
        holder.mItemNameText.setText(itemName);

        final String dateSold = mCursor.getString(mCursor.getColumnIndex("dateSold"));
        holder.mDateSold.setText(dateSold);

        final String priceSold = mCursor.getString(mCursor.getColumnIndex("priceSold"));
        holder.mPriceSold.setText(priceSold);

        final String priceProfit = mCursor.getString(mCursor.getColumnIndex("priceProfit"));
        holder.mPriceProfit.setText(priceProfit);

    }

    @Override
    public int getItemCount() {
        return mCursor.getCount();
    }


}
