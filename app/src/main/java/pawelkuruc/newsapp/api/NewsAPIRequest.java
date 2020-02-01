package pawelkuruc.newsapp.api;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class NewsAPIRequest {

    private String APIKey;

    private Throwable lastError;
    private int lastCode;
    private String lastContent;

    boolean anyParameterUsed=false;

    public NewsAPIRequest(String APIKey) {
        this.APIKey = APIKey;

        lastError = null;
        lastContent = "";
    }

    public boolean requestTopHeadlines(String country, String category){
        lastError = null;
        lastCode = 0;
        lastContent = "";
        anyParameterUsed = false;

        String requestURL = "https://newsapi.org/v2/top-headlines?";
        if(!(country == null || country.equals(""))){
            requestURL += "country="+country+"&";
            anyParameterUsed=true;
        }
        if(!(category == null || category.equals(""))){
            requestURL += "category="+category+"&";
            anyParameterUsed=true;
        }
        if (!anyParameterUsed){
            requestURL += "category=general&";
        }

        requestURL += "apiKey="+APIKey;

        try {
            HttpURLConnection connection = (HttpURLConnection) new URL(requestURL).openConnection();
            connection.connect();

            lastCode = connection.getResponseCode();

            if (200 == lastCode || 401 == lastCode || 404 == lastCode) {
                BufferedReader rd = new BufferedReader(new InputStreamReader(lastCode==200?connection.getInputStream():connection.getErrorStream()));
                StringBuffer sb = new StringBuffer();
                String line;
                while ((line = rd.readLine()) != null) {
                    sb.append(line);
                }
                rd.close();
                lastContent = sb.toString();
            }

            return (lastCode == 200);

        } catch (Exception e) {
            lastError = e;
        }
        return false;
    }

    public String getContent(){
        return lastContent;
    }

    public int getCode(){
        return lastCode;
    }
}
