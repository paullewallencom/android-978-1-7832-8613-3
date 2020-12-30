package com.opengltutorials.breakout;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

import com.android.vending.billing.util.IabHelper;
import com.android.vending.billing.util.IabHelper.OnIabPurchaseFinishedListener;
import com.android.vending.billing.util.IabHelper.OnIabSetupFinishedListener;
import com.android.vending.billing.util.IabResult;
import com.android.vending.billing.util.Inventory;
import com.android.vending.billing.util.Purchase;

public class MainMenu extends Activity implements OnIabSetupFinishedListener, OnIabPurchaseFinishedListener {

	// In-app billing helper
	private IabHelper inAppHelper;
	// Base key for the app - HIDE THIS PROPERLY 
	private final static String baseKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAjLsXX7lyo5ON4+GeFLJKcvd06tJvCGeQgWFIJRcaTjol6nK6LwKZbUzJedKm7nJVHihPuyaAOFz+zNk7JwpCeC7D5uThWJmaJiAC923cKGxFTMuVnkFdLJeFr4HPdb59HZA3JNvjhPl7XJKP93oW9B3nKFgMDwcUhr0/9vkBMbbiCKh0xTXnyc25wPw2qoneLGR62eCFbhFUhvAxRu0mlhMZegHEM5k7W44QZYo7b3U4Vx3L8oNMyZ8elh1RoI+DVxt62e3MqYLUoNdw9+0sNBTWmnHdzYDAz8SWePu0NDNV56Tt4skIiYs24k9/6YvfJjgVClAlOxP+b00+8gD+8QIDAQAB";
	// Test SKU for purchasing
	private final static String livesSKU = "android.test.purchased";
	// Extra lives for purchasing
	private int lifeOffset = 0;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_menu);
		
		// Initialize the in app billing helper
		inAppHelper = new IabHelper(this, baseKey);
		inAppHelper.startSetup(this);
		
		// Set the game start button
		final Button button = (Button) findViewById(R.id.button1);
		final Intent GameStart = new Intent(this, OpenGLActivity.class);
		button.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				GameStart.putExtra("com.OpenGLProject.livesoffset", lifeOffset);
		      	startActivity(GameStart);
			}
		});
		
		
	}
	
	// Function used for the Purchase Button
	public void purchaseItem(View v) {
		inAppHelper.launchPurchaseFlow(this, livesSKU, 123, this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main_menu, menu);
		return true;
	}

	@Override
	public void onIabPurchaseFinished(IabResult result, Purchase info) {
		// Check the purchase result
		if (result.isFailure()) {
			// If failed, log the result and turn off the button
			Log.i("Purchase", "Purchase failed!");
			Button button = (Button) findViewById(R.id.purchasebutton);
			button.setVisibility(View.INVISIBLE);
		} else {
			if(info.getSku().equals(livesSKU)) {
				// If success, log the result and add the lives
				Log.i("Purchase", "Purchase Success!");
				inAppHelper.consumeAsync(info, null);
				lifeOffset += 3;
				
			}
		}
				
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// Handles the results of purchasing
		super.onActivityResult(requestCode, resultCode, data);
		
		if(inAppHelper.handleActivityResult(requestCode, resultCode, data)) {
			super.onActivityResult(requestCode, resultCode, data);
		}
	}
	
	IabHelper.QueryInventoryFinishedListener mGotInventoryListener = new IabHelper.QueryInventoryFinishedListener() {		
		@Override
		public void onQueryInventoryFinished(IabResult result, Inventory inv) {
			// Checks for existing purchases
			if(inv.hasPurchase(livesSKU)) {
				// Consumes existing purchase
				inAppHelper.consumeAsync(inv.getPurchase(livesSKU), null);
				lifeOffset += 3;
				Log.i("Purchase", "Old Purchase set");
			}
			
		}
	};

	@Override
	public void onIabSetupFinished(IabResult result) {
		if(result.isSuccess()) {
			// If in-app billing setup is successful, turn on button
			Button button = (Button) findViewById(R.id.purchasebutton);
			inAppHelper.queryInventoryAsync(mGotInventoryListener);
			button.setVisibility(View.VISIBLE);
		}
	}


}
