package com.udacity.sandwichclub.utils;

import android.util.Log;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    private static final String LOG_TAG = JsonUtils.class.getSimpleName();


    private static final String SANDWICH_NAME = "name";

    private static final String SANDWICH_MAIN_NAME = "mainName";

    private static final String KNOWN_AS = "alsoKnownAs";

    private static final String SANDWICH_ORIGIN = "placeOfOrigin";

    private static final String DESCRIPTION = "description";

    private static final String IMAGE_PATH = "image";

    private static final String INGREDIENTS = "ingredients";


    public static Sandwich parseSandwichJson(String json) {

        Sandwich sandwich = new Sandwich();

        List<String> alsoKnownAsList = new ArrayList<>();
        List<String> ingredientsList = new ArrayList<>();

        try {

            JSONObject sandwichJsonObject = new JSONObject(json);

            // Extract the current sandwich name
            JSONObject sandwichObjectName = sandwichJsonObject.getJSONObject(SANDWICH_NAME);
            // Get the string with the current name
            String sandwichName = sandwichObjectName.getString(SANDWICH_MAIN_NAME);

            // Extract the current sandwich also known as
            JSONArray alsoKnownArray = sandwichObjectName.getJSONArray(KNOWN_AS);
            for (int i = 0; i < alsoKnownArray.length(); i++) {
                if (alsoKnownArray.length() != 0) {

                    String value = alsoKnownArray.getString(i);

                    String knownAs = String.valueOf(value);
                    alsoKnownAsList.add(knownAs);
                }
            }

            // Extract the current sandwich place of origin
            String sandwichOrigin = "";
            if (sandwichJsonObject.has(SANDWICH_ORIGIN)) {
                sandwichOrigin = sandwichJsonObject.getString(SANDWICH_ORIGIN);
            }

            // Extract the current sandwich description
            String sandwichDescription = sandwichJsonObject.getString(DESCRIPTION);

            // Extract the current sandwich image path
            String sandwichImagePath = sandwichJsonObject.getString(IMAGE_PATH);

            JSONArray ingredientsArray = sandwichJsonObject.getJSONArray(INGREDIENTS);
            for (int n = 0; n < ingredientsArray.length(); n++) {
                if (ingredientsArray.length() != 0) {
                    String ingredient = ingredientsArray.getString(n);
                    String sandwichIngredient = String.valueOf(ingredient);
                    ingredientsList.add(sandwichIngredient);
                }
            }

            Log.v(LOG_TAG, "Sandwich name: " + sandwichName +
                    " \nAlso known as: " + alsoKnownAsList + " \nOrigin: " + sandwichOrigin +
                    " \nDescription: " + sandwichDescription + " \nImage path: " + sandwichImagePath +
                    " \nIngredients: " + ingredientsList);

            sandwich = new Sandwich(sandwichName, alsoKnownAsList, sandwichOrigin,
                    sandwichDescription, sandwichImagePath, ingredientsList);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return sandwich;
    }
}
