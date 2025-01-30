package Utility;

import org.testng.annotations.DataProvider;

public class DataSet {

    @DataProvider(name = "validCredentials")
    public static Object ValidCredentials(){
        Object[][] data = {
                {"01303026151", "Anik54123"}
        };
        return data;
    }
}
