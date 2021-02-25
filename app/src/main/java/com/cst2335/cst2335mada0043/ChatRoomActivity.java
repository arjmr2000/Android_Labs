package com.cst2335.cst2335mada0043;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
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
import java.util.List;

public class ChatRoomActivity extends AppCompatActivity {
    ArrayList<Message> listItems;
    MyListAdapter adapter = new MyListAdapter();
    Mydatabase db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_room);
        db=new Mydatabase(this);

        ListView myList = (ListView)findViewById(R.id.ListView1);
        Button sndButton = findViewById(R.id.SndBtn);
        Button rcvButton = findViewById(R.id.RcvBtn);
        EditText editText =(EditText)findViewById(R.id.Txt1);
        listItems =  new ArrayList<Message>();
        // adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listItems);
        myList.setAdapter(adapter);
        loadMessage();
        myList.setOnItemLongClickListener((p, b, pos, id) -> {

            Message message=listItems.get(pos);
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            alertDialogBuilder.setTitle(R.string.Dialog)

                    //What is the message:
                    .setMessage(getString(R.string.selection)+pos+getString(R.string.database)+id)

                    .setPositiveButton("Yes", (click, arg) -> {
                        db.deleteMessage(listItems.get(pos));
                        loadMessage();
                    })
                    .setNegativeButton("No", (click, arg) -> { }).create().show();
            return true;
        });
        sndButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                Message message= new Message(((EditText) findViewById(R.id.Txt1)).getText().toString(), 1, true);
                db.addMessage(message);
                loadMessage();
                editText.getText().clear();
            }
        });
        rcvButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                Message message= new Message(((EditText) findViewById(R.id.Txt1)).getText().toString(), 1, false);
                db.addMessage(message);
                loadMessage();
                editText.getText().clear();
            }
        });
    }

    public void loadMessage(){
        listItems=db.getAll();
        adapter.notifyDataSetChanged();
    }

    private class MyListAdapter extends BaseAdapter {

        public int getCount() { return listItems.size();}

        public Object getItem(int position) { return "This is row " + position; }

        public long getItemId(int position) { return listItems.get(position).id; }

        public View getView(int position, View old, ViewGroup parent)
        {
            LayoutInflater inflater = getLayoutInflater();
            Message message=listItems.get(position);
            View newView=null;
            if(message.send){
                newView = inflater.inflate(R.layout.sender_activity, parent, false);
                TextView tView = newView.findViewById(R.id.SndTxt);
                tView.setText(message.msg );
            }else{
                newView = inflater.inflate(R.layout.reciever_activity, parent, false);
                TextView tView = newView.findViewById(R.id.RcvTxt);
                tView.setText(message.msg );
            }
            return newView;
        }
    }
}