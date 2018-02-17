package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import java.util.Arrays;
import java.util.List;

public class DetailActivity extends AppCompatActivity {

    private static final String LOG_TAG = "DetailActivity";


    private static final String KNOWN_AS = "alsoKnownAs";

    private static final String SANDWICH_ORIGIN = "placeOfOrigin";

    private static final String DESCRIPTION = "description";

    private static final String IMAGE_PATH = "image";

    private static final String INGREDIENTS = "ingredients";

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    private TextView mOrigin;
    private TextView mDescription;
    private TextView mIngredients;
    private TextView mAlsoKnownAs;

    private String mOriginString;
    private String mDescriptionString;
    private List<String> mIngredientsList;
    private List<String> mAlsoKnownAsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView ingredientsIv = findViewById(R.id.image_iv);
        mOrigin = findViewById(R.id.origin_tv);
        mDescription = findViewById(R.id.description_tv);
        mIngredients = findViewById(R.id.ingredients_tv);
        mAlsoKnownAs = findViewById(R.id.also_known_tv);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        if (intent.hasExtra(SANDWICH_ORIGIN)) {
            mOriginString = intent.getStringExtra(SANDWICH_ORIGIN);
            mOrigin.setText(mOriginString);
        }

        if (intent.hasExtra(DESCRIPTION)) {
            // Has to be a List<String>
            mDescriptionString = intent.getStringExtra(DESCRIPTION);
            mDescription.setText(mDescriptionString);
        }

        if (intent.hasExtra(KNOWN_AS)) {
            // Both are working, which is better?
            int nameIndex = getIntent().getIntExtra(KNOWN_AS, 0);
            mAlsoKnownAs.append(mAlsoKnownAsList.get(nameIndex));

            // Both are working, which is better?
//            mAlsoKnownAsList = intent.getStringArrayListExtra(KNOWN_AS);
//            String value = mAlsoKnownAsList.get(position);
//            mAlsoKnownAs.setText(value);
        }

        if (intent.hasExtra(INGREDIENTS)) {
            int ingredientIndex = intent.getIntExtra(INGREDIENTS, 0);
            mIngredients.append(mIngredientsList.get(ingredientIndex));
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        Sandwich sandwich = JsonUtils.parseSandwichJson(json);
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI();
        Picasso.with(this)
                .load(sandwich.getImage())
                .into(ingredientsIv);

        setTitle(sandwich.getMainName());
        mOrigin.setText(sandwich.getPlaceOfOrigin());
        mDescription.setText(sandwich.getDescription());

        mAlsoKnownAs.setText(sandwich.getAlsoKnownAs().toString());

//        String alsoKnownAsString = mAlsoKnownAs.toString();
//        String[] part =alsoKnownAsString.split("\\s*,\\s*");


        mIngredients.setText(sandwich.getIngredients().toString());

    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI() {


    }
}
