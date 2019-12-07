package com.example.devin.flipper.view;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.devin.flipper.MainActivity;
import com.example.devin.flipper.R;
import com.example.devin.flipper.database.DatabaseHelper;

public class itemAdd extends AppCompatActivity {

    private static final String TAG = "itemAdd";

    DatabaseHelper mDatabaseHelper;
    private Button btnRecipeAdd;
    private EditText editRecipieText;
    private int selectedRecipieFolderID;
    ImageButton mImageBtn;
    RadioGroup radioGroup;
    RadioButton radioButton;
    TextView textView;
    TextView mCountTv;
    MenuItem mCartIconMenuItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_add);

 //       btnRecipeAdd = (Button) findViewById( R.id.btnRecipieAdd );
 //       editRecipieText = (EditText) findViewById( R.id.editRecipieText );
 //       mDatabaseHelper = new DatabaseHelper(this );

 //       radioGroup = findViewById( R.id.radioGroup );

        btnRecipeAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent recievedIntent = getIntent();
                selectedRecipieFolderID = recievedIntent.getIntExtra("FolderID", -1 );
                Log.d( TAG, "recipie folder id value is: " + selectedRecipieFolderID );

                int radioId = radioGroup.getCheckedRadioButtonId();
                radioButton = findViewById( radioId );
                Log.d( TAG, "Your choice: " + radioButton.getText() );
                String recipieName = editRecipieText.getText().toString();
                if ( radioButton.getText().equals( "Ingredients" ) ) {
                    if ( editRecipieText.length() != 0 ) {
              //          insertItem( recipieName, selectedRecipieFolderID );
                        editRecipieText.setText( "" );
                    } else {
                        toastMessage( "Please put something in the textbox!" );
                    }
                } else {
                    if ( editRecipieText.length() != 0 ) {
              //          insertRecipe( recipieName, selectedRecipieFolderID );
                    }
                }
            }
        });
    }

    /*
    public void insertItem( String recipieName, int selectedRecipieFolderID ) {

        String lowerCaseRecipe = recipieName.toLowerCase();

        boolean insertData = mDatabaseHelper.addRecipieData( lowerCaseRecipe, null, "Y", selectedRecipieFolderID );

        if ( insertData ) {
            toastMessage( "Data successfully inserted!" );

            Cursor data = mDatabaseHelper.getRecipieItemID( lowerCaseRecipe );
            int itemID = -1;
            while ( data.moveToNext() ) {
                itemID = data.getInt(0);
            }
            toastMessage( "The recipieID is: " + itemID );

            Intent intent = new Intent(RecipieInsert.this, IngredientLayoutScreen.class );
            intent.putExtra("RecipieName", lowerCaseRecipe );
            intent.putExtra("RecipieId", itemID );

            startActivity( intent );
        } else {
            toastMessage( "Something went wrong!" );
        }
    }
*/
    /*
    public void insertRecipe( String recipieName, int selectedRecipieFolderID ) {

        String lowerCaseRecipe = recipieName.toLowerCase();

        boolean insertData = mDatabaseHelper.addRecipieData( lowerCaseRecipe, null, "N", selectedRecipieFolderID );

        if ( insertData ) {
            toastMessage( "Data successfully inserted!" );

            Cursor data = mDatabaseHelper.getRecipieItemID( lowerCaseRecipe );
            int itemID = -1;
            while ( data.moveToNext() ) {
                itemID = data.getInt(0);
            }
            toastMessage( "The recipieID is: " + itemID );

            Intent intent = new Intent(RecipieInsert.this, MainActivity.class );
            intent.putExtra("RecipieName", lowerCaseRecipe );
            intent.putExtra("RecipieId", itemID );
            startActivity( intent );

        } else {

            toastMessage( "Something went wrong!" );
        }
    }
*/
    public void checkButton( View v ) {
        int radioId = radioGroup.getCheckedRadioButtonId();
        radioButton = findViewById( radioId );
        toastMessage( "Selected radio Button: " + radioButton.getText() );
    }

    private void toastMessage( String message ) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT ).show();
    }

}
