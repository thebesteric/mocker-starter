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

    @Test
    public void toJsonStr3() throws JsonProcessingException {
        String str = "{\n" +
                "  \"code\": 100,\n" +
                "  \"data\": {\n" +
                "    \"link\": \"ftp://www.baidu.com\",\n" +
                "    \"url\": \"https://www.baidu.com\"\n" +
                "  },\n" +
                "  \"message\": \"OK\"\n" +
                "}";
        checkJsonStr(str);
    }

    @Test
    public void toJsonStr4() throws JsonProcessingException {
        String str = "{\n" +
                "                \"sequence\": 0,\n" +
                "                \"linkUrl\": null,\n" +
                "                \"linkText\": null,\n" +
                "                \"linkTitle\": null,\n" +
                "                \"id\": \"15914\",\n" +
                "                \"text\": \"<a href=\"https://www.starbucks.com.cn/help/legal/terms-of-starbucks-gift-card/\" target=\"_blank\">https://www.starbucks.com.cn/help/legal/terms-of-starbucks-gift-card/</a><br/>\",\n" +
                "                \"type\": \"GroupNotice\",\n" +
                "                \"title\": {\n" +
                "                    \"iconId\": 235,\n" +
                "                    \"mainTitleShow\": 1,\n" +
                "                    \"mainTitle\": \"须知\",\n" +
                "                    \"icon\": \"https://ml-storage.stg.starbucks.com.cn/app/sbuxappeclayout/f62430a4-3684-4b21-b718-edc681aa2de7\",\n" +
                "                    \"iconShow\": 1,\n" +
                "                    \"mainTitleBgColor\": \"\",\n" +
                "                    \"topMargin\": \"1\",\n" +
                "                    \"bottomMargin\": \"1\",\n" +
                "                    \"radius\": 1\n" +
                "                }\n" +
                "            }";
        checkJsonStr(str);
    }

    private void checkJsonStr(String str) throws JsonProcessingException {
        System.out.println(JsonUtils.objectMapper.readValue(JsonUtils.toJsonStr(str), Object.class));
    }

}
