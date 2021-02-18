package com.cst2335mada0043;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;

public class ChatRoomActivity extends AppCompatActivity {
    ArrayList<Message> listItems;
    MyListAdapter adapter = new MyListAdapter();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_room);
        ListView myList = (ListView)findViewById(R.id.ListView1);
        Button sndButton = findViewById(R.id.SndBtn);
        Button rcvButton = findViewById(R.id.RcvBtn);
        EditText editText =(EditText)findViewById(R.id.Txt1);
        listItems =  new ArrayList<Message>();

        myList.setAdapter(adapter);
        myList.setOnItemLongClickListener((p, b, pos, id) -> {

            Message message=listItems.get(pos);
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            alertDialogBuilder.setTitle(R.string.Alert)


                    .setMessage(getString(R.string.selection)+pos+getString(R.string.database)+id)

                    .setPositiveButton("Yes", (click, arg) -> {
                        listItems.remove(pos);
                        adapter.notifyDataSetChanged();
                    })
                    .setNegativeButton("No", (click, arg) -> { }).create().show();
            return true;
        });
        sndButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                listItems.add( new Message(((EditText) findViewById(R.id.Txt1)).getText().toString(), 1, true));
                adapter.notifyDataSetChanged();
                editText.getText().clear();
            }
        });
        rcvButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                listItems.add(new Message(((EditText) findViewById(R.id.Txt1)).getText().toString(), 1, false));
                adapter.notifyDataSetChanged();
                editText.getText().clear();
            }
        });
    }

    private class MyListAdapter extends BaseAdapter {

        public int getCount() { return listItems.size();}

        public Object getItem(int position) { return "This is row " + position; }

        public long getItemId(int position) { return (long) position; }

        public View getView(int position, View old, ViewGroup parent)
        {
            LayoutInflater inflater = getLayoutInflater();
            Message message=listItems.get(position);
            View newView=null;
            if(message.send){
                newView = inflater.inflate(R.layout.recieve_layout, parent, false);
                TextView tView = newView.findViewById(R.id.SndTxt);
                tView.setText(message.msg );
            }else{
                newView = inflater.inflate(R.layout.send_layout, parent, false);
                TextView tView = newView.findViewById(R.id.RcvTxt);
                tView.setText(message.msg );
            }
            return newView;
        }
    }
}
