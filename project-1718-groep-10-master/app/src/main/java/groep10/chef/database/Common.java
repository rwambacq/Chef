package groep10.chef.database;

import groep10.chef.Class.Ingredient;

/**
 * Created by thoma on 13/04/2018.
 */

public class Common {
    private static String DB_NAME="chef";
    private static String COLLECTION_NAME="ingredients2";
    private static String COLLECTION_NAME2="recepten1";
    public static String API_KEY="ltA5mpjkFYLLVKgnPXrUk-xpNNmWfKEZ";

    public static String getAddressSingle(Ingredient ingredient){
        String baseUrl = String.format("https://api.mlab.com/api/1/databases/%s/collections/%s",DB_NAME,COLLECTION_NAME);
        StringBuilder stringBuilder = new StringBuilder(baseUrl);
        stringBuilder.append("/"+ingredient.get_id().getOid()+"?apiKey="+API_KEY);
        return stringBuilder.toString();
    }

    public static String getAddressAPI(){
        String baseUrl = String.format("https://api.mlab.com/api/1/databases/%s/collections/%s",DB_NAME,COLLECTION_NAME);
        StringBuilder stringBuilder = new StringBuilder(baseUrl);
        stringBuilder.append("?apiKey="+API_KEY);
        return stringBuilder.toString();

    }
    public static String getAddressAPI2(){
        String baseUrl = String.format("https://api.mlab.com/api/1/databases/%s/collections/%s",DB_NAME,COLLECTION_NAME2);
        StringBuilder stringBuilder = new StringBuilder(baseUrl);
        stringBuilder.append("?apiKey="+API_KEY);
        return stringBuilder.toString();

    }
}
