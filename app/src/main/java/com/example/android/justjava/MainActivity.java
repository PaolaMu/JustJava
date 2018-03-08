package com.example.android.justjava;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
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
    int quantity = 2 ;
    int price = quantity * 5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {

        // Figure out customer name
        EditText nameText = findViewById(R.id.plain_text_input);
        Editable hasName = nameText.getText();

        // Figure out if the user wants whipped cream topping
        CheckBox whippedCreamCheckBox = findViewById(R.id.whipped_cream_checkbox);
        boolean hasWhippedCream = whippedCreamCheckBox.isChecked();


        // Figure out if the user wants chocolate topping
        CheckBox chocolateCheckBox = findViewById(R.id.chocolate_checkbox);
        boolean hasChocolate = chocolateCheckBox.isChecked();

        // Calculate the price
        int price = calculatePrice(hasWhippedCream, hasChocolate);

        // Display the order summary on the screen
        String priceMessage = createOrderSummary(price, hasWhippedCream, hasChocolate, hasName);

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_SUBJECT, "Just Java order for: " + hasName);
        intent.putExtra(Intent.EXTRA_TEXT, priceMessage);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }
    /**

     Calculates the price of the order.

     @param addWhippedCream whether or not the customer wants addWhippedCream

     @param addChocolate whether or not the customer wants addChocolate

     @return total price
     */
    private int calculatePrice(boolean addWhippedCream, boolean addChocolate) {
// Price of 1 cup of coffee
        int basePrice = 5;

// Add $1 if customer wants whipped cream
        if (addWhippedCream) {
            basePrice = basePrice + 1;
        }

// Add $2 if customer wants chocolate
        if (addChocolate) {
            basePrice = basePrice + 2;
        }

// Calculate the total order price by multiplying by quantity
        return quantity * basePrice;
    }

    /**
        }
    }
    /**
     * Create summary of the order
     *
     * @param hasName to the order.
     * @param price of the order
     * @param addWhippedCream is whether or not the user wants whipped cream topping.
     * @param addChocolate is whether or not the user wants chocolate topping
     * @return text summary.
     */
    private String createOrderSummary(int price, boolean addWhippedCream, boolean addChocolate, Editable hasName) {
        String priceMessage = getString(R.string.order_summary_name, hasName);
        priceMessage += "\n" + getString(R.string.order_summary_whipped_cream, addWhippedCream);
        priceMessage += "\n" + getString(R.string.order_summary_chocolate, addChocolate);
        priceMessage += "\n" + getString(R.string.order_summary_quantity, quantity);
        priceMessage += "\n" + getString(R.string.order_summary_price, NumberFormat.getCurrencyInstance().format(price));
        priceMessage += "\n" + getString(R.string.thank_you);
        return priceMessage;
    }
    /**
     * This method is called when the plus button is clicked.
     */
    public void increment(View view) {
        if (quantity == 100) {
// Show an error message at a toast
            Toast.makeText(this, "You cannot have more than 100 coffees", Toast.LENGTH_SHORT).show();
            return;
        }
        quantity = quantity + 1;
        displayQuantity(quantity);
    }
    /**
     * This method is called when the minus button is clicked.
     */
    public void decrement(View view) {
        if (quantity ==1) {
            // Show an error message at a toast
            Toast.makeText(this, "You cannot have less thant 1 coffee", Toast.LENGTH_SHORT).show();
            return;
        }
        quantity = quantity - 1 ;
        displayQuantity (quantity);
    }
    /**
     * This method displays the given quantity value on the screen.
     */
    private void displayQuantity(int numberOfCoffees) {
        TextView quantityTextView = findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + numberOfCoffees);
    }
}

