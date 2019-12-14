package com.example.devin.flipper.view;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.devin.flipper.R;
import com.example.devin.flipper.database.DatabaseHelper;

public class allItemsAdapter extends RecyclerView.Adapter<allItemsAdapter.allItemsViewHolder> {

    private allItemsAdapter.OnItemClickListener mListener;
    private Context mContext;
    private Cursor mCursor;
    public DatabaseHelper mDatabaseHelper;

    private static final String TAG = "allItemsAdapter";

    public interface OnItemClickListener {
        void onItemClick( int position );

        void onDeleteClick(int position, String recipeName, String recipeId);
    }

    public void setOnItemClickListener( allItemsAdapter.OnItemClickListener listener ) { mListener = listener; }

    public allItemsAdapter( Context context, Cursor cursor ) {
        mContext = context;
        mCursor = cursor;
        mDatabaseHelper = new DatabaseHelper( context.getApplicationContext() );
    }

    public static class allItemsViewHolder extends RecyclerView.ViewHolder {
        public TextView mItemName, mCurrentDate, mPurchasePrice, mProjValue, mProjProfit;

        public allItemsViewHolder( final View itemView, final allItemsAdapter.OnItemClickListener listener ) {
            super( itemView );
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
    public allItemsAdapter.allItemsViewHolder onCreateViewHolder(ViewGroup parent, int viewType ) {
        LayoutInflater inflater = LayoutInflater.from( mContext );
        View view = inflater.inflate( R.layout.activity_all_items_item, parent, false );
        return new allItemsAdapter.allItemsViewHolder( view, mListener );
    }

    @Override
    public void onBindViewHolder( allItemsAdapter.allItemsViewHolder holder, int position ) {
        if ( !mCursor.moveToPosition( position ) ) {
            return;
        }

        final String itemName = mCursor.getString( mCursor.getColumnIndex("itemName"));
        holder.mItemName.setText(itemName);

        Log.d( TAG, "itemName value is: " + itemName );

        holder.mItemName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Cursor data = mDatabaseHelper.getItemId(itemName);
                int itemId = -1;
                while (data.moveToNext()) {
                    itemId = data.getInt(0);
                }

                Log.d( TAG, "onClick: clicked on: " + mCursor.getPosition() );
                Intent intent = new Intent( mContext, itemSold.class ); //we're re-routing to IngredientInfo instead...
                intent.putExtra("itemId", itemId );
                intent.putExtra( "itemName", itemName );
                mContext.startActivity( intent );

            }

        });

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
