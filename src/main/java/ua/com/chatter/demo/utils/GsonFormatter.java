package ua.com.chatter.demo.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class GsonFormatter {

    public static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

}
