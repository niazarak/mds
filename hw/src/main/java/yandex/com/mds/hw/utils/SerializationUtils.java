package yandex.com.mds.hw.utils;

import android.graphics.Color;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Modifier;
import java.lang.reflect.Type;

import yandex.com.mds.hw.models.ColorRecord;

public class SerializationUtils {
    public static final Gson GSON = new GsonBuilder()
            .excludeFieldsWithModifiers(Modifier.STATIC)
            .setDateFormat(TimeUtils.IsoDateFormat.toPattern())
            .create();

    public static final Gson GSON_SERVER = new GsonBuilder()
            .excludeFieldsWithModifiers(Modifier.STATIC)
            .setDateFormat(TimeUtils.IsoDateFormat.toPattern())
            .registerTypeAdapter(new TypeToken<ColorRecord>() {
            }.getType(), new NoteSerializer())
            .registerTypeAdapter(ColorRecord.class, new NoteDeserializer())
            .create();

    private static class NoteDeserializer implements JsonDeserializer<ColorRecord> {

        @Override
        public ColorRecord deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            JsonElement element = json.getAsJsonObject().remove("color");
            String color = element.getAsString();
            ColorRecord record = GSON.fromJson(json, ColorRecord.class);
            try {
                record.setColor(Color.parseColor(color));
            } catch (IllegalArgumentException e) {
                record.setColor(-1);
            }
            return record;
        }
    }

    private static class NoteSerializer implements JsonSerializer<ColorRecord> {

        @Override
        public JsonElement serialize(ColorRecord src, Type typeOfSrc, JsonSerializationContext context) {
            JsonElement element = GSON.toJsonTree(src);
            element.getAsJsonObject().remove("color");
            element.getAsJsonObject().addProperty("color", "#" + Integer.toHexString(src.getColor()));
            element.getAsJsonObject().remove("serverId");
            element.getAsJsonObject().remove("ownerId");
            element.getAsJsonObject().remove("id");
            return element;
        }
    }
}
