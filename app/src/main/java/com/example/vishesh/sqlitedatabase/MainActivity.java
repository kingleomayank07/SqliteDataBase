package com.example.vishesh.sqlitedatabase;

import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    DatabaseHelper myDb;
    EditText editName,editSurname,editMarks ,editTextId;
    Button btnAddData;
    Button btnviewAll;
    Button btnDelete;
    Button btnviewUpdate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myDb = new DatabaseHelper(this);
        editName = findViewById(R.id.editText_name);
        editSurname = findViewById(R.id.editText_surname);
        editMarks = findViewById(R.id.editText_Marks);
        editTextId = findViewById(R.id.editText_id);
        btnAddData = findViewById(R.id.button_add);
        btnviewAll = findViewById(R.id.button_viewAll);
        btnviewUpdate= findViewById(R.id.button_update);
        btnDelete= findViewById(R.id.button_delete);
        AddData();viewAll();UpdateData();DeleteData();
    }

    private void DeleteData() {

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Integer deletedRows = myDb.deleteData(editTextId.getText().toString());
                if (deletedRows>0)
                    Toast.makeText(MainActivity.this,"Data deleted",Toast.LENGTH_SHORT).show();
                else Toast.makeText(MainActivity.this,"Data not deleted",Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void UpdateData() {
        btnviewUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                  boolean isupdate = myDb.updateData(editTextId.getText().toString(),editName.getText().toString()
                          ,editMarks.getText().toString(),editSurname.getText().toString());
                  if (isupdate == true)Toast.makeText(MainActivity.this,"Data Updata",Toast.LENGTH_SHORT).show();
                  else Toast.makeText(MainActivity.this,"Data not update",Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void viewAll() {
        btnviewAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor res = myDb.getAllData();
                if(res.getCount() == 0) {
                    // show message
                    showMessage("Error","Nothing found");
                    return;
                }

                StringBuffer buffer = new StringBuffer();
                while (res.moveToNext()) {
                    buffer.append("Id :"+ res.getString(0)+"\n");
                    buffer.append("Name :"+ res.getString(1)+"\n");
                    buffer.append("Surname :"+ res.getString(2)+"\n");
                    buffer.append("Marks :"+ res.getString(3)+"\n\n");
                }

                // Show all data
                showMessage("Data",buffer.toString());
            }
            });
        }

    private void showMessage(String title, String Message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Message);
        builder.show();
    }

    private void AddData() {
        btnAddData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isInserted = myDb.insertData(editTextId.getText().toString(),
                        editName.getText().toString(),editSurname.getText().toString(),editMarks.toString());
                if (isInserted == true)Toast.makeText(MainActivity.this,"Data inserted",Toast.LENGTH_SHORT).show();
                else Toast.makeText(MainActivity.this,"Data not inserted",Toast.LENGTH_SHORT).show();
            }
        });
    }
}
