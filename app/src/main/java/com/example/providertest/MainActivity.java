package com.example.providertest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    private String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button addData=(Button)findViewById(R.id.add_words);
        addData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //添加数据
                Uri uri=Uri.parse("content://com.example.Vocabulary_Notebook.provider/words");
                ContentValues values=new ContentValues();
                values.put("word","ProviderTest");
                values.put("mean","my provider");
                values.put("ep","this is my provider.");
                Uri newUri=getContentResolver().insert(uri,values);
                //Log.d("add",""+newUri);
                id=newUri.getPathSegments().get(1);
                //Log.d("add",""+id);
            }
        });
        Button queryData=(Button)findViewById(R.id.query_words);
        queryData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //查询数据
                Uri uri=Uri.parse("content://com.example.Vocabulary_Notebook.provider/words");
                Cursor cursor=getContentResolver().query(uri,null,null,null,null);
                EditText ans=(EditText)findViewById(R.id.query_ans);
                ans.setText("");
                if(cursor!=null){
                    while (cursor.moveToNext()){
                        String word=cursor.getString(cursor.getColumnIndex("word"));
                        String mean=cursor.getString(cursor.getColumnIndex("mean"));
                        String ep=cursor.getString(cursor.getColumnIndex("ep"));
                        Log.d("MainActivity","words word is  "+word);
                        Log.d("MainActivity","words mean is  "+mean);
                        Log.d("MainActivity","words ep is  "+ep);
                        ans.append("word:   "+word+"\nmean:   "+mean+"\nep:   "+ep+"\n\n");
                    }
                }
                cursor.close();
            }
        });
        Button deleteData=(Button)findViewById(R.id.delete_words);
        deleteData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //删除数据
                Uri uri=Uri.parse("content://com.example.Vocabulary_Notebook.provider/words/"+id);
                //Log.d("shanchu:",uri.toString());
                getContentResolver().delete(uri,null,null);
            }
        });
        Button updateData=(Button)findViewById(R.id.update_words);
        updateData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //修改数据
                Uri uri=Uri.parse("content://com.example.Vocabulary_Notebook.provider/words/"+id);
                ContentValues values=new ContentValues();
                values.put("mean","update");
                values.put("ep","update success.");
                getContentResolver().update(uri,values,null,null);
            }
        });
    }
}
