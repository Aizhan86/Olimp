package com.omilia;

import com.omilia.diamant.dialog.components.fields.ApiField;
import com.omilia.diamant.dialog.components.fields.FieldStatus;
import java.io.BufferedReader;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import sun.net.www.http.HttpClient;
import java.io.FileReader;
import com.opencsv.CSVReader;
import java.io.File;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.io.*;
import java.net.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.util.*;

public class test {
    private static final String getBasicAuthenticationHeader(String username, String password) throws UnsupportedEncodingException {
        String valueToEncode = username + ":" + password;
        return "Basic " + Base64.getEncoder().encodeToString(valueToEncode.getBytes("UTF-8"));
    }

    public static void main(String[] args) throws IOException, RuntimeException {
        URL url = null;
        String address;
        String encoder;
        try {
            address = "кенесары+33";
            encoder = null;
                try {
                    encoder = URLEncoder.encode(address, String.valueOf(StandardCharsets.UTF_8));
                } catch (UnsupportedEncodingException var24) {
                    throw new RuntimeException(var24);
                }
            url = new URL("http://172.16.116.33:5555/ru/" + encoder + "/87770241470/id1/0/0");
        } catch (MalformedURLException var24) {
            throw new RuntimeException(var24);
        }
        System.out.println(String.valueOf(url));
        HttpURLConnection http;
        http = null;
        System.out.println(URLDecoder.decode(String.valueOf(url), StandardCharsets.UTF_8.name()));
        try {
            http = (HttpURLConnection) url.openConnection();
        } catch (IOException var23) {
            throw new RuntimeException(var23);
        }

        try {
            http.setRequestMethod("GET");
        } catch (ProtocolException var22) {
            throw new RuntimeException(var22);
        }
        String responseCode = null;
        try {
            responseCode = http.getResponseCode() + " " + http.getResponseMessage();
            System.out.println(String.valueOf(responseCode));
            if (!String.valueOf(http.getResponseCode()).equals("200")){
                System.out.println(String.valueOf("branchID:" + "-2"));
            }
        } catch (IOException var19) {
            throw new RuntimeException(var19);
        }

//        http.setDoOutput(true);
//        http.setRequestProperty("Accept", "application/json");
//        http.setRequestProperty("Content-Type", "application/json");
//        System.out.println(URLDecoder.decode(String.valueOf(url), StandardCharsets.UTF_8.name()));

        BufferedReader reader;
        reader = null;
        reader = new BufferedReader(new InputStreamReader(((HttpURLConnection) (new URL(String.valueOf(url))).openConnection()).getInputStream(), Charset.forName("UTF-8")));
        System.out.println(reader);
        StringBuilder json = new StringBuilder();

        while (true) {
            String lines;
            try {
                if ((lines = reader.readLine()) == null) {
                    break;
                }
            } catch (IOException var28) {
                throw new RuntimeException(var28);
            }

            json.append(lines);
        }

        System.out.println(String.valueOf(json));

        try {
            reader.close();
        } catch (IOException var21) {
            throw new RuntimeException(var21);
        }

        String branchID = "";
        String sendSMS = "";

        try {
            JSONObject jo = new JSONObject(String.valueOf(json));
            branchID = jo.get("id").toString();
            System.out.println(String.valueOf(branchID));
            if (!branchID.equals("-1") || !branchID.equals("-2")) {
                System.out.println(String.valueOf(branchID + "ru"));
            }
            sendSMS = String.valueOf(jo.get("sms"));
            System.out.println(String.valueOf(sendSMS));
        } catch (JSONException var27) {
            throw new RuntimeException(var27);
        }

        http.disconnect();
        String Response = "";
        if (branchID.equals("-1")) {
            Response = "Адрес не найден";
            System.out.println(String.valueOf("Response1:" + Response));
        } else {
            File file = new File("src/main/java/wowsh.csv");
            String absolute = file.getAbsolutePath();
            System.out.println("Current working directory in Java : " + absolute);
            String line = "";
            String splitBy = ",";
            List<List<String>> records = new ArrayList();

            try {
                BufferedReader br = new BufferedReader(new FileReader(absolute));

                while (true) {
                    if ((line = br.readLine()) == null) {
                        try {
                            br.close();
                            break;
                        } catch (IOException var19) {
                            throw new RuntimeException(var19);
                        }
                    }

                    String[] values = line.split(splitBy);
                    records.add(Arrays.asList(values));
                }
            } catch (IOException var25) {
                throw new RuntimeException(var25);
            } catch (RuntimeException var26) {
                throw new RuntimeException(var26);
            }

            System.out.println(records.get(Integer.parseInt(branchID)));
        }

    }
}



//public class test {



//    public static void main(String[] args) throws IOException {
//        String json1 = "{plist: [{\"HAND_FL\":0.0,\"TYPE\":\"ВнПТ\",\"DOC_NUMBER\":\"KZ156010002025840983\",\"DOC_DATE\":\"02-SEP-22\",\"ACCOUNT_NUMBER\":\"KZ726010002010264184\",\"PAY_SUMM\":\"2000\",\"CURRENCY\":\"KZT\",\"DT\":\"02-SEP-22\",\"NAME_NK\":\"АО \\\"Народный Банк Казахстана\\\"\",\"BIK_NK\":\"HSBKKZKX\",\"RNN_NK\":\"940140000385\"},{\"HAND_FL\":0.0,\"TYPE\":\"ВнПТ\",\"DOC_NUMBER\":\"KZ156010002025840982\",\"DOC_DATE\":\"02-SEP-22\",\"ACCOUNT_NUMBER\":\"KZ506015703100123118\",\"PAY_SUMM\":\"2000\",\"CURRENCY\":\"KZT\",\"DT\":\"02-SEP-22\",\"NAME_NK\":\"АО \\\"Народный Банк Казахстана\\\"\",\"BIK_NK\":\"HSBKKZKX\",\"RNN_NK\":\"940140000385\"},{\"HAND_FL\":0.0,\"TYPE\":\"ВнПТ\",\"DOC_NUMBER\":\"KZ1560100020258323240982\",\"DOC_DATE\":\"02-SEP-22\",\"ACCOUNT_NUMBER\":\"KZ546010002012965567\",\"PAY_SUMM\":\"2000\",\"CURRENCY\":\"KZT\",\"DT\":\"02-SEP-22\",\"NAME_NK\":\"АО \\\"Народный Банк Казахстана\\\"\",\"BIK_NK\":\"HSBKKZKX\",\"RNN_NK\":\"940140000385\"},{\"HAND_FL\":0.0,\"TYPE\":\"ВнПТ\",\"DOC_NUMBER\":\"KZ156010002025840982\",\"DOC_DATE\":\"02-SEP-22\",\"ACCOUNT_NUMBER\":\"KZ446010002654018963548\",\"PAY_SUMM\":\"2000\",\"CURRENCY\":\"KZT\",\"DT\":\"02-SEP-22\",\"NAME_NK\":\"АО \\\"Народный Банк Казахстана\\\"\",\"BIK_NK\":\"HSBKKZKX\",\"RNN_NK\":\"940140000385\"},{\"HAND_FL\":0.0,\"TYPE\":\"ВнПТ\",\"DOC_NUMBER\":\"KZ156010002025840982\",\"DOC_DATE\":\"02-SEP-22\",\"ACCOUNT_NUMBER\":\"KZ176010002018963549\",\"PAY_SUMM\":\"2000\",\"CURRENCY\":\"KZT\",\"DT\":\"02-SEP-22\",\"NAME_NK\":\"АО \\\"Народный Банк Казахстана\\\"\",\"BIK_NK\":\"HSBKKZKX\",\"RNN_NK\":\"940140000385\"},{\"HAND_FL\":0.0,\"TYPE\":\"ВнПТ\",\"DOC_NUMBER\":\"KZ156010002025840982\",\"DOC_DATE\":\"02-SEP-22\",\"ACCOUNT_NUMBER\":\"KZ71116010002018963547\",\"PAY_SUMM\":\"2000\",\"CURRENCY\":\"KZT\",\"DT\":\"02-SEP-22\",\"NAME_NK\":\"АО \\\"Народный Банк Казахстана\\\"\",\"BIK_NK\":\"HSBKKZKX\",\"RNN_NK\":\"940140000385\"},{\"HAND_FL\":0.0,\"TYPE\":\"ВнПТ\",\"DOC_NUMBER\":\"KZ156010002025840982\",\"DOC_DATE\":\"02-SEP-22\",\"ACCOUNT_NUMBER\":\"KZ626010002033446682\",\"PAY_SUMM\":\"2000\",\"CURRENCY\":\"KZT\",\"DT\":\"02-SEP-22\",\"NAME_NK\":\"АО \\\"Народный Банк Казахстана\\\"\",\"BIK_NK\":\"HSBKKZKX\",\"RNN_NK\":\"940140000385\"},{\"HAND_FL\":0.0,\"TYPE\":\"ВнПТ\",\"DOC_NUMBER\":\"KZ156010002025840982\",\"DOC_DATE\":\"02-SEP-22\",\"ACCOUNT_NUMBER\":\"KZ156010002025840982\",\"PAY_SUMM\":\"2000\",\"CURRENCY\":\"KZT\",\"DT\":\"02-SEP-22\",\"NAME_NK\":\"АО \\\"Народный Банк Казахстана\\\"\",\"BIK_NK\":\"HSBKKZKX\",\"RNN_NK\":\"940140000385\"},{\"HAND_FL\":0.0,\"TYPE\":\"ВнПТ\",\"DOC_NUMBER\":\"KZ156010002025840982\",\"DOC_DATE\":\"02-SEP-22\",\"ACCOUNT_NUMBER\":\"KZ356010002033446683\",\"PAY_SUMM\":\"2000\",\"CURRENCY\":\"KZT\",\"DT\":\"02-SEP-22\",\"NAME_NK\":\"АО \\\"Народный Банк Казахстана\\\"\",\"BIK_NK\":\"HSBKKZKX\",\"RNN_NK\":\"940140000385\"},{\"HAND_FL\":0.0,\"TYPE\":\"ВнПТ\",\"DOC_NUMBER\":\"KZ156010002025840982\",\"DOC_DATE\":\"02-SEP-22\",\"ACCOUNT_NUMBER\":\"KZ466010002033446679\",\"PAY_SUMM\":\"2000\",\"CURRENCY\":\"KZT\",\"DT\":\"02-SEP-22\",\"NAME_NK\":\"АО \\\"Народный Банк Казахстана\\\"\",\"BIK_NK\":\"HSBKKZKX\",\"RNN_NK\":\"940140000385\"},{\"HAND_FL\":0.0,\"TYPE\":\"ВнПТ\",\"DOC_NUMBER\":\"KZ156010002025840982\",\"DOC_DATE\":\"02-SEP-22\",\"ACCOUNT_NUMBER\":\"KZ196010002033446680\",\"PAY_SUMM\":\"2000\",\"CURRENCY\":\"KZT\",\"DT\":\"02-SEP-22\",\"NAME_NK\":\"АО \\\"Народный Банк Казахстана\\\"\",\"BIK_NK\":\"HSBKKZKX\",\"RNN_NK\":\"940140000385\"},{\"HAND_FL\":0.0,\"TYPE\":\"ВнПТ\",\"DOC_NUMBER\":\"KZ156010002025840982\",\"DOC_DATE\":\"02-SEP-22\",\"ACCOUNT_NUMBER\":\"KZ896010002033446681\",\"PAY_SUMM\":\"2000\",\"CURRENCY\":\"KZT\",\"DT\":\"02-SEP-22\",\"NAME_NK\":\"АО \\\"Народный Банк Казахстана\\\"\",\"BIK_NK\":\"HSBKKZKX\",\"RNN_NK\":\"940140000385\"},{\"HAND_FL\":0.0,\"TYPE\":\"ВнПТ\",\"DOC_NUMBER\":\"KZ156010002025840982\",\"DOC_DATE\":\"02-SEP-22\",\"ACCOUNT_NUMBER\":\"KZ036010002017813963\",\"PAY_SUMM\":\"2000\",\"CURRENCY\":\"KZT\",\"DT\":\"02-SEP-22\",\"NAME_NK\":\"АО \\\"Народный Банк Казахстана\\\"\",\"BIK_NK\":\"HSBKKZKX\",\"RNN_NK\":\"940140000385\"}]}";
//        String json2 = "{plist: [{\"HAND_FL\":0.0,\"TYPE\":\"ВнПТ\",\"DOC_NUMBER\":\"KZ156010002025840981\",\"DOC_DATE\":\"02-SEP-22\",\"ACCOUNT_NUMBER\":\"KZ726010002010264181\",\"PAY_SUMM\":\"2000\",\"CURRENCY\":\"KZT\",\"DT\":\"02-SEP-22\",\"NAME_NK\":\"АО \\\"Народный Банк Казахстана\\\"\",\"BIK_NK\":\"HSBKKZKX\",\"RNN_NK\":\"940140000385\"},{\"HAND_FL\":0.0,\"TYPE\":\"ВнПТ\",\"DOC_NUMBER\":\"KZ156010002025840981\",\"DOC_DATE\":\"02-SEP-22\",\"ACCOUNT_NUMBER\":\"KZ506015703100123118\",\"PAY_SUMM\":\"2000\",\"CURRENCY\":\"KZT\",\"DT\":\"02-SEP-22\",\"NAME_NK\":\"АО \\\"Народный Банк Казахстана\\\"\",\"BIK_NK\":\"HSBKKZKX\",\"RNN_NK\":\"940140000385\"},{\"HAND_FL\":0.0,\"TYPE\":\"ВнПТ\",\"DOC_NUMBER\":\"KZ156010002025840981\",\"DOC_DATE\":\"02-SEP-22\",\"ACCOUNT_NUMBER\":\"KZ546010002012965567\",\"PAY_SUMM\":\"2000\",\"CURRENCY\":\"KZT\",\"DT\":\"02-SEP-22\",\"NAME_NK\":\"АО \\\"Народный Банк Казахстана\\\"\",\"BIK_NK\":\"HSBKKZKX\",\"RNN_NK\":\"940140000385\"},{\"HAND_FL\":0.0,\"TYPE\":\"ВнПТ\",\"DOC_NUMBER\":\"KZ156010002025840981\",\"DOC_DATE\":\"02-SEP-22\",\"ACCOUNT_NUMBER\":\"KZ446010002654018963548\",\"PAY_SUMM\":\"2000\",\"CURRENCY\":\"KZT\",\"DT\":\"02-SEP-22\",\"NAME_NK\":\"АО \\\"Народный Банк Казахстана\\\"\",\"BIK_NK\":\"HSBKKZKX\",\"RNN_NK\":\"940140000385\"},{\"HAND_FL\":0.0,\"TYPE\":\"ВнПТ\",\"DOC_NUMBER\":\"KZ156010002025840981\",\"DOC_DATE\":\"02-SEP-22\",\"ACCOUNT_NUMBER\":\"KZ176010002018963549\",\"PAY_SUMM\":\"2000\",\"CURRENCY\":\"KZT\",\"DT\":\"02-SEP-22\",\"NAME_NK\":\"АО \\\"Народный Банк Казахстана\\\"\",\"BIK_NK\":\"HSBKKZKX\",\"RNN_NK\":\"940140000385\"},{\"HAND_FL\":0.0,\"TYPE\":\"ВнПТ\",\"DOC_NUMBER\":\"KZ156010002025840981\",\"DOC_DATE\":\"02-SEP-22\",\"ACCOUNT_NUMBER\":\"KZ71116010002018963547\",\"PAY_SUMM\":\"2000\",\"CURRENCY\":\"KZT\",\"DT\":\"02-SEP-22\",\"NAME_NK\":\"АО \\\"Народный Банк Казахстана\\\"\",\"BIK_NK\":\"HSBKKZKX\",\"RNN_NK\":\"940140000385\"},{\"HAND_FL\":0.0,\"TYPE\":\"ВнПТ\",\"DOC_NUMBER\":\"KZ156010002025840981\",\"DOC_DATE\":\"02-SEP-22\",\"ACCOUNT_NUMBER\":\"KZ626010002033446682\",\"PAY_SUMM\":\"2000\",\"CURRENCY\":\"KZT\",\"DT\":\"02-SEP-22\",\"NAME_NK\":\"АО \\\"Народный Банк Казахстана\\\"\",\"BIK_NK\":\"HSBKKZKX\",\"RNN_NK\":\"940140000385\"},{\"HAND_FL\":0.0,\"TYPE\":\"ВнПТ\",\"DOC_NUMBER\":\"KZ156010002025840981\",\"DOC_DATE\":\"02-SEP-22\",\"ACCOUNT_NUMBER\":\"KZ156010002025840982\",\"PAY_SUMM\":\"2000\",\"CURRENCY\":\"KZT\",\"DT\":\"02-SEP-22\",\"NAME_NK\":\"АО \\\"Народный Банк Казахстана\\\"\",\"BIK_NK\":\"HSBKKZKX\",\"RNN_NK\":\"940140000385\"},{\"HAND_FL\":0.0,\"TYPE\":\"ВнПТ\",\"DOC_NUMBER\":\"KZ156010002025840981\",\"DOC_DATE\":\"02-SEP-22\",\"ACCOUNT_NUMBER\":\"KZ356010002033446683\",\"PAY_SUMM\":\"2000\",\"CURRENCY\":\"KZT\",\"DT\":\"02-SEP-22\",\"NAME_NK\":\"АО \\\"Народный Банк Казахстана\\\"\",\"BIK_NK\":\"HSBKKZKX\",\"RNN_NK\":\"940140000385\"},{\"HAND_FL\":0.0,\"TYPE\":\"ВнПТ\",\"DOC_NUMBER\":\"KZ156010002025840981\",\"DOC_DATE\":\"02-SEP-22\",\"ACCOUNT_NUMBER\":\"KZ466010002033446679\",\"PAY_SUMM\":\"2000\",\"CURRENCY\":\"KZT\",\"DT\":\"02-SEP-22\",\"NAME_NK\":\"АО \\\"Народный Банк Казахстана\\\"\",\"BIK_NK\":\"HSBKKZKX\",\"RNN_NK\":\"940140000385\"},{\"HAND_FL\":0.0,\"TYPE\":\"ВнПТ\",\"DOC_NUMBER\":\"KZ156010002025840981\",\"DOC_DATE\":\"02-SEP-22\",\"ACCOUNT_NUMBER\":\"KZ196010002033446680\",\"PAY_SUMM\":\"2000\",\"CURRENCY\":\"KZT\",\"DT\":\"02-SEP-22\",\"NAME_NK\":\"АО \\\"Народный Банк Казахстана\\\"\",\"BIK_NK\":\"HSBKKZKX\",\"RNN_NK\":\"940140000385\"},{\"HAND_FL\":0.0,\"TYPE\":\"ВнПТ\",\"DOC_NUMBER\":\"KZ156010002025840981\",\"DOC_DATE\":\"02-SEP-22\",\"ACCOUNT_NUMBER\":\"KZ896010002033446681\",\"PAY_SUMM\":\"2000\",\"CURRENCY\":\"KZT\",\"DT\":\"02-SEP-22\",\"NAME_NK\":\"АО \\\"Народный Банк Казахстана\\\"\",\"BIK_NK\":\"HSBKKZKX\",\"RNN_NK\":\"940140000385\"},{\"HAND_FL\":0.0,\"TYPE\":\"ВнПТ\",\"DOC_NUMBER\":\"KZ156010002025840981\",\"DOC_DATE\":\"02-SEP-22\",\"ACCOUNT_NUMBER\":\"KZ036010002017813963\",\"PAY_SUMM\":\"2000\",\"CURRENCY\":\"KZT\",\"DT\":\"02-SEP-22\",\"NAME_NK\":\"АО \\\"Народный Банк Казахстана\\\"\",\"BIK_NK\":\"HSBKKZKX\",\"RNN_NK\":\"940140000385\"}]}";
//        try {
//            getjson(json2);
//        } catch (JSONException e) {
//            throw new RuntimeException(e);
//        }
        /**
         * Send a 1-way SMS from "XYZCorp"
         *
         *
         */
//
//public class test {
//    public static void main(String[] args) throws IOException, RuntimeException, JSONException {
//        Map<String, ApiField> output = new HashMap<>();
//        String branchID = "";
//        String index = "";
//        System.out.println("Started");
//        URL url = new URL("http://10.0.1.23:5555/ru/"+(URLEncoder.encode("сыганак 43", String.valueOf(StandardCharsets.UTF_8)) +"/87066160888/id1"));
//        System.out.println("Get URL");
//        try {
//            System.out.println("In try");
//            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//            conn.setRequestMethod("POST");
//            System.out.println("Give the Request Method");
//            conn.setDoOutput(true);
//            BufferedReader rd = new BufferedReader(
//                    new InputStreamReader(conn.getInputStream()));
//            String result = rd.readLine();
//            System.out.println("Getting Result");
//            rd.close();
//            System.out.println(result);
//            if (result == null) {
//                System.out.println("No response received");
//            } else if (result.startsWith("id:")) {
//                System.out.println("Message sent successfully");
//            } else {
//                System.out.println("Error - " + result);
//            }
//        } catch (Exception e) {
//            System.out.println("Error - " + e);
//        }
//    }
//}
//                JSONObject jo;
//                JSONObject v;
//                JSONObject f;
//                try {
//                    jo = new JSONObject(index);
//                    v = jo.getJSONObject("id");
//                    branchID = String.valueOf(v.get("id"));
//                    System.out.println(branchID);
//                    System.out.println(ApiField.builder().value(branchID).status(FieldStatus.DEFINED).build());
//                    f = jo.getJSONObject("call_status");
//                    String sendSMS = String.valueOf(f.get("call_status"));
//                    System.out.println(sendSMS);
//                    System.out.println(ApiField.builder().value(sendSMS).status(FieldStatus.DEFINED).build());
//                } catch (JSONException e) {
//                    throw new RuntimeException(e);

//        private static String getUrlContents(String theUrl) {
//            StringBuilder content = new StringBuilder();
//            try {
//                URL url = new URL(theUrl); // creating a url object
//                URLConnection urlConnection = url.openConnection(); // creating a urlconnection object
//
//                // wrapping the urlconnection in a bufferedreader
//                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
//                String line;
//                // reading from the urlconnection using the bufferedreader
//                while ((line = bufferedReader.readLine()) != null)
//                {
//                    content.append(line + "\n");
//                }
//                bufferedReader.close();
//            }
//            catch(Exception e)
//            {
//                e.printStackTrace();
//            }
//
//            return content.toString();
//        }
//    }



//        try {
//            String auth = "{'kdl2', '9Ewq5tqV8'}";
//
//            String{} headers = {
//                    'authorization': 'Basic XXXXXXXXXXXXXXXXXXXXXXXX==',
//                    'cache-control': 'no-cache',
//                    'content-type': 'application/json',
//                    };
//
//            String params = String.format("{\"from\":\\"KDL_OLYMP\",\\"to\":\\"77050241470\",\\"text\":\\"норм пашет\"}");
//
//            // Send request to the API servers over HTTPS
//            URL url = new URL("http://isms.center/api/sms/send");
//            URLConnection conn = url.openConnection();
//            conn.setRequestMethod("POST");
//            conn.setRequestProperty(headers);
//            conn.setDoOutput(true);
//            OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
//                byte[] input = params.getBytes("utf-8");
//            wr.write(params);
//            wr.flush();
//
//            // The response from the gateway is going to look like this:
//            // id: a4c5ad77ad6faf5aa55f66a
//            //
//            // In the event of an error, it will look like this:
//            // err: invalid login credentials
//            BufferedReader rd = new BufferedReader(
//                    new InputStreamReader(conn.getInputStream()));
//            String result = rd.readLine();
//            wr.close();
//            rd.close();
//
//            if(result == null) {
//                System.out.println("No response received");
//            }
//            else if(result.startsWith("id:")) {
//                System.out.println("Message sent successfully");
//            }
//            else {
//                System.out.println("Error - " + result);
//            }
//        }
//        catch (Exception e) {
//            System.out.println("Error - " + e);
//        }
//    }

//        String ani = "87055472852";
//        String[] code_num = ani.split("");
//        List<String> numberList = Arrays.asList("700", "701","702", "705", "707", "708", "747", "771", "775", "776", "777", "778");
//        String number = "";
//        for(int i=0; i<code_num.length;i++) {
//            if (code_num.length == 11 && i > 0 && i < 4) {
//                number += code_num[i];
//                System.out.println(code_num[i]);
//            } else if (code_num.length == 12 && i > 1 && i < 5) {
//                number += code_num[i];
//                System.out.println(code_num[i]);
//            }
//        }
//        for(int i=0; i<numberList.size()-1;i++){
//            if(number.equals(numberList.get(i))){
////                System.out.println(number.equals(numberList.get(i)));
//                System.out.println(numberList.get(i));
//            }
//        Assert.assertTrue(numberList.contains(number));
//        }
//
//
//        private static void getjson(String json) throws JSONException {
//            JsonObject jsonObject = new JsonParser().parse(json).getAsJsonObject();
//
//            JsonArray jsonObjectArray;
//            jsonObjectArray = jsonObject.getAsJsonArray("plist");
//            JsonArray jsonObjectArraycopy = new JsonArray();
//
//            JsonObject arrayObject = new JsonParser().parse(jsonObjectArray.get(0).toString()).getAsJsonObject();
//            jsonObjectArraycopy.add(arrayObject);
//
//            for(int i=1; i < jsonObjectArray.size(); i++) {
//                JsonObject arrayObject2 = new JsonParser().parse(jsonObjectArray.get(i).toString()).getAsJsonObject();
//                int size = 0;
//                int j=0;
//                while (j<jsonObjectArraycopy.size()) {
//                    JsonObject arrayObject3 = new JsonParser().parse(jsonObjectArraycopy.get(j).toString()).getAsJsonObject();
//                    if (arrayObject2.get("DOC_NUMBER").equals(arrayObject3.get("DOC_NUMBER"))) {
//                        break;
//                    } else {
//                        size++;
//                    }
//                    j++;
//                }
//                if (size == jsonObjectArraycopy.size()) {
//                    jsonObjectArraycopy.add(arrayObject2);
//                }
//            }
//            System.out.println(jsonObjectArraycopy);
//        }
//        }

//    private static String getUrlContents(String theUrl) {
//        StringBuilder content = new StringBuilder();
//        try {
//            URL url = new URL(theUrl); // creating a url object
//            URLConnection urlConnection = url.openConnection(); // creating a urlconnection object
//
//            // wrapping the urlconnection in a bufferedreader
//            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
//            String line;
//            // reading from the urlconnection using the bufferedreader
//            while ((line = bufferedReader.readLine()) != null)
//            {
//                content.append(line + "\n");
//            }
//            bufferedReader.close();
//        }
//        catch(Exception e)
//        {
//            e.printStackTrace();
//        }
//
//        return content.toString();
//    }
//}