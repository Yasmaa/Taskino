package pw.codehub.taskino;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import pw.codehub.taskino.db.Projects;
import pw.codehub.taskino.db.ProjectsDoa;


/**
 * An activity representing a list of Items. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link ItemDetailActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
public class ItemListActivity extends AppCompatActivity {

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private boolean mTwoPane;
    ProjectsDoa datasource;
    static List<Projects> characters= new ArrayList<>();
    static RecyclerView rv;
    private MyAdapter Adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_list);

        datasource = new ProjectsDoa(this);
        datasource.open();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ItemListActivity.this, AddProject.class);

                startActivity(intent);
            }
        });



        if (findViewById(R.id.item_detail_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-w900dp).
            // If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = true;
        }



        //List<Pair<String, String>> projects =  Arrays.asList();

        characters = datasource.getAllProjects();

        /*final List<Pair<String, String>> characters = Arrays.asList(

                Pair.create("Lyra Belacqua", "Brave, curious, and crafty, she has been prophesied by the witches to help the balance of life"),

                Pair.create("Pantalaimon", "Lyra's daemon, nicknamed Pan."),

                Pair.create("Roger Parslow", "Lyra's friends"),

                Pair.create("Lord Asriel", "Lyra's uncle"),

                Pair.create("Marisa Coulter", "Intelligent and beautiful, but extremely ruthless and callous."),

                Pair.create("Iorek Byrnison", "Armoured bear, Rightful king of the panserbjørne. Reduced to a slave of the human village Trollesund."),

                Pair.create("Serafina Pekkala", "Witch who closely follows Lyra on her travels."),

                Pair.create("Lee Scoresby", "Texan aeronaut who transports Lyra in his balloon. Good friend with Iorek Byrnison."),

                Pair.create("Ma Costa", "Gyptian woman whose son, Billy Costa is abducted by the \"Gobblers\"."),

                Pair.create("John Faa", "The King of all gyptian people.")

        );
        */

        RecyclerView rv = (RecyclerView) findViewById(R.id.item_list);

        rv.setLayoutManager(new LinearLayoutManager(this));

        Adapter = new MyAdapter(characters);

        rv.setAdapter(Adapter);


    }


    public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

        List<Projects> characters =  Arrays.asList();


        public MyAdapter(List<Projects> characters) {
            this.characters=characters;
        }


        @Override

        public int getItemCount() {

            return characters.size();

        }


        @Override

        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            LayoutInflater inflater = LayoutInflater.from(parent.getContext());

            View view = inflater.inflate(R.layout.item_list_content, parent, false);

            return new MyViewHolder(view);

        }


        @Override

        public void onBindViewHolder(MyViewHolder holder, int position) {

            String[] pair = new String[]{String.valueOf(characters.get(position).getId()),characters.get(position).getName(), characters.get(position).getDescription(), String.valueOf(characters.get(position).getColor())};

            holder.display(pair);

        }




        public class MyViewHolder extends RecyclerView.ViewHolder {


            private final TextView name;

            private final TextView description;

            private final FloatingActionButton bl ;



            private String[] currentPair;


            public MyViewHolder(final View itemView) {

                super(itemView);


                name = ((TextView) itemView.findViewById(R.id.id_text));

                description = ((TextView) itemView.findViewById(R.id.content));

                bl = itemView.findViewById(R.id.fab);
                /*
                final Button delete = itemView.findViewById(R.id.delete);

                delete.setOnClickListener(new View.OnClickListener() {

                    @Override

                    public void onClick(View view) {



                        datasource.deleteproject(currentPair[0]);


                        characters= datasource.getAllProjects();

                        notifyDataSetChanged();




                    }

                });



                   */


                itemView.setOnClickListener(new View.OnClickListener() {

                    @Override

                    public void onClick(View view) {

                        /*

                        new AlertDialog.Builder(itemView.getContext())

                                .setTitle(currentPair[0])

                                .setMessage(currentPair[1])

                                .show();

                          */

                        TextView name = (TextView) findViewById(R.id.name);


                        Intent intent = new Intent(getBaseContext(), ItemDetailActivity.class);
                        intent.putExtra("Id",currentPair[0]);
                        intent.putExtra( "name",currentPair[1] );


                        startActivity(intent);
                    }

                });

            }


            public void display(String[] pair) {

                currentPair = pair;




                name.setText(pair[1]);

                description.setText(pair[2]);

                //bl.setBackgroundTintList(ColorStateList.valueOf(Integer.parseInt(pair[3])));

            }

        }


    }
}
