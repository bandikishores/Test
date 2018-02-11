import java.io.*;
import java.util.*;
import java.text.*;
import java.math.*;
import java.util.regex.*;
import java.net.*;
import com.google.gson.*;

import lombok.Data;
import lombok.ToString;

public class OlaSolution {

    static Gson gson = new GsonBuilder().create();

    static class Response {
        public Integer        page;
        public Integer        per_page;
        public Integer        total;
        public Integer        total_pages;
        public ResponseData[] data;
    }

    static class ResponseData {
        public String  Poster;
        public String  Title;
        public String  Type;
        public Integer Year;
        public String  imdbID;
    }

    public static void main(String[] args) {
        String[] movieTitles = getMovieTitles("spiderman");
        if(movieTitles != null) {
            for(String str : movieTitles) {
                System.out.println(str);
            }
        }
    }

    static String[] getMovieTitles(String substr) {
        if (substr == null || substr.length() == 0) {
            return new String[0];
        }

        /* response = gson.fromJson(
                "{\"page\":1,\"per_page\":10,\"total\":13,\"total_pages\":2,\"data\":[{\"Poster\":\"https://images-na.ssl-images-amazon.com/images/M/MV5BYjFhN2RjZTctMzA2Ni00NzE2LWJmYjMtNDAyYTllOTkyMmY3XkEyXkFqcGdeQXVyNTA0OTU0OTQ@._V1_SX300.jpg\",\"Title\":\"Italian Spiderman\",\"Type\":\"movie\",\"Year\":2007,\"imdbID\":\"tt2705436\"},{\"Poster\":\"https://images-na.ssl-images-amazon.com/images/M/MV5BMjQ4MzcxNDU3N15BMl5BanBnXkFtZTgwOTE1MzMxNzE@._V1_SX300.jpg\",\"Title\":\"Superman, Spiderman or Batman\",\"Type\":\"movie\",\"Year\":2011,\"imdbID\":\"tt2084949\"},{\"Poster\":\"N/A\",\"Title\":\"Spiderman\",\"Type\":\"movie\",\"Year\":1990,\"imdbID\":\"tt0100669\"},{\"Poster\":\"N/A\",\"Title\":\"Spiderman\",\"Type\":\"movie\",\"Year\":2010,\"imdbID\":\"tt1785572\"},{\"Poster\":\"N/A\",\"Title\":\"Fighting, Flying and Driving: The Stunts of Spiderman 3\",\"Type\":\"movie\",\"Year\":2007,\"imdbID\":\"tt1132238\"},{\"Poster\":\"http://ia.media-imdb.com/images/M/MV5BMjE3Mzg0MjAxMl5BMl5BanBnXkFtZTcwNjIyODg5Mg@@._V1_SX300.jpg\",\"Title\":\"Spiderman and Grandma\",\"Type\":\"movie\",\"Year\":2009,\"imdbID\":\"tt1433184\"},{\"Poster\":\"N/A\",\"Title\":\"The Amazing Spiderman T4 Premiere Special\",\"Type\":\"movie\",\"Year\":2012,\"imdbID\":\"tt2233044\"},{\"Poster\":\"N/A\",\"Title\":\"Amazing Spiderman Syndrome\",\"Type\":\"movie\",\"Year\":2012,\"imdbID\":\"tt2586634\"},{\"Poster\":\"N/A\",\"Title\":\"Hollywood's Master Storytellers: Spiderman Live\",\"Type\":\"movie\",\"Year\":2006,\"imdbID\":\"tt2158533\"},{\"Poster\":\"N/A\",\"Title\":\"Spiderman 5\",\"Type\":\"movie\",\"Year\":2008,\"imdbID\":\"tt3696826\"}]}\n",
                Response.class);*/

        List<String> titles = getTitlesList(substr);
        if (titles != null && titles.size() > 0) {
            titles.sort(String::compareToIgnoreCase);
            return titles.toArray(new String[0]);
        } else {
            return new String[0];
        }
    }

    private static List<String> getTitlesList(String substr) {

        List<String> titles = new ArrayList<>();
        Response response = getResponse(substr, 0);
        if (response != null) {
            parseResponse(titles, response);
            int totalPages = response.total_pages;
            if(totalPages >= 2) {
                for (int i = 2; i <= totalPages; i++) {
                    Response tempResponse = getResponse(substr, i);
                    parseResponse(titles, tempResponse);
                }
            }
        }
        return titles;
    }

    private static void parseResponse(List<String> titles, Response tempResponse) {
        if (tempResponse != null && tempResponse.data != null && tempResponse.data.length > 0) {
            for (ResponseData responseData : tempResponse.data) {
                if (responseData.Title != null) {
                    titles.add(responseData.Title);
                }
            }
        }
    }

    private static Response getResponse(String substr, int pageNumber) {
        Response response = null;
        BufferedReader in = null;
        try {
            String getURL = "https://jsonmock.hackerrank.com/api/movies/search/?Title=" + substr;
            if (pageNumber > 0) {
                getURL += "&page=" + pageNumber;
            }
            URL hackerRankURL = new URL(getURL);
            URLConnection urlConnection = hackerRankURL.openConnection();
            in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            String finalOutput = "", inputLine;
            while ((inputLine = in.readLine()) != null) {
                finalOutput += inputLine;
            }
            response = gson.fromJson(finalOutput, Response.class);
        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            if(in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
        return response;
    }

}
