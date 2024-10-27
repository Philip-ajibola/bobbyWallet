package africa.semicolon.ppay.infrastructure.utils;

import org.apache.http.HttpResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class HelperClass {
    public static StringBuilder bufferReader(HttpResponse response, StringBuilder result) throws IOException {
        BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
        String line;

        while ((line = rd.readLine()) != null) {
            result.append(line);
        }
        return result;
    }
}
