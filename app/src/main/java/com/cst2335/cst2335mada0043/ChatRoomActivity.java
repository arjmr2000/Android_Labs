package com.cst2335.cst2335mada0043;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import static com.cst2335.cst2335mada0043.Mydatabase.TABLE_NAME;

public class ChatRoomActivity extends AppCompatActivity {
    ArrayList<Message> listItems =new ArrayList<>();
    MyListAdapter adapter = new MyListAdapter();
    int positionClicked = 0;
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_room);

        //Get the fields from the screen:
        ListView myList = (ListView)findViewById(R.id.ListView1);
        Button sndButton = findViewById(R.id.SndBtn);
        Button rcvButton = findViewById(R.id.RcvBtn);
        EditText editText =(EditText)findViewById(R.id.Txt1);


        loadDataFromDatabase(); //get any previously saved Contact objects



        myList.setAdapter(adapter);
        myList.setOnItemLongClickListener((p, b, pos, id) -> {//deleting listview start

                Message message=listItems.get(pos);
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
                alertDialogBuilder.setTitle(R.string.Dialog)


                    .setMessage(getString(R.string.selection)+pos+"\n"+getString(R.string.database)+id)

                    .setPositiveButton("Yes", (click, arg) -> {
                        listItems.remove(pos);
                        adapter.notifyDataSetChanged();
                    })
                    .setNegativeButton("No", (click, arg) -> { }).create().show();
                showContact(pos);
                return true;
        });//end
        //Listen for an insert button click event:
        sndButton.setOnClickListener(new View.OnClickListener() {//snding msg

            public void onClick(View v) {

                String textValue = ((EditText) findViewById(R.id.Txt1)).getText().toString();
                //add to the database and get the new ID
                ContentValues newRowValues = new ContentValues();
                //Now provide a value for every database column defined in Mydatabase.java:
                //put string name in the tExt column:
                newRowValues.put(Mydatabase.COL_TEXT, textValue);
                //Now insert in the database:

                long newId = db.insert(TABLE_NAME, null, newRowValues);

                //now you have the newId, you can create the Contact object
                Message newMessage = new Message( newId,textValue);
                //add the new contact to the list:
                //clear the EditText fields:
                listItems.add( new Message(((EditText) findViewById(R.id.Txt1)).getText().toString(), 1, true));
                adapter.notifyDataSetChanged();
                editText.getText().clear();
                //Show the id of the inserted item:
                Toast.makeText(ChatRoomActivity.this, "Inserted item id:"+newId, Toast.LENGTH_LONG).show();
            }
        });
        rcvButton.setOnClickListener(new View.OnClickListener() {//rcving msg

            public void onClick(View v) {

                String textValue = ((EditText) findViewById(R.id.Txt1)).getText().toString();
                //add to the database and get the new ID
                ContentValues newRowValues = new ContentValues();
                //Now provide a value for every database column defined in Mydatabase.java:
                //put string name in the tExt column:
                newRowValues.put(Mydatabase.COL_TEXT, textValue);
                //Now insert in the database:

                long newId = db.insert(TABLE_NAME, null, newRowValues);

                //now you have the newId, you can create the Contact object
                Message newMessage = new Message( newId,textValue);
                //add the new contact to the list:
                //update the     listView:
                listItems.add(new Message(((EditText) findViewById(R.id.Txt1)).getText().toString(), 1, false));
                adapter.notifyDataSetChanged();
                editText.getText().clear();
                //Show the id of the inserted item:
                Toast.makeText(ChatRoomActivity.this, "Inserted item id:"+newId, Toast.LENGTH_LONG).show();


            }
        });
    }
    private void loadDataFromDatabase()
    {
        //get a database connection:
        Mydatabase dbOpener = new Mydatabase(this);
        db = dbOpener.getWritableDatabase(); //This calls onCreate() if you've never built the table before, or onUpgrade if the version here is newer


        // We want to get all of the columns. Look at MyOpener.java for the definitions:
        String [] columns = {Mydatabase.COL_ID, Mydatabase.COL_TEXT};
        //query all the results from the database:
        Cursor results = db.query(false, Mydatabase.TABLE_NAME, columns, null, null, null, null, null, null);

        //Now the results object has rows of results that match the query.
        //find the column indices:
        int textColIndex = results.getColumnIndex(Mydatabase.COL_TEXT);

        int idColIndex = results.getColumnIndex(Mydatabase.COL_ID);

        //iterate over the results, return true if there is a next item:
        while(results.moveToNext())
        {
            String textValue = results.getString(textColIndex);
            long id = results.getLong(idColIndex);

            //add the new Contact to the array list:
            listItems.add(new Message(id,textValue));
        }

        //At this point, the contactsList array has loaded every row from the cursor.
    }


    protected void showContact(int position)
    {
        Message selectedContact = listItems .get(position);

        View contact_view = getLayoutInflater().inflate(R.layout.activity_chat_room, null);
        //get the TextViews
        EditText rowText = contact_view.findViewById(R.id.ListView1);
        TextView rowId = contact_view.findViewById(R.id.row_id);

        //set the fields for the alert dialog
        rowText.setText(selectedContact.getTextValue());
        rowId.setText("id:" + selectedContact.getId());

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("You clicked on item #" + position)
                .setMessage("You can update the fields and then click update to save in the database")
                .setView(contact_view) //add the 3 edit texts showing the contact information
                .setPositiveButton("Update", (click, b) -> {
                    selectedContact.update(rowText.getText().toString());
                    updateContact(selectedContact);
                    adapter.notifyDataSetChanged(); //the email and name have changed so rebuild the list
                })
                .setNegativeButton("Delete", (click, b) -> {
                    deleteContact(selectedContact); //remove the contact from database
                    listItems.remove(position); //remove the contact from contact list
                    adapter.notifyDataSetChanged(); //there is one less item so update the list
                })
                .setNeutralButton("dismiss", (click, b) -> { })
                .create().show();
    }

    protected void updateContact(Message c)
    {
        //Create a ContentValues object to represent a database row:
        ContentValues updatedValues = new ContentValues();
        updatedValues.put(Mydatabase.COL_TEXT, c.getTextValue());


        //now call the update function:
        db.update(Mydatabase.TABLE_NAME, updatedValues, Mydatabase.COL_ID + "= ?", new String[] {Long.toString(c.getId())});
    }

    protected void deleteContact(Message c)
    {
        db.delete(Mydatabase.TABLE_NAME, Mydatabase.COL_ID + "= ?", new String[] {Long.toString(c.getId())});
    }
    private class MyListAdapter extends BaseAdapter {

        public int getCount() { return listItems.size();}

        public Object getItem(int position) { return "This is row " + position; }


        public View getView(int position, View old, ViewGroup parent)
        {
            LayoutInflater inflater = getLayoutInflater();
            Message message=listItems.get(position);
            View newView=null;
            if(message.send){//goes to snd activity
                newView = inflater.inflate(R.layout.sender_activity, parent, false);
                TextView tView = newView.findViewById(R.id.SndTxt);
                tView.setText(message.msg );

            }else{//goes to recv activity
                newView = inflater.inflate(R.layout.reciever_activity, parent, false);
                TextView tView = newView.findViewById(R.id.RcvTxt);
                tView.setText(message.msg );
            }
            return newView;
        }
        public long getItemId(int position)
        {
            return getItem(position).getId();
        }
    }
}