package com.tj.tisktaskrecycler.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
    public void startDetailActivity(String dateandTime, String message, int colorResource) {

        Intent i =new Intent(this,DetailActivity.class);
        i.putExtra(EXTRA_DATE_AND_TIME,dateandTime);
        i.putExtra(EXTRA_MESSAGE,message);
        i.putExtra(EXTRA_COLOUR,colorResource);

        startActivity(i);


    }

    @Override
    public void setUpAdapterAndView(List<ListItem> listOfData) {

        this.listofData = listOfData;
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


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

                ListItem listItem =listofData.get(this.getAdapterPosition()
                );
                controller.onListItemClick(
                        listItem

                );


            }

        }


    }
}
