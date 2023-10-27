package qbittorrent.api.gson;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import io.quarkus.logging.Log;

import java.io.IOException;

/**
 * This class is used to prevent numbers in the API defined as a long from
 * returning java.lang.NumberFormatException when the value is larger than
 * a long.
 */
public class LongTypeAdapter extends TypeAdapter<Long> {

    @Override
    public Long read(JsonReader reader) throws IOException {
        if (reader.peek() == JsonToken.NULL) {
            reader.nextNull();
            return 0L;
        }
        String value = reader.nextString();
        try {
            return Long.valueOf(value);
        } catch (NumberFormatException e) {
            Log.warnv("Could not read long {0} which had an invalid long value. Defaulting value to 0.", value);
            return 0L;
        }
    }

    @Override
    public void write(JsonWriter writer, Long value) throws IOException {
        if (value == null) {
            writer.nullValue();
            return;
        }
        writer.value(value);
    }
}
