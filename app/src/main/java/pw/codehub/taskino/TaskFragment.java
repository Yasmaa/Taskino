package pw.codehub.taskino;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import pw.codehub.taskino.db.Tasks;
import pw.codehub.taskino.db.TasksDoa;
import pw.codehub.taskino.dummy.DummyContent;
import pw.codehub.taskino.dummy.DummyContent.DummyItem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static android.content.Intent.getIntent;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class TaskFragment extends Fragment {

    String Id;
    TasksDoa datasource;
    static List<Tasks> tasks= new ArrayList<>();
    static RecyclerView rv;
    private MyTaskAdapter Adapter;

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;
    private OnListFragmentInteractionListener mListener;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public TaskFragment() {
    }

    public TaskFragment(String id) {

        this.Id=id;

    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static TaskFragment newInstance(int columnCount) {
        TaskFragment fragment = new TaskFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }


        datasource = new TasksDoa(getActivity());
        datasource.open();

        tasks = datasource.getAllTasks(Id);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_task_list, container, false);





        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            rv = (RecyclerView) view;
            if (mColumnCount <= 1) {
                rv.setLayoutManager(new LinearLayoutManager(context));
            } else {
                rv.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }

            Adapter = new MyTaskAdapter(tasks);
            rv.setAdapter(Adapter);
        }

        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        void onListFragmentInteraction(DummyItem item);
    }


    public class MyTaskAdapter extends RecyclerView.Adapter<MyTaskAdapter.MyViewHolder> {

        List<Tasks> tasks =  Arrays.asList();


        public MyTaskAdapter(List<Tasks> tasks) {
            this.tasks=tasks;
        }


        @Override

        public int getItemCount() {

            return tasks.size();

        }


        @Override

        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            LayoutInflater inflater = LayoutInflater.from(parent.getContext());

            View view = inflater.inflate(R.layout.item_list_content, parent, false);

            return new MyViewHolder(view);

        }


        @Override

        public void onBindViewHolder(MyViewHolder holder, int position) {

            String[] pair = new String[]{String.valueOf(tasks.get(position).getId()),tasks.get(position).getName(), tasks.get(position).getDescription(),tasks.get(position).getTime()};

            holder.display(pair);

        }




        public class MyViewHolder extends RecyclerView.ViewHolder {


            private final TextView name;

            private final TextView description;




            private String[] currentPair;


            public MyViewHolder(final View itemView) {

                super(itemView);


                name = ((TextView) itemView.findViewById(R.id.id_text));

                description = ((TextView) itemView.findViewById(R.id.content));

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

                        TextView name = (TextView) itemView.findViewById(R.id.id_text);


                        Intent intent = new Intent(getContext(), TaskDetail.class);
                        intent.putExtra("id",currentPair[0]);
                        intent.putExtra("time",currentPair[3]);



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
