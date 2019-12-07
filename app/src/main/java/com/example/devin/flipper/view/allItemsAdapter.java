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

public class allItemsAdapter extends RecyclerView.Adapter<allItemsAdapter.allItemsViewHolder> {

    private allItemsAdapter.OnItemClickListener mListener;
    private Context mContext;
    private Cursor mCursor;

    public interface OnItemClickListener {
        void onItemClick( int position );

        void onDeleteClick(int position, String recipeName, String recipeId);
    }

    public void setOnItemClickListener( allItemsAdapter.OnItemClickListener listener ) { mListener = listener; }

    public allItemsAdapter( Context context, Cursor cursor ) {
        mContext = context;
        mCursor = cursor;
    }

    public static class allItemsViewHolder extends RecyclerView.ViewHolder {
        public TextView mRecipeIdView;
        public TextView mTextView1;
        public ImageView mDeleteImage;

        public allItemsViewHolder( final View itemView, final allItemsAdapter.OnItemClickListener listener ) {
            super( itemView );
     //       mRecipeIdView = itemView.findViewById( R.id.recipeId );
     //       mTextView1 = itemView.findViewById( R.id.recipe );
     //       mDeleteImage = itemView.findViewById( R.id.image_delete );
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

        final String recipeId = mCursor.getString( mCursor.getColumnIndex("ExportedRecipeID" ) );
        holder.mRecipeIdView.setText( recipeId );

        final String recipeName = mCursor.getString( mCursor.getColumnIndex("RecipieName" ) );
        holder.mTextView1.setText( recipeName );

    }

    @Override
    public int getItemCount() {
        return mCursor.getCount();
    }
}
