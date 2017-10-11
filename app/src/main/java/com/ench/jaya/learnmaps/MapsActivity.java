package com.ench.jaya.learnmaps;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static android.os.Build.VERSION_CODES.M;
import static com.ench.jaya.learnmaps.R.id.map;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    ListView listja;
   // TextView tx1,tx2;
    //String h;
    Double lat,lng;
    //String hh;
    int PERMISSION_ALL = 1;
    //private final int FIVE_SECONDS = 5000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(map);
        mapFragment.getMapAsync(this);
        listja=(ListView) findViewById(R.id.user);

        String[] PERMISSIONS = {Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.ACCESS_FINE_LOCATION};

        if(!hasPermissions(this, PERMISSIONS)){
            ActivityCompat.requestPermissions(this, PERMISSIONS, PERMISSION_ALL);
        }
        TrackGPS gps = new TrackGPS(MapsActivity.this);
        lat =gps .getLatitude();
         lng =  gps.getLongitude();
     //   tx1=(TextView)findViewById(R.id.textView);
       // tx2=(TextView) findViewById(R.id.textView2);
        final Handler handler = new Handler();

       // final String h= Double.toString(lat);
        //final String hh=Double.toString(lng);
//        tx2.setText(lng.toString());
      //  tx1.setText(h);
        //tx2.setText(hh);
//insertme(h,hh);
       // handler.postDelayed(new Runnable() {
         //   public void run() {
           //     Toast.makeText(getApplicationContext(),"jhiaOI",Toast.LENGTH_SHORT).show();
             //   insertme(h,hh);
                // this method will contain your almost-finished HTTP calls
               // handler.postDelayed(this, FIVE_SECONDS);
            //}
        //}, FIVE_SECONDS);
    }


    public static boolean hasPermissions(Context context, String... permissions) {
        if (android.os.Build.VERSION.SDK_INT >= M && context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }
   // private void insertme(final String h, final String hh) {
     //   StringRequest stringRequest = new StringRequest(Request.Method.POST, App_Url_Global.Login, new Response.Listener<String>() {
       //     @Override
         //   public void onResponse(String s) {

           // }
        //}, new Response.ErrorListener() {
          //  @Override
            //public void onErrorResponse(VolleyError volleyError) {
//
  //          }
    //    }) {
      //      @Override
        //    protected Map<String, String> getParams() throws AuthFailureError {
//
  //              Map<String,String> insest=new HashMap<String, String>();
    //            insest.put("lat_andrio",h);
      //          insest.put("lon_andrio",hh);


//                return insest;

//            }
  //      };
    //    AppController.getInstance().addToRequestQueue(stringRequest);
    //}



    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        new kilomilo().execute(Global_url.LOGIN);

        BitmapDescriptor icon1=BitmapDescriptorFactory.fromResource(R.drawable.image);
      //  BitmapDescriptor icon1= BitmapDescriptorFactory.fromResource(R.drawable.icon);
        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(lat,lng);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney").icon(icon1));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        double zoomLevel = 16.0; //This goes up to 21
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, (float) zoomLevel));
        mMap.addCircle(new CircleOptions()
                        .center(sydney)
                        .radius(5000)
                        .strokeWidth(1f)
                /*.fillColor(0x550000FF)*/);
    }
    public class MovieAdap extends ArrayAdapter {

        private List<all_days> movieModelList;
        private int resource;
        Context context;
        private LayoutInflater inflater;
        MovieAdap(Context context, int resource, List<all_days> objects) {
            super(context, resource, objects);
            movieModelList = objects;
            this.context =context;
            this.resource = resource;
            inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);

        }
        @Override
        public int getViewTypeCount() {

            return 1;
        }

        @Override
        public int getItemViewType(int position) {

            return position;
        }


        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {

            final ViewHolder holder  ;

            if(convertView == null){
                convertView = inflater.inflate(resource,null);
                holder = new ViewHolder();

                holder.textlon=(TextView)  convertView.findViewById(R.id.textView);
                holder.textlan=(TextView)  convertView.findViewById(R.id.textView2);
                convertView.setTag(holder);

            }
            else {
                holder = (ViewHolder) convertView.getTag();
            }
            all_days ccitac=movieModelList.get(position);
            holder.textlon.setText(ccitac.getLan());
            holder.textlan.setText(ccitac.getLon());
            lat=Double.parseDouble(ccitac.getLan());
            lng=Double.parseDouble(ccitac.getLon());
            mMap.addMarker(new MarkerOptions().position(new LatLng(lat, lng)).icon(BitmapDescriptorFactory.fromResource(R.drawable.icon)).title("jayanthi"));
            mMap.getUiSettings().setZoomControlsEnabled(true);
            return convertView;
        }




        class ViewHolder{
            public TextView textlan;
            public TextView textlon;



        }

    }
    public class kilomilo extends AsyncTask<String,String, List<all_days>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected List<all_days> doInBackground(String... params) {
            HttpURLConnection connection = null;
            BufferedReader reader = null;

            try {
                URL url = new URL(params[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();
                InputStream stream = connection.getInputStream();
                reader = new BufferedReader(new InputStreamReader(stream));
                StringBuilder buffer = new StringBuilder();
                String line = "";
                while ((line = reader.readLine()) != null) {
                    buffer.append(line);
                }

                String finalJson = buffer.toString();

                JSONObject parentObject = new JSONObject(finalJson);
                JSONArray parentArray = parentObject.getJSONArray("result");
                List<all_days> milokilo = new ArrayList<>();
                Gson gson = new Gson();
                for (int i = 0; i < parentArray.length(); i++) {
                    JSONObject finalObject = parentArray.getJSONObject(i);
                    all_days catego = gson.fromJson(finalObject.toString(), all_days.class);
                    milokilo.add(catego);

                }

                return milokilo;

            } catch (JSONException | IOException e) {
                e.printStackTrace();
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
                try {
                    if (reader != null) {
                        reader.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return null;

        }

        @Override
        protected void onPostExecute(final List<all_days> movieMode) {
            super.onPostExecute(movieMode);
            if (movieMode.size()>0)
            {
                MovieAdap adapter = new MovieAdap(getApplicationContext(), R.layout.user, movieMode);
                listja.setAdapter(adapter);
                listja.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        all_days item = movieMode.get(position);

                      //  Intent intent = new Intent(MainActivity.this, Main2Activity.class);
                        //intent.putExtra("sno",item.getSno());
                        //intent.putExtra("username",item.getUsername());
                        //startActivity(intent);
                    }
                });

            }
            else
            {
                Toast.makeText(getApplicationContext(),"Check your internet connection",Toast.LENGTH_SHORT).show();
            }



        }
    }
}
