/**
 * IMPORTANT: Make sure you are using the correct package name.
 * This example uses the package name:
 * package com.example.android.justjava
 * If you get an error when copying this code into Android studio, update it to match teh package name found
 * in the project's AndroidManifest.xml file.
 **/

package com.example.android.justjava;



import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {
    int quantity=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        // Reads the user entered name.
        EditText text = (EditText) findViewById(R.id.name_field);
        String name = text.getText().toString();

        // Figure out if the user wants whipped cream topping or not.
        CheckBox whippedCreamCheckBox = (CheckBox) findViewById(R.id.whipped_cream_checkbox);
        boolean hasWhippedCream = whippedCreamCheckBox.isChecked();

        // Figure out if the user wants chocolate topping or not.
        CheckBox chocolateCheckBox = (CheckBox) findViewById(R.id.chocolate_checkbox);
        boolean hasChocolate = chocolateCheckBox.isChecked();

        int price =calculatePrice(hasWhippedCream, hasChocolate);
        String priceMessage = createOrderSummary(name, price, hasWhippedCream, hasChocolate);

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_SUBJECT, "Jusr Java coffee order for: " + name);
        intent.putExtra(Intent.EXTRA_TEXT, priceMessage);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
            }
         }

    /**
     * Calculates the price of the order.
     *
     * @param hasWhippedCream is whether or not the user wants whipped cream topping.
     * @param hasChocolate is whether or not the user wants whipped cream topping.
     * @return total price.
     */
    private int calculatePrice(boolean hasWhippedCream, boolean hasChocolate) {
        // Price of coffee withour any toppings.
        int pricePerCup = 50;
        // Add Rs.5 if the user wants whipped cream topping.
        if(hasWhippedCream)
           pricePerCup += 5;
        // Add Rs.10 if the user wants chocolate topping.
        if(hasChocolate)
            pricePerCup += 10;
        // Calculate the total price.
        return quantity * pricePerCup;
    }

    /**
     * Create summary of the order.
     *
     * @param price of the order.
     * @param hasWhippedCream  topping.
     * @param hasChocolate topping.
     * @return text summary.
     */
    private String createOrderSummary(String name, int price, boolean hasWhippedCream, boolean hasChocolate) {
        String priceMessage= "Name : " + name;
        priceMessage += "\nAdd whipped cream? " + hasWhippedCream;
        priceMessage += "\nAdd chocolate? " + hasChocolate;
        priceMessage += "\nQuantity : " + quantity;
        priceMessage += "\nTotal : Rs." + price;
        priceMessage += "\nThank You!";
        return priceMessage;
    }

    /**
     * This method is called when the plus button is clicked.
     */
    public void increment(View view) {
        if (quantity == 100) {
            // Show an error message on a toast.
            Toast.makeText(this, "You cannot order more than 100 cups of coffee " +
                    "at a single time.", Toast.LENGTH_SHORT).show();
            // Exit this method early because there's nothing left to do.
            return;
        }
        quantity++;
        displayQuantity(quantity);
    }
    /**
     * This method is called when the minus button is clicked.
     */
    public void decrement(View view) {
        if (quantity == 1) {
            // Show an error message on a toast.
            Toast.makeText(this, "You cannot order less than a cup of " +
                    "coffee.", Toast.LENGTH_SHORT).show();
            // Exit this method early because there's nothing left to do.
            return;
        }
        quantity--;
        displayQuantity(quantity);
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void displayQuantity(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }
}