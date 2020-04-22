package com.example.chattingapp;


import android.app.Activity;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.parse.FindCallback;
import com.parse.LogInCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    EditText editText;
    RecyclerView recyclerView;
    messageAdapter messageAdapter;
    List<ResponseMessage> responseMessages;
    InputMethodManager keyboard;
    int sie;
    boolean mFirstLoad;
      // android.os.Handler



    static final int POLL_INTERVAL = 1000; // milliseconds
    Handler myHandler = new Handler();  // android.os.Handler
    Runnable mRefreshMessagesRunnable = new Runnable() {
        @Override
        public void run() {
            refreshmessage();
            myHandler.postDelayed(this, POLL_INTERVAL);
        }
    };

    public void login()
    {
        ParseUser.logInInBackground("Rahul", "5678", new LogInCallback() {
            @Override
            public void done(ParseUser user, ParseException e) {
                if(user != null) {
                    Log.i("Success","We logged in");

                } else {
                    e.printStackTrace();
                }

            }
        });


    }


    public void sendChat(){
        editText = findViewById(R.id.editText);


    ParseObject message = new ParseObject("hoMessage");
    message.put("sender","Rahul");
    message.put("recipient","Snehdeep");
    message.put("message",editText.getText().toString());
    message.saveInBackground(new SaveCallback() {
        @Override
        public void done(ParseException e) {
            if(e == null)
            {
                ResponseMessage message1 = new ResponseMessage(editText.getText().toString(),true);
                responseMessages.add(message1);
                editText.setText("");
                messageAdapter.notifyDataSetChanged();

            }

        }
    });
}
    
    public void refreshmessage()
    {
        ParseQuery<ParseObject> query1 = new ParseQuery<ParseObject>("hoMessage");

        query1.whereEqualTo("sender", "Snehdeep");
        query1.whereEqualTo("recipient", "Rahul");

        ParseQuery<ParseObject> query2 = new ParseQuery<ParseObject>("hoMessage");

        query2.whereEqualTo("recipient", "Snehdeep");
        query2.whereEqualTo("sender", "Rahul");

        final List<ParseQuery<ParseObject>> queries = new ArrayList<ParseQuery<ParseObject>>();

        queries.add(query1);
        queries.add(query2);

        ParseQuery<ParseObject> query = ParseQuery.or(queries);

        query.orderByAscending("createdAt");
        sie = responseMessages.size();

        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
               // int sie = responseMessages.size();


                if (e == null) {


                    if (objects.size() > 0) {

                        responseMessages.clear();

                        for (ParseObject message : objects) {

                            String messageContent = message.getString("message");
                            ResponseMessage messaged = new ResponseMessage("",true);


                            if (!message.getString("sender").equals(ParseUser.getCurrentUser().getUsername())) {

                                messaged = new ResponseMessage(messageContent,false);

                            }
                            else
                            {
                                messaged = new ResponseMessage(messageContent,true);

                            }
                            responseMessages.add(messaged);







                        }

                        messageAdapter.notifyDataSetChanged();
                        if(responseMessages.size() > sie) {
                            recyclerView.smoothScrollToPosition(messageAdapter.getItemCount() - 1);
                            sie = responseMessages.size();
                        }



                    }

                }

            }
        });

    }




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Parse.initialize(new Parse.Configuration.Builder(getApplicationContext())
                .applicationId("myappID")
                .clientKey("")//your key
                .server("http://3.16.29.59/parse/") //change accordingly
                .build()
        );
        login();
        setContentView(R.layout.activity_main);
        editText = findViewById(R.id.editText);
        keyboard = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE) ;
        recyclerView = findViewById(R.id.conversation);
        responseMessages = new ArrayList<>();
        messageAdapter = new messageAdapter(responseMessages);
        recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        recyclerView.setAdapter(messageAdapter);
        //mFirstLoad = true;
        //myHandler.postDelayed(mRefreshMessagesRunnable, POLL_INTERVAL);


        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEND){
                    //ResponseMessage message = new ResponseMessage(editText.getText().toString(),true);
                    //responseMessages.add(message);
                    keyboard.hideSoftInputFromWindow(v.getWindowToken(),0);
                    sendChat();


                   // messageAdapter.notifyDataSetChanged();

                }
                return true;
            }
        });
        myHandler.postDelayed(mRefreshMessagesRunnable, POLL_INTERVAL);










    }


}
