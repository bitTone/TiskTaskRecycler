package com.tj.tisktaskrecycler.view;

import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tj.tisktaskrecycler.R;
import com.tj.tisktaskrecycler.data.FakeDataSource;
import com.tj.tisktaskrecycler.data.ListItem;
import com.tj.tisktaskrecycler.logic.Controller;

import java.util.List;

public class ListActivity extends AppCompatActivity implements ViewInterface {

    private static final String EXTRA_DATE_AND_TIME = "EXTRA_DATE_AND_TIME";
    private static final String EXTRA_MESSAGE = "EXTRA_MESSAGE";
    private static final String EXTRA_COLOUR = "EXTRA_COLOUR";

    private List<ListItem> listofData;

    private LayoutInflater layoutInflater;
    private RecyclerView recyclerView;
    private CustomAdapter adapter;

    private Controller controller;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        //creates the recycler view
        recyclerView = (RecyclerView)findViewById(R.id.rec_list_activity);
        //inflates layout duh
        layoutInflater=getLayoutInflater();
        //dependency injection
        controller = new Controller(this,new FakeDataSource());





    }

    @Override
    public void startDetailActivity(String dateandTime, String message, int colorResource,View viewroot) {

        Intent i =new Intent(this,DetailActivity.class);
        i.putExtra(EXTRA_DATE_AND_TIME,dateandTime);
        i.putExtra(EXTRA_MESSAGE,message);
        i.putExtra(EXTRA_COLOUR,colorResource);

        startActivity(i);


    }

    /**
     * In order to make sure things execute in the proper order, we have our Controller tell the
     * View when to set up it's stuff.
     *
     * @param listOfData
     */

    @Override
    public void setUpAdapterAndView(List<ListItem> listOfData) {

        this.listofData=listOfData;
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);

        recyclerView.setLayoutManager(layoutManager);

        adapter = new CustomAdapter();
        recyclerView.setAdapter(adapter);

        DividerItemDecoration itemDecoration = new DividerItemDecoration(
                recyclerView.getContext(),
                layoutManager.getOrientation()
        );

        itemDecoration.setDrawable(
                ContextCompat.getDrawable(
                        ListActivity.this,
                        R.drawable.divider_white
                )
        );

        recyclerView.addItemDecoration(
                itemDecoration
        );

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(createHelperCallback());
        itemTouchHelper.attachToRecyclerView(recyclerView);



    }

    @Override
    public void addNewListItemToView(ListItem newItem) {
        listofData.add(newItem);

        int endOfList = listofData.size() - 1;

        adapter.notifyItemInserted(endOfList);

        recyclerView.smoothScrollToPosition(endOfList);
    }

    @Override
    public void deleteListItemAt(int position) {
       listofData.remove(position);

        adapter.notifyItemRemoved(position);
    }

    @Override
    public void showUndoSnackbar() {



    }

    @Override
    public void insertListItemAt(int temporaryListItemPosition, ListItem temporaryListItem) {

    }


    private class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.CustomViewHolder>{

        @Override
        public CustomAdapter.CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            View v=layoutInflater.inflate(R.layout.item_data,parent,false);

            return new CustomViewHolder(v);
        }

        @Override
        public void onBindViewHolder(CustomAdapter.CustomViewHolder holder, int position) {
            ListItem currentItem = listofData.get(position);

            holder.coloredCircle.setBackgroundResource(
                    currentItem.getColorResource()
            );

            holder.message.setText(
                    currentItem.getMessage()
            );
            holder.dateAndTime.setText(
                    currentItem.getDateAndTime()
            );



        }

        @Override
        public int getItemCount() {

            //helps the adapter decide how many items it will need to manage

            return listofData.size();
        }

        class CustomViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

            private View coloredCircle;
            private TextView dateAndTime;
            private TextView message;
            private ViewGroup container;


            public CustomViewHolder(View itemView) {
                super(itemView);

                this.coloredCircle=itemView.findViewById(R.id.imv_list_item_circle);
                this.dateAndTime=(TextView)itemView.findViewById(R.id.lbl_date_and_time);
                this.message=(TextView)itemView.findViewById(R.id.lbl_message);
                this.coloredCircle=(View)itemView.findViewById(R.id.imv_list_item_circle);

                this.container=(ViewGroup) itemView.findViewById(R.id.root_list_item);






            }

            @Override
            public void onClick(View v) {
                int viewId = v.getId();
                if (viewId == R.id.fab_create_new_item) {
                    //User wishes to creat a new RecyclerView Item
                    controller.createNewListItem();
                }
            }

            }

        }




    private ItemTouchHelper.Callback createHelperCallback() {
        /*First Param is for Up/Down motion, second is for Left/Right.
        Note that we can supply 0, one constant (e.g. ItemTouchHelper.LEFT), or two constants (e.g.
        ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) to specify what directions are allowed.
        */
        ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(
                0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {

            //not used, as the first parameter above is 0
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder,
                                  RecyclerView.ViewHolder target) {
                return false;
            }


            @Override
            public void onSwiped(final RecyclerView.ViewHolder viewHolder, int swipeDir) {
                int position = viewHolder.getAdapterPosition();
                controller.onListItemSwiped(
                        position,
                        listofData.get(position)
                );
            }
        };

        return simpleItemTouchCallback;
    }
}

