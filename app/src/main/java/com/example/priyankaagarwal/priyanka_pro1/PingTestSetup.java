package com.example.priyankaagarwal.priyanka_pro1;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


public class PingTestSetup extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    Spinner _spinner;
    EditText _ET_HostName;
    EditText _ET_PacketCount;
    EditText _ET_Interval;
    EditText _ET_Delay;
    ListView _ListViewTC;
    Button _Btn_AddTC;
    Button _Btn_AddDealy;
    Button _BtnSave;
    Button _BtnDel;

    String _HostName;
    String _PacketSize;
    String _PacketCount;
    String _PacketInterval;
    String _Delay;
    int delete_item;

    ArrayList<String> TClist;
    ArrayAdapter<String> Adapter_TClist;
    ArrayList<TC_Type_Item> Info_TC_List;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ping_test_setup);
        setTitle("Ping Test Setup");

        Info_TC_List = new ArrayList<TC_Type_Item>();

        _ET_HostName = (EditText)findViewById(R.id.editTextHostName);
        _ET_PacketCount = (EditText)findViewById(R.id.editTextNumPackets);
        _ET_Interval = (EditText)findViewById(R.id.editTextInterval);
        _ET_Delay = (EditText)findViewById(R.id.editTextDelay);
        _ListViewTC = (ListView)findViewById(R.id.ListView_TC);

        _Btn_AddTC = (Button)findViewById(R.id.btn_PingTestSetupTest);
        _Btn_AddDealy = (Button)findViewById(R.id.btnAddDelay);
        _BtnSave = (Button)findViewById(R.id.btn_Save);
        _BtnDel = (Button)findViewById(R.id.btnDelete);

        _spinner = (Spinner)findViewById(R.id.spinner);
        ArrayAdapter adapter1 = ArrayAdapter.createFromResource(this,R.array.PacketSize,android.R.layout.simple_spinner_item);
        _spinner.setAdapter(adapter1);
        _spinner.setOnItemSelectedListener(this);

        TClist = new ArrayList<String>();
        Adapter_TClist = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,TClist);
        _ListViewTC.setAdapter(Adapter_TClist);
        _ListViewTC.setLongClickable(true);
        _ListViewTC.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                view.setSelected(true);
                return false;
            }
        });


        onAddClick();
        onDelayAddClick();
        onDeleteClick();


    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        TextView textselected = (TextView) view;
        Toast.makeText(this,"You selected " +  textselected.getText() , Toast.LENGTH_SHORT).show();
        _PacketSize = textselected.getText().toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }




    public  void onAddClick() {

        _Btn_AddTC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                _HostName = _ET_HostName.getText().toString();
                _PacketCount = _ET_PacketCount.getText().toString();
                _PacketInterval = _ET_Interval.getText().toString();

                String TC_Details =  "(P1:" + _HostName + " P2:" + _PacketSize + " P3:" + _PacketCount + " P4:" + _PacketInterval + ")";

                TClist.add(TC_Details);

                TC_Type_Item TC_Item_Temp = new TC_Type_Item();
                TC_Item_Temp.Bit_Delay = false;
                TC_Item_Temp.Bit_TC = true;
                TC_Item_Temp.Info_Host = _HostName;
                TC_Item_Temp.Info_Packet_Count = _PacketCount;
                TC_Item_Temp.Info_Interval = _PacketInterval;
                TC_Item_Temp.Info_Size = _PacketSize;
                TC_Item_Temp.Info_Delay = null;

                Info_TC_List.add(TC_Item_Temp);

                Adapter_TClist.notifyDataSetChanged();


            }
        });


    }


    public void onDelayAddClick(){

        _Btn_AddDealy.setOnClickListener( new View.OnClickListener() {


            @Override
            public void onClick(View view) {

                _Delay = _ET_Delay.getText().toString();

                String Delay_Details = "Delay for " + _Delay + "sec" ;

                TClist.add(Delay_Details);
                TC_Type_Item TC_Item_Temp2 = new TC_Type_Item();
                TC_Item_Temp2.Bit_Delay = true;
                TC_Item_Temp2.Bit_TC = false;
                TC_Item_Temp2.Info_Host = null;
                TC_Item_Temp2.Info_Packet_Count = null;
                TC_Item_Temp2.Info_Interval = null;
                TC_Item_Temp2.Info_Size = null;
                TC_Item_Temp2.Info_Delay = _Delay;

                Info_TC_List.add(TC_Item_Temp2);
                Adapter_TClist.notifyDataSetChanged();
            }
        });
    }


    public  void onDeleteClick(){
        _BtnDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Info_TC_List.remove(delete_item);
                TClist.remove(delete_item);
                Adapter_TClist.notifyDataSetChanged();

            }
        }

        );
    }

}
