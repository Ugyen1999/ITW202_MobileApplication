package gcit.edu.keepme;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;



public class Notification extends AppCompatActivity {
    EditText title,description;
    Button addEvent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        title = findViewById(R.id.eTitle);

        description = findViewById(R.id.eDescription);
        addEvent = findViewById(R.id.btnaAdd);

        addEvent.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                if (!title.getText().toString().isEmpty() && !description.getText().toString().isEmpty()){
                    Intent intent = new Intent(Intent.ACTION_INSERT);
                    intent.setData(CalendarContract.Events.CONTENT_URI);
                    intent.putExtra(CalendarContract.Events.TITLE, title.getText().toString());

                    intent.putExtra(CalendarContract.Events.DESCRIPTION,description.getText().toString());
                    intent.putExtra(CalendarContract.Events.ALL_DAY, true);


                    if(intent.resolveActivity(getPackageManager()) !=null){
                        startActivity(intent);
                    }else{
                        Toast.makeText(Notification.this,"There is no app that support this action", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(Notification.this, "Please fill all the fields", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}