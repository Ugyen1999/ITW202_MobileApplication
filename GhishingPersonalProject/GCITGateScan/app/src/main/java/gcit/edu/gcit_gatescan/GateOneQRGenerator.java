package gcit.edu.gcit_gatescan;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Bitmap;
import android.hardware.input.InputManager;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import java.text.DateFormat;
import java.util.Calendar;

public class GateOneQRGenerator extends AppCompatActivity {
    EditText date;
    Button btn;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gate_one_qrgenerator);
        date=findViewById(R.id.editTextTextPersonName);
        btn=findViewById(R.id.button);
        imageView=findViewById(R.id.imageview);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar=Calendar.getInstance();
                String currentDate= DateFormat.getDateInstance().format(calendar.getTime());

                MultiFormatWriter multiFormatWriter=new MultiFormatWriter();
                try { 
                    BitMatrix matrix=multiFormatWriter.encode(currentDate, BarcodeFormat.QR_CODE,350,350);
                    BarcodeEncoder barcodeEncoder=new BarcodeEncoder();
                    Bitmap bitmap=barcodeEncoder.createBitmap(matrix);
                    imageView.setImageBitmap(bitmap);
                    //Add this on input field
//                    InputMethodManager inputMethodManager=(InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
//                    inputMethodManager.hideSoftInputFromWindow(date.getApplicationWindowToken(),0);
                } catch (WriterException e) {
                    e.printStackTrace();
                }


            }
        });
    }
}