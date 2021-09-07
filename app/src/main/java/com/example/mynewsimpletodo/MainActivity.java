package com.example.mynewsimpletodo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.os.FileUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.commons.io.FileSystemUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    List<String> items = new ArrayList<>(FileUtils.readLines(getDataFile(), Charset.defaultCharset());

    Button btnAdd;
    EditText etItem;
    RecyclerView rvItems;
    ItemsAdapter intemsAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnAdd = findViewById(R.id.btnAdd);
        etItem = findViewById(R.id.etItem);
        rvItems = findViewById(R.id.rvitems);


        loadItems();


        ItemsAdapter.OnLongClickListener onLongClickListener = new ItemsAdapter.OnLongClickListener(){
            @Override
            public void onItemLongClicked(int position) {
                //Delete hte item from the model
                items.remove(position);
                // Nofity the adapter
                intemsAdapter.notifyItemRemoved(position);
                Toast.makeText(getApplicationContext(), "Item was removed", Toast.LENGTH_SHORT).show();
                saveItems();
            }
        };
        ItemsAdapter itemsAdapter = new ItemsAdapter(items, onLongClickListener);
        rvItems.setAdapter(itemsAdapter);
        rvItems.setLayoutManager(new LinearLayoutManager(this));

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String todoItem = etItem.getText().toString();
                // Add item to the model
                items.add(todoItem);
                //Notidy adapter that an item is insetrted
                itemsAdapter.notifyItemInserted( items.size()-1);
                etItem.setText("");
                Toast.makeText(getApplicationContext(),"Item was added", Toast.LENGTH_SHORT).show();
                saveItems();
            }
        });

    }
    private File getDataFile(){
        return new File(getFilesDir(), "data.txt");
    }
    //This function will load itesm by reading every line of the data file
    private void loadItems(){
        try{
        items = new ArrayList<>(FileUtils.readLines(getDataFile(), Charset.defaultCharset());
    } catch (IOException e) {
            Log.e( "MainActivity" , "Error reading items", e);
        items = new ArrayList<>();

    }


    // This fucnation saves itesm by writnng them into the data file
        private void saveItems() {
            FileUtils.writeLines(getDataFile(), items);
        } catch (IOException e) {
            Log.e( "MainActivity" , "Error writing items", e);
        }
        }
}