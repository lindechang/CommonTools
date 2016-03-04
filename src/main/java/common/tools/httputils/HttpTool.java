package common.tools.httputils;


import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;



public class HttpTool {

    private static final Logger logger = LoggerFactory.getLogger(HttpTool.class);

    public static StringBuffer httpConnect(String urlString, String param, Map<String, String> map,
                                           String method) {

        System.setProperty("sun.net.http.allowRestrictedHeaders", "true");
        HttpURLConnection con = null;
        URL url;
        OutputStreamWriter osw = null;
        StringBuffer buffer = new StringBuffer();
        try {
            url = new URL(urlString);
            con = (HttpURLConnection) url.openConnection();
            con.setConnectTimeout(10000);
            con.setRequestMethod(method);
            con.setDoOutput(true);
            con.setDoInput(true);
            con.setUseCaches(false);
            con.setRequestProperty("Charset", "UTF-8");
            if (map.size() > 0) {
                for (Map.Entry<String, String> entry : map.entrySet()) {
                    con.setRequestProperty(entry.getKey(), entry.getValue());
                }
            }
            if ("POST".equals(method) && !StringUtils.isEmpty(param)) {
                osw = new OutputStreamWriter(con.getOutputStream(), "UTF-8");
                osw.write(param);
                osw.flush();
            }
        } catch (Exception e) {
            logger.error("urlString: " + urlString + " param: " + param + " paraType: " + map
                    + " method: " + method, e);
            return buffer.append("timeout");
        } finally {
            if (osw != null) {
                try {
                    osw.close();
                } catch (Exception e) {
                    logger.error("" + e);
                }
            }
        }

        if (con != null) {
            con.disconnect();
            InputStream inputStream = null;
            BufferedReader br = null;
            try {
                inputStream = con.getInputStream();
            } catch (Exception e) {
                con.disconnect();
            }
            try {
                if (con.getResponseCode() == HttpURLConnection.HTTP_BAD_REQUEST) {
                    buffer.append("noAccount");
                } else if (inputStream != null && (con.getResponseCode() == HttpURLConnection.HTTP_OK || con.getResponseCode() == HttpURLConnection.HTTP_CREATED)) {
                    br = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
                    String temp;
                    while ((temp = br.readLine()) != null) {
                        buffer.append(temp);
                        buffer.append("\n");
                    }
                    if (buffer.length() == 0) {
                        buffer.append("success");
                    }
                } else {
                    buffer.append("error");
                }
                return buffer;
            } catch (Exception e) {
                logger.error("urlString: " + urlString + " param: " + param + " paraType: "
                        + map + " method: " + method, e);
            } finally {

                if (br != null) {
                    try {
                        br.close();
                    } catch (Exception e) {
                        logger.error("" + e);
                    }
                }
            }

        }

        return null;
    }


}
