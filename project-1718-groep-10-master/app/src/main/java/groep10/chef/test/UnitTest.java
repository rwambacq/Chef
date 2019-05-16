package groep10.chef.test;

import android.content.Context;

import java.util.Arrays;
import java.util.List;

import groep10.chef.Class.Ingredient;
import groep10.chef.Class.Recipe;

import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;

public class UnitTest {

    public void assertListsFilled(List<Ingredient> list1, List<Recipe> list2) throws Exception {
        assertNotNull(list1);
        assertNotNull(list2);
        assertTrue(list1.size() != 0);
        assertTrue(list2.size() != 0);
    }

    public void assertFilesMade(List<String> fileList, String RECIPE_FILE, String INGREDIENT_FILE) throws Exception {
        assertTrue(fileList.size() != 0);
        assertTrue(fileList.contains(RECIPE_FILE));
        assertTrue(fileList.contains(INGREDIENT_FILE));
    }


}