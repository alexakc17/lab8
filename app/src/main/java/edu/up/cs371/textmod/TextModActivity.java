package edu.up.cs371.textmod;

/**
 * class TextModActivity
 *
 * Allow text to be modified in simple ways with button-presses.
 */
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import java.util.ArrayList;
import java.util.Random;

public class TextModActivity extends ActionBarActivity {

    // array-list that contains our images to display
    private ArrayList<Bitmap> images;

    // instance variables containing widgets
    private ImageView imageView; // the view that shows the image
    private EditText editText;
    private Spinner spinner;

    /**
     * @see android.app.Activity#onCreate(android.os.Bundle)
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // perform superclass initialization; load the layout
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_mod);

        // set instance variables for our widgets
        imageView = (ImageView)findViewById(R.id.imageView);
        editText = (EditText) findViewById(R.id.editText);
        spinner = (Spinner) findViewById(R.id.spinner);

        // Set up the spinner so that it shows the names in the spinner array resources
        //
        // get spinner object
        String[] spinnerNames = getResources().getStringArray(R.array.spinner_names);
        // create adapter with the strings
        ArrayAdapter adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, spinnerNames);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // bind the spinner and adapter
        spinner.setAdapter(adapter);

        // load the images from the resources
        //
        // create the arraylist to hold the images
        images = new ArrayList<Bitmap>();
        // get array of image-resource IDs
        TypedArray imageIds2 = getResources().obtainTypedArray(R.array.imageIdArray);
        // loop through, adding one image per string
        for (int i = 0; i < spinnerNames.length; i++) {
            // determine the index; use 0 if out of bounds
            int id = imageIds2.getResourceId(i,0);
            if (id == 0) id = imageIds2.getResourceId(0,0);
            // load the image; add to arraylist
            Bitmap img = BitmapFactory.decodeResource(getResources(), id);
            images.add(img);
        }

        // define a listener for the spinner
        spinner.setOnItemSelectedListener(new MySpinnerListener());

    }

    public void noSpacesClicked(View v){
        CharSequence text = editText.getText();
        String noSpacesText = "";
        int start = 0;

        for(int i = 0; i < text.length(); ++i){
            if(text.charAt(i) == ' '){
                noSpacesText += text.subSequence(start, i);
                start = ++i;
            }
        }

        noSpacesText += text.subSequence(start,text.length());

        editText.setText(noSpacesText);
    }

    public void CopyNameClicked(View v) {
        String concat = editText.getText().toString() + spinner.getSelectedItem().toString();
        editText.setText(concat);
    }

    //reverses the text
    public void reverseClicked(View v){
        CharSequence text = editText.getText().toString(); //get text from gui
        int start = text.length()-1; //get index of last character in text
        int end = text.length(); //get index of last character in text to stop at
        String reversedText = ""; //hold reversed string

        //stops when pass the first index of the string
        while( start > -1){
            reversedText += text.subSequence(start,end);
            --start;
            --end;
        }

        editText.setText(reversedText);
    }

    //sets all text to uppercase
    public void upperClicked(View v){
        editText.setText(editText.getText().toString().toUpperCase());
    }

    /**
     * Removes the spaces from the editText
     * @param v
     */
    public void insertRandomChar(View v){
        String content = editText.getText().toString();
        Random rand = new Random();

        //Get a random character
        char randChar = (char) rand.nextInt(256);

        //Insert character at that location
        if (content.equals("")){
            content += randChar;
        }
        else{
            //Generate random location
            int index = rand.nextInt(content.length());
            content = content.substring(0, index) + randChar + content.substring(index);
        }


        editText.setText(content);
    }

    /**
     * @see android.app.Activity#onCreateOptionsMenu(android.view.Menu)
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_text_mod, menu);
        return true;
    }

    /**
     * @see android.app.Activity#onOptionsItemSelected(android.view.MenuItem)
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * Called by the Clear button to clear the text of the editText
     * @param v - the Clear button
     */
    public void clearEditText(View v){
        editText.setText("");
    }

    /**
     * Called by the Lower button to convert the text in the edit text to lower case
     * @param v - the Edit button
     */
    public void setEditTextLowerCase(View v){
        String editTextContent = editText.getText().toString();
        editText.setText(editTextContent.toLowerCase());
    }


    /**
     * class that handles our spinner's selection events
     */
    private class MySpinnerListener implements OnItemSelectedListener {

        /**
         * @see android.widget.AdapterView.OnItemSelectedListener#onItemSelected(
         *                  android.widget.AdapterView, android.view.View, int, long)
         */
        @Override
        public void onItemSelected(AdapterView<?> parentView, View selectedItemView,
                                   int position, long id) {
            // set the image to the one corresponding to the index selected by the spinner
            imageView.setImageBitmap(images.get(position));
        }

        /**
         * @see android.widget.AdapterView.OnItemSelectedListener#onNothingSelected(
         *                  android.widget.AdapterView)
         */
        @Override
        public void onNothingSelected(AdapterView<?> parentView) {
            // your code here
        }
    }
}
