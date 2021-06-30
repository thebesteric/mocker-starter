package test;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.github.thebesteric.framework.mocker.commons.utils.JsonUtils;
import org.junit.Test;

public class JsonUtilsTest {

    @Test
    public void toJsonStr1() throws JsonProcessingException {
        String str = "{\n" +
                "  \"code\": 100,\n" +
                "  \"data\": {\n" +
                "    \"defaultWords\": [\n" +
                "      \"杯子1\"\n" +
                "    ],\n" +
                "    \"itemList\": [\n" +
                "      \"杯子1\",\n" +
                "      \"杯子2\",\n" +
                "      \"杯子3\"\n" +
                "    ],\n" +
                "    \"pageNum\": 1,\n" +
                "    \"pageSize\": 20,\n" +
                "    \"pages\": 1,\n" +
                "    \"total\": 20\n" +
                "  },\n" +
                "  \"timestamp\": 7274482514781304425,\n" +
                "  \"message\": \"OK\",\n" +
                "  \"trackId\": \"jf8V\"\n" +
                "}";
        checkJsonStr(str);
    }

    @Test
    public void toJsonStr2() throws JsonProcessingException {
        String str = "{\n" +
                "  \"code\": 100,\n" +
                "  \"data\": {\n" +
                "    \"associateWords\": [\n" +
                "      {\n" +
                "        \"code\": \"101\",\n" +
                "        \"name\": \"<h1>测试1</h1>\",\n" +
                "        \"nameWithColor\": \"<font color=#00a862>测试1</font>\"\n" +
                "      },\n" +
                "      {\n" +
                "        \"code\": \"102\",\n" +
                "        \"name\": \"<h1>测试2</h1>\",\n" +
                "        \"nameWithColor\": \"<font color=#00a862>测试2</font>\"\n" +
                "      },\n" +
                "      {\n" +
                "        \"code\": \"103\",\n" +
                "        \"name\": \"<h1>测试3</h1>\",\n" +
                "        \"nameWithColor\": \"<font color=#00a862>测试3</font>\"\n" +
                "      }\n" +
                "    ]\n" +
                "  },\n" +
                "  \"timestamp\": 620196975389378792,\n" +
                "  \"message\": \"yVDCcok\",\n" +
                "  \"trackId\": \"vRJqqn\"\n" +
                "}";
        checkJsonStr(str);
    }

    private void checkJsonStr(String str) throws JsonProcessingException {
        System.out.println(JsonUtils.objectMapper.readValue(JsonUtils.toJsonStr(str), Object.class));
    }

}
