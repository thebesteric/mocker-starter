package io.github.thebesteric.framework.mocker.commons.utils;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.google.gson.Gson;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * JsonUtils
 *
 * @author Eric Joe
 * @version 1.0
 * @date 2020-11-01 00:13
 * @since 1.0
 */
public class JsonUtils {

    private static final Pattern PATTERN = Pattern.compile("\\s*|\t|\r|\n");

    public static Gson gson = new Gson();

    public static ObjectMapper objectMapper = new ObjectMapper()
            .configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true)
            .configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true)
            .configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false)
            .configure(SerializationFeature.FAIL_ON_SELF_REFERENCES, false)
            .setSerializationInclusion(JsonInclude.Include.ALWAYS);

    public static String toJsonStr(String str) {
        Matcher matcher = PATTERN.matcher(str);
        str = matcher.replaceAll("");
        str = str.replaceAll("\"(\\w+)\"", "$1");
        str = str.replace("\"", "");
        str = str.replace("'", "");
        str = str.replace(":", "\":\"");
        str = str.replace(",", "\",\"");
        str = str.replace("{", "{\"");
        str = str.replace("}", "\"}");
        str = str.replace("\"{", "{");
        str = str.replace("}\"", "}");
        str = str.replace("\"[", "[");
        str = str.replace("]\"", "]");
        return str;
    }

    public static String toJsonStr(Object obj) throws JsonProcessingException {
        return objectMapper.writeValueAsString(obj);
    }

    public static Object deepCopy(Object obj) {
        // non-args constructor are not supported by jackson, so we used gson instead
        return gson.fromJson(gson.toJson(obj), obj.getClass());
    }

}
