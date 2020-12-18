package pw.codehub.taskino;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.icu.lang.UCharacter;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.DateFormat;
import java.util.Calendar;

import pw.codehub.taskino.db.Projects;
import pw.codehub.taskino.db.ProjectsDoa;
import pw.codehub.taskino.ItemListActivity;




public class AddProject extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    final Context context = this;
    ProjectsDoa  datasource;

    ImageView mImageView;
    TextView mResult;
    View mColor;

    Bitmap bitmap ;

    int r ;
    int g ;
    int b ;
    String hex;
    int pixel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_project);

        datasource = new ProjectsDoa(this);
        datasource.open();

        final EditText name= findViewById(R.id.name);
        final EditText description= findViewById(R.id.description);
        final EditText hr= findViewById(R.id.hourly_rate);
        final TextView date= findViewById(R.id.date);



        Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment datePicker = new DatePickerFragment();
                datePicker.show(getSupportFragmentManager(), "date picker");
            }
        });


        Button add = (Button) findViewById(R.id.add);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // @SuppressWarnings("unchecked")

                Projects project = null;

                EditText name= findViewById(R.id.name);
                EditText description= findViewById(R.id.description);
                EditText hr= findViewById(R.id.hourly_rate);
                TextView date= findViewById(R.id.date);


                project=datasource.createproject(name.getText().toString(), description.getText().toString(),pixel, Integer.parseInt(hr.getText().toString()),date.getText().toString() );

                //datasource.createproject("test", "test",pixel, 10,"test");
                        //adapter.add(comment);


                Intent intent = new Intent(getBaseContext(), ItemListActivity.class);

                startActivity(intent);




            }
        });


        final FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog dialog = new Dialog(context);
                dialog.setContentView(R.layout.color_picker);
                dialog.setTitle("Title...");



                final ImageView mImageView = (ImageView) dialog.findViewById(R.id.image);
                final TextView mResult = (TextView) dialog.findViewById(R.id.result);
                final View mColor = (View) dialog.findViewById(R.id.colorView);

                mImageView.setDrawingCacheEnabled(true);
                mImageView.buildDrawingCache(true);

                mImageView.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {

                        if(event.getAction()==MotionEvent.ACTION_MOVE || event.getAction()==MotionEvent.ACTION_DOWN){
                            bitmap = mImageView.getDrawingCache();
                            pixel = bitmap.getPixel((int) event.getX(),(int) event.getY());

                            r = Color.red(pixel);
                            g = Color.green(pixel);
                            b = Color.blue(pixel);

                            hex = "#"+Integer.toHexString(pixel);
                            mColor.setBackgroundColor(Color.rgb(r, g, b));
                            mResult.setText("hex: "+hex);





                        }
                        return true;
                    }
                });

                dialog.show();
                Button button = (Button) dialog.findViewById(R.id.button);


                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        fab.setBackgroundTintList(ColorStateList.valueOf(pixel));
                        mResult.setText("hex: "+pixel);

                    }
                });




            }
        });
/*
        mImageView = findViewById(R.id.image);
        mResult = findViewById(R.id.result);
        mColor = findViewById(R.id.colorView);

        mImageView.setDrawingCacheEnabled(true);
        mImageView.buildDrawingCache(true);

        mImageView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if(event.getAction()==MotionEvent.ACTION_MOVE || event.getAction()==MotionEvent.ACTION_DOWN){
                    bitmap = mImageView.getDrawingCache();
                    int pixel = bitmap.getPixel((int) event.getX(),(int) event.getY());

                    int r = Color.red(pixel);
                    int g = Color.green(pixel);
                    int b = Color.blue(pixel);

                    String hex = "#"+Integer.toHexString(pixel);
                    mColor.setBackgroundColor(Color.rgb(r,g,b));
                    mResult.setText("hex: "+hex);



                }
                return true;
            }
        });


        /*
        datasource = new ProjectsDoa(this);
        datasource.open();


        List<String> values = new ArrayList<String>();

        List<Projects> pr = datasource.getAllProjects();
        for(int i=0;i<pr.size();i++){

            values.add(pr.get(i).getName());
        }


        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1,  values);
        setListAdapter(adapter);

    }

    public void onClick(View view) {
        @SuppressWarnings("unchecked")
        ArrayAdapter<String> adapter = (ArrayAdapter<String>) getListAdapter();
        Projects project = null;
        switch (view.getId()) {
            case R.id.add:

                project = datasource.createproject("project","Description","color",10, "today");
                adapter.add(project.getName());



                break;
            case R.id.delete:
                if (getListAdapter().getCount() > 0) {
                    project = (Projects) getListAdapter().getItem(0);
                    datasource.deleteproject(project);
                   // adapter.remove(project);
                }
                break;
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onResume() {
        datasource.open();
        super.onResume();
    }

    @Override
    protected void onPause() {
        datasource.close();
        super.onPause();
    }

         */

    }


    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        String currentDateString = DateFormat.getDateInstance(DateFormat.FULL).format(c.getTime());

        TextView textView = (TextView) findViewById(R.id.date);
        textView.setText(currentDateString);
    }

/*
    public void onClick(View view) {
        @SuppressWarnings("unchecked")
        Projects project = null;
        EditText name= findViewById(R.id.name);
        EditText description= findViewById(R.id.description);
        EditText hr= findViewById(R.id.hourly_rate);
        TextView date= findViewById(R.id.date);

        switch (view.getId()) {
            case R.id.add:
                //project = datasource.createproject(name.getText().toString(), description.getText().toString(),pixel, hr.getText().toString(),date.getText().toString() );
                //adapter.add(comment);

                project = datasource.createproject("test", "test",pixel, "test","test");
                //adapter.add(comment);
                break;

        }
        //adapter.notifyDataSetChanged();
    }

*/
}
