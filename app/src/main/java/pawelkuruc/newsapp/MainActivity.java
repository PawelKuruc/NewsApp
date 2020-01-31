package pawelkuruc.newsapp;

import android.app.Activity;
import android.os.AsyncTask;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity{

    Spinner sCountry;
    Spinner sCategory;

    String selectedCountry;
    String selectedCategory;

    Button bSearch;

    TextView tvContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sCountry = (Spinner) findViewById(R.id.sCountry);
        ArrayAdapter<CharSequence> adapterCountry = ArrayAdapter.createFromResource(this,
                R.array.countryCodes, android.R.layout.simple_spinner_item);
        adapterCountry.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sCountry.setAdapter(adapterCountry);
        sCountry.setOnItemSelectedListener(new sCountryListener());

        sCategory = (Spinner) findViewById(R.id.sCategory);
        ArrayAdapter<CharSequence> adapterCategory = ArrayAdapter.createFromResource(this,
                R.array.categoryNames, android.R.layout.simple_spinner_item);
        adapterCategory.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sCategory.setAdapter(adapterCategory);
        sCategory.setOnItemSelectedListener(new sCategoryListener());

        bSearch = (Button) findViewById(R.id.bSearch);
        bSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Searching...", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                tvContent.setText("");
                new AsyncNetworkProcessing().execute(selectedCountry, selectedCategory);
            }
        });

        tvContent = (TextView) findViewById(R.id.tvContent);

    }

    private class sCountryListener implements AdapterView.OnItemSelectedListener {
        public void onItemSelected(AdapterView<?> parent, View view,
                                   int pos, long id) {
            if (parent.getItemAtPosition(pos).toString().equals("all")){
                selectedCountry = null;
            }else {
                selectedCountry = parent.getItemAtPosition(pos).toString();
            }
        }

        public void onNothingSelected(AdapterView<?> parent) {
        }
    }

    private class sCategoryListener implements AdapterView.OnItemSelectedListener {
        public void onItemSelected(AdapterView<?> parent, View view,
                                   int pos, long id) {
            selectedCategory = parent.getItemAtPosition(pos).toString();
        }

        public void onNothingSelected(AdapterView<?> parent) {
        }
    }

    private class AsyncNetworkProcessing extends AsyncTask <String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            NewsAPIRequest request = new NewsAPIRequest(NewsAPIKey.getAPIKey());
            request.requestTopHeadlines(params[0],params[1]);

            String queryResponse="";

                try{
                    queryResponse = "HTTP status code: "+request.getCode();
                    queryResponse += " Content: "+request.getContent();
                }catch (Exception e){
                    e.printStackTrace();
                }finally {
                    return queryResponse;
                }

        }

        @Override
        protected void onPostExecute(String queryResponse) {
            super.onPostExecute(queryResponse);

                tvContent.setText(queryResponse);

        }
    }
}
