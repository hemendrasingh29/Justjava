package com.example.zendynamix.justjava;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {
    int coffeeQuantity = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method is called when coffee subtracted
     */
    public void onSubtractingCoffee(View view) {
        if (coffeeQuantity == 1) {
            Toast toast = Toast.makeText(this, "YOU CAN'T HAVE LESS THEN 1 COFFEE", Toast.LENGTH_SHORT);
            toast.show();
            return;
        }
        coffeeQuantity = coffeeQuantity - 1;
        displayQuantity(coffeeQuantity);

    }

    /**
     * This method is called when coffee added
     */
    public void onAddingCoffee(View view) {
        if (coffeeQuantity == 100) {
            Toast toast = Toast.makeText(this, "YOU CAN'T HAVE MORE THEN 100 COFFEE", Toast.LENGTH_SHORT);
            toast.show();
            return;
        }
        coffeeQuantity = coffeeQuantity + 1;
        displayQuantity(coffeeQuantity);
    }

    /**
     * returning total price of coffee ordered
     */
    private int calculateprice(boolean topping, boolean ctopping) {
        int price = coffeeQuantity * 5;
        // checking for topping choice
        if (topping == true) {
            price = price + 1;
        }
        if (ctopping == true) {
            price = price + 2;
        }

        return price;
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        String topping, ctopping;


        TextView textView = (TextView) findViewById(R.id.name_entered);
        String enteredname = textView.getText().toString();

        CheckBox isCchecked = (CheckBox) findViewById(R.id.chocoklat_cream_check);
        boolean cchecked = isCchecked.isChecked();
        if (cchecked == true) {
            ctopping = "Yes";
        } else {
            ctopping = "No";
        }
        CheckBox whippedCreamcheck = (CheckBox) findViewById(R.id.whipped_cream_check);
        boolean isChecked = whippedCreamcheck.isChecked();

        if (isChecked == true) {
            topping = "Yes";
        } else {
            topping = "No";
        }
        int totalPrice = calculateprice(isChecked, cchecked);


        String orderSummary = createOrderSummary(coffeeQuantity, totalPrice, topping, ctopping, enteredname);

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_SUBJECT, "Message for " + enteredname);
        intent.putExtra(Intent.EXTRA_TEXT, "Order summary " + orderSummary);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
        displayPrice(orderSummary);
//        Intent intent2 = new Intent(Intent.ACTION_VIEW);
//        intent.setData(Uri.parse("geo:47.6,-122.3"));
//        if (intent.resolveActivity(getPackageManager()) != null) {
//            startActivity(intent);
//        }

    }


    // Create order summary for order placed


    private String createOrderSummary(int coffeeQuantity, int price, String wcream, String chocolate, String ename) {

        String priceMessage = "Name: " + ename;
        priceMessage += "\nQUANTITY " + coffeeQuantity + " Cup of coffee";
        priceMessage += "\nWith cream topping ? " + wcream;
        priceMessage += "\nWith chocolate topping ? " + chocolate;
        priceMessage += "\nTOTAL $ " + price;
        priceMessage += "\nThank you !";
        return priceMessage;
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void displayQuantity(int number) {
        TextView quantityTextView = (TextView) findViewById(
                R.id.quantity_text_view);
        quantityTextView.setText("" + coffeeQuantity);
    }


    private void displayPrice(String pmessage) {
        TextView priceTextView = (TextView) findViewById(R.id.price_text_view);
        priceTextView.setText(pmessage);

    }
}