package com.example.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


import org.web3j.crypto.Credentials;
import org.web3j.crypto.ECKeyPair;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.Web3ClientVersion;
import org.web3j.protocol.http.HttpService;

import java.math.BigInteger;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    Web3j web3;
    Credentials credentials;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnConnect  = (Button) findViewById(R.id.btnConnect);
        btnConnect .setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            //    Toast.makeText(MainActivity.this, "click", Toast.LENGTH_LONG);
            //    Log.d(TAG, "onClick: Button Clicked");
                web3 = Web3j.build(new HttpService("https://ropsten.infura.io/v3/7300efd00d2743c7afa9bae61659ca09"));
                try {
                    Web3ClientVersion clientVersion = web3.web3ClientVersion().sendAsync().get();
                    if(!clientVersion.hasError()){

                        toastAsync("Connected!");

                    }
                    else {
                        toastAsync(clientVersion.getError().getMessage());
                    }
                } catch (Exception e) {
                    toastAsync(e.getMessage());
                }
            }
        });

        Button btnGetAddress  = (Button) findViewById(R.id.btnGetAddress);
        btnGetAddress .setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "btnGetAddress onClicked");
                try {
//            Credentials credentials = WalletUtils.loadCredentials(password, new File(walletPath+"/"+fileName));
                    credentials = getCredentailFromPrivteKey("E14611FC8AC6D9B417204EDAAC18494055E798EE08EDE6369EC48BCCB30B279C");

                    toastAsync("Your address is " + credentials.getAddress());
                    Log.d(TAG, "getAddress: "+ credentials.getAddress());
                    Log.d(TAG, "getAddress: 0xA80c982364EF3f146266bec9d7E2F2b30CD94aF9");


                }
                catch (Exception e){

                    toastAsync("getAddress error "+e.getMessage());
                }
            }
        });
    }

    private Credentials getCredentailFromPrivteKey(String privateKeyInHex) {
        BigInteger privateKeyInBT = new BigInteger(privateKeyInHex, 16);
        ECKeyPair aPair = ECKeyPair.create(privateKeyInBT);
        Credentials aCredential = Credentials.create(aPair);
        return aCredential;
    }

    public void toastAsync(String message) {
        runOnUiThread(() -> {
            Toast.makeText(this, message, Toast.LENGTH_LONG).show();
            Log.d(TAG, "toastAsync: "+message);
        });
    }
}
