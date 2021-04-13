package com.token.printertestapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.token.printerlib.IPrinterService;
import com.token.printerlib.PrinterBroadcastReceiver;
import com.token.printerlib.PrinterDefinitions;
import com.token.printerlib.PrinterService;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "Printer Test App";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeSpinners();
        subscribeToPrinterStatusBroadcast();

    }

    private void showPage(int id)
    {
        // TODO: 08.12.2020
    }

    private void initializeSpinners() {
        Spinner mySpinner = findViewById(R.id.fonts_spinner);

        // Populate the spinner using a customized ArrayAdapter that hides the first (dummy) entry
        ArrayAdapter<PrinterDefinitions.Font_E> dataAdapter = new ArrayAdapter<PrinterDefinitions.Font_E>(this, android.R.layout.simple_spinner_item, PrinterDefinitions.Font_E.values()) {
            @Override
            public View getDropDownView(int position, View convertView, ViewGroup parent)
            {
                View v = null;

                // Thoma fonts have licencing issues so hide them for now
                if (position == PrinterDefinitions.Font_E.Tahoma.ordinal() || position == PrinterDefinitions.Font_E.Tahoma_Bold.ordinal()) {
                    TextView tv = new TextView(getContext());
                    tv.setHeight(0);
                    tv.setVisibility(View.GONE);
                    v = tv;
                }
                else {
                    // Pass convertView as null to prevent reuse of special case views
                    v = super.getDropDownView(position, null, parent);
                }

                // Hide scroll bar because it appears sometimes unnecessarily, this does not prevent scrolling
                parent.setVerticalScrollBarEnabled(false);
                return v;
            }
        };

        //mySpinner.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, PrinterDefinitions.Font_E.values())); // fill values
        mySpinner.setAdapter(dataAdapter);
        mySpinner.setSelection((PrinterDefinitions.defaultFont.ordinal())); // set default value

        mySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                                @Override
                                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                                    setFont(PrinterDefinitions.Font_E.values()[position]);
                                                }

                                                @Override
                                                public void onNothingSelected(AdapterView<?> parent) {
                                                    setFont(PrinterDefinitions.Font_E.values()[PrinterDefinitions.defaultFont.ordinal()]);
                                                }
                                            }
        );

        // Do the same for fontsize spinner

        mySpinner = findViewById(R.id.fontSizes_spinner);
        mySpinner.setAdapter(new ArrayAdapter<Integer>(this, android.R.layout.simple_spinner_item, PrinterDefinitions.fontSizes));
        mySpinner.setSelection(3); // 3 is the inde of default font size 12

        mySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                                @Override
                                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                                    if (position < PrinterDefinitions.fontSizes.length)
                                                        setFontSize(PrinterDefinitions.fontSizes[position]);
                                                }

                                                @Override
                                                public void onNothingSelected(AdapterView<?> parent) {
                                                    setFontSize(PrinterDefinitions.defaultFontSize);
                                                }
                                            }
        );
    }

    private void subscribeToPrinterStatusBroadcast()
    {
        // TODO: 08.12.2020 move it to library
        BroadcastReceiver br = new PrinterBroadcastReceiver();
        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        filter.addAction("android.intent.action.PRINTER_STATUS_CHANGED");
        this.registerReceiver(br, filter);
    }

    // used to restore font preferences after using a function that changes them
    private void restoreFontSettings() {
        Spinner mySpinner = (Spinner) findViewById(R.id.fonts_spinner);
        setFont(PrinterDefinitions.Font_E.values()[mySpinner.getSelectedItemPosition()]);
        mySpinner = (Spinner) findViewById(R.id.fontSizes_spinner);
        setFontSize(PrinterDefinitions.fontSizes[mySpinner.getSelectedItemPosition()]);
    }


    private void setFont(PrinterDefinitions.Font_E font) {

        IPrinterService iPrinterService = PrinterService.getService();
        if (iPrinterService == null) {
            Log.d(TAG, "PrinterService is null");
        }
        else
        {
            try {
                iPrinterService.setFontFace(font.ordinal());
            }
            catch (RemoteException e)
            {
                e.printStackTrace();
                PrinterService.resetService();
            }
        }
    }

    private void setFontSize(int fontSize)
    {
        IPrinterService iPrinterService = PrinterService.getService();

        if (iPrinterService == null) {
            Log.d(TAG, "PrinterService is null");
        }
        else
        {
            try {
                iPrinterService.setFontSize(fontSize);
            }
            catch (RemoteException e)
            {
                e.printStackTrace();
                PrinterService.resetService();
            }
        }
    }

    /*
        BUTTON CLICK EVENTS
     */

    public void onCustomPrintBtn(View v) {

        IPrinterService iPrinterService = PrinterService.getService();

        if (iPrinterService == null) {
            Log.d(TAG, "PrinterService is null");
        }
        else
        {
            try {
                EditText customText = (EditText) findViewById(R.id.customText);
                String str = customText.getText().toString();
                iPrinterService.printText(str);
                iPrinterService.addSpace(Examples.bottomMargin);
            }
            catch (RemoteException e)
            {
                e.printStackTrace();
                PrinterService.resetService();
            }
        }
    }

    public void onTestFontsBtn(View v) {
        Examples.TestFonts();
        restoreFontSettings();
    }

    public void onLoremIpsumBtn(View v) {
        Examples.PrintLoremIpsum(true);
    }

    public void onPrintAllBtn(View v) {
        Examples.PrintAllCharacters(true);
    }

    public void sampleReceiptBtn(View v) {
        Examples.PrintSampleReceipt();
        restoreFontSettings();
    }

    public void sampleReceiptBtn2(View v) {
        Examples.PrintSampleReceipt2();
        restoreFontSettings();
    }

    public void changingSizesTestBtn(View v) {
        Examples.changingSizesTest();
        restoreFontSettings();
    }

    public void checkstatusBtn(View v) {
        PrinterDefinitions.PrinterErrorCode errCode= Examples.checkStatus();
        Examples.ToastStatus(this, errCode);
    }

    public void onBitmapTestBtn(View v)
    {
        byte[] bitmapFile = getBitmapReceiptArray(this, R.raw.fis);

        if(bitmapFile != null)
            Examples.PrintBitmapReceiptWithStyledStringMethod(this, bitmapFile);
    }

    public static byte[] getBitmapReceiptArray(Context context, int resourceId) {

        byte[] bitmap;
        try
        {
            InputStream inStream = context.getResources().openRawResource(resourceId);
             bitmap = new byte[inStream.available()];

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            byte[] buff = new byte[10240];
            int i = Integer.MAX_VALUE;

            while ((i = inStream.read(buff, 0, buff.length)) > 0) {
                baos.write(buff, 0, i);
            }

            bitmap = baos.toByteArray(); // be sure to close InputStream in calling function

            inStream.close();
        }
        catch (IOException e)
        {
            bitmap = null;
            e.printStackTrace();
        }

        return bitmap;
    }

    public void printQrCodeBtn(View view) {
        EditText inputForQrCode = findViewById(R.id.qrEditText);

        String text = inputForQrCode.getText().length() == 0 ? inputForQrCode.getHint().toString() : inputForQrCode.getText().toString();

        Examples.PrintQrWithStyledString(text);
    }
}
