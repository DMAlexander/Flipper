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

public class currentInventoryAdapter extends RecyclerView.Adapter<currentInventoryAdapter.currentInventoryViewHolder> {

    private currentInventoryAdapter.OnItemClickListener mListener;
    private Context mContext;
    private Cursor mCursor;

    public interface OnItemClickListener {
        void onItemClick( int position );

        void onDeleteClick(int position, String recipeName, String recipeId);
    }

    public void setOnItemClickListener( currentInventoryAdapter.OnItemClickListener listener ) { mListener = listener; }

    public currentInventoryAdapter( Context context, Cursor cursor ) {
        mContext = context;
        mCursor = cursor;
    }

    public static class currentInventoryViewHolder extends RecyclerView.ViewHolder {
        /*
        public TextView mRecipeIdView;
        public TextView mTextView1;
        public ImageView mDeleteImage;
        */
        public TextView mItemName, mCurrentDate, mPurchasePrice, mProjValue, mProjProfit;

        public currentInventoryViewHolder( final View itemView, final currentInventoryAdapter.OnItemClickListener listener ) {
            super( itemView );
     //       mRecipeIdView = itemView.findViewById( R.id.recipeId );
     //       mTextView1 = itemView.findViewById( R.id.recipe );
     //       mDeleteImage = itemView.findViewById( R.id.image_delete );
            mItemName = itemView.findViewById(R.id.itemNameText);
            mCurrentDate = itemView.findViewById(R.id.currentDate);
            mPurchasePrice = itemView.findViewById(R.id.purchasePrice);
            mProjValue = itemView.findViewById(R.id.projValue);
            mProjProfit = itemView.findViewById(R.id.projProfit);
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
            }); */
        }
    }

    @Override
    public currentInventoryAdapter.currentInventoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType ) {
        LayoutInflater inflater = LayoutInflater.from( mContext );
        View view = inflater.inflate( R.layout.activity_current_inventory_item, parent, false );
        return new currentInventoryAdapter.currentInventoryViewHolder( view, mListener );
    }

    @Override
    public void onBindViewHolder( currentInventoryAdapter.currentInventoryViewHolder holder, int position ) {
        if ( !mCursor.moveToPosition( position ) ) {
            return;
        }

        final String itemName = mCursor.getString( mCursor.getColumnIndex("itemName"));
        holder.mItemName.setText(itemName);

        final String currentDate = mCursor.getString(mCursor.getColumnIndex("datePurchased"));
        holder.mCurrentDate.setText(currentDate);

        final String purchasePrice = mCursor.getString(mCursor.getColumnIndex("pricePurchased"));
        holder.mPurchasePrice.setText(purchasePrice);

        final String projValue = mCursor.getString(mCursor.getColumnIndex("projValue"));
        holder.mProjValue.setText(projValue);

        final String projProfit = mCursor.getString(mCursor.getColumnIndex("projPrice"));
        holder.mProjProfit.setText(projProfit);

    }

    @Override
    public int getItemCount() {
        return mCursor.getCount();
    }
}
