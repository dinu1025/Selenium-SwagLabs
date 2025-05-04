package utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;

import java.io.InputStream;
import java.util.List;

import org.testng.annotations.DataProvider;

public class JsonDataProvider {

    @DataProvider(name = "loginData")
    public Object[][] getLoginData() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        InputStream is = getClass().getClassLoader().getResourceAsStream("loginData.json");
        List<LoginData> data = mapper.readValue(is, new TypeReference<List<LoginData>>() {
        });
        Object[][] result = new Object[data.size()][2];
        for (int i = 0; i < data.size(); i++) {
            result[i][0] = data.get(i).username;
            result[i][1] = data.get(i).password;
        }
        return result;
    }
}