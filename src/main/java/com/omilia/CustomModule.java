//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.omilia;

import com.omilia.diamant.application.Application;
import com.omilia.diamant.custommodule.CustomModuleAdaptor;
import com.omilia.diamant.custommodule.DataPooler;
import com.omilia.diamant.dialog.components.fields.ApiField;
import com.omilia.diamant.dialog.components.fields.FieldStatus;
import com.omilia.diamant.dialog.components.fields.FieldsContainer;
import com.omilia.diamant.dialog.eventlogger.EventLogger;
import com.omilia.diamant.dialog.output.EndType;
import com.omilia.diamant.loggers.DialogLogger;
import com.omilia.diamant.ondialogclosing.OutgoingSessionData;
import com.omilia.diamant.loggers.GenericLogger;
import com.omilia.diamant.managers.DialogManager;
import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.io.*;
import java.net.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.*;


public class CustomModule extends CustomModuleAdaptor {
    private Map<String, ApiField> output = new HashMap();
    private Map<String, ApiField> input = new HashMap();
    private final String MODULE_NAME = "Olimp";
    private FieldsContainer fieldsContainer;
    static Instant my_timeStamp_start;

    public CustomModule() {
    }

    String branchID = "";
    String address;

    public Map<String, ApiField> applyCustomAction(String function, Map<String, ApiField> input) {
        Map<String, ApiField> output = new HashMap();
        this.logger.log("start");
        String Ani = ((ApiField) input.get("Ani")).getValue();
        String DialogID = ((ApiField) input.get("DialogID")).getValue();
        String NFaddressCounter = ((ApiField) input.get("NFaddressCounter")).getValue();
        String locale = ((ApiField) input.get("Language")).getValue();
        String street = ((ApiField) input.get("askStreet")).getValue();
        String houseNumber = ((ApiField) input.get("askHouseNumber")).getValue();
        String savedBranchID = ((ApiField) input.get("savedBranchID")).getValue();
        this.logger.log("Ani " + Ani);
        this.logger.log("NFaddressCounter " + NFaddressCounter);
        this.logger.log("DialogID " + DialogID);
        this.logger.log("locale1 " + locale);
        this.logger.log("street " + street);
        this.logger.log("houseNumber: " + houseNumber);
        this.logger.log("savedBranchID " + savedBranchID);
        if (function.equals("branchAddress")) {
            if (locale.equals("kz")) {
                locale = "kz";
            } else {
                locale = "ru";
            }

            if (!NFaddressCounter.equals("0")) {
                NFaddressCounter = "1";
            }

            this.logger.log("locale2 " + locale);
            URL url = null;
            this.logger.log("URL url = null;");

            String encoder;
            String address;

            try {
                encoder = street + "+" + houseNumber;
                address = null;
                try {
                    address = URLEncoder.encode(encoder, String.valueOf(StandardCharsets.UTF_8));
                } catch (UnsupportedEncodingException var24) {
                    throw new RuntimeException(var24);
                }

                url = new URL("http://172.16.116.33:5555/" + locale + "/" + address + "/" + Ani + "/" + DialogID + "/" + NFaddressCounter + "/" + savedBranchID);
            } catch (MalformedURLException var25) {
                throw new RuntimeException(var25);
            }
            HttpURLConnection http;
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
                this.logger.log(String.valueOf(responseCode));
                if (!String.valueOf(http.getResponseCode()).equals("200")){
                    output.put("branchID", ApiField.builder().value("-2").status(FieldStatus.DEFINED).build());
                }
            } catch (IOException var19) {
                throw new RuntimeException(var19);
            }
            this.logger.log("url " + String.valueOf(url));

            try {
                this.logger.log(URLDecoder.decode(String.valueOf(url), StandardCharsets.UTF_8.name()));
            } catch (UnsupportedEncodingException e) {
                throw new RuntimeException(e);
            }

            BufferedReader rd;
            try {
                rd = new BufferedReader(new InputStreamReader(((HttpURLConnection) (new URL(String.valueOf(url))).openConnection()).getInputStream(), Charset.forName("UTF-8")));
                this.logger.log("reader" + rd);
            } catch (IOException var21) {
                throw new RuntimeException(var21);
            }

            StringBuilder json = new StringBuilder();
            this.logger.log("StringBuilder json");
            while (true) {
                String lines;
                try {
                    if ((lines = rd.readLine()) == null) {
                        break;
                    }
                } catch (IOException var27) {
                    throw new RuntimeException(var27);
                }
                json.append(lines);
            }
            this.logger.log(String.valueOf(json));
            try {
                rd.close();
            } catch (IOException var20) {
                throw new RuntimeException(var20);
            }

            this.logger.log("step before getting branchID");
            String branchIDLocale = "";
            String sendSMS = "";
            try {
                JSONObject jo = new JSONObject(String.valueOf(json));
                this.branchID = jo.get(String.valueOf("id")).toString();
                output.put("branchID", ApiField.builder().value(String.valueOf(this.branchID)).status(FieldStatus.DEFINED).build());
                this.logger.log("branchID: " + this.branchID);
                if (!this.branchID.equals("-1") || !branchID.equals("-2")) {
                    output.put("branchIDLocale", ApiField.builder().value(this.branchID + locale).status(FieldStatus.DEFINED).build());
                    this.logger.log("branchIDLocale: " + this.branchID + locale);
                }
                sendSMS = jo.get(String.valueOf("sms")).toString();
                output.put("sendSMS", ApiField.builder().value(String.valueOf(sendSMS)).status(FieldStatus.DEFINED).build());
                this.logger.log("SMS: " + sendSMS);
            } catch (JSONException var26) {
                throw new RuntimeException(var26);
            }
            http.disconnect();
        }
        return output;
    }

    public boolean onApplicationStart(Map<String, Object> map) {
//        GenericLogger logger = DialogManager.getInstance().getLogger();
//        logger.log("Sample_module is started RU ");
        return true;
    }

    public boolean onDialogStart(FieldsContainer fieldsContainer) {
//        DialogLogger var10000 = this.logger;
//        StringBuilder var10001 = new StringBuilder();
//        this.getClass();
//        var10000.log(var10001.append("Olimp").append(" CustomModule instance initialized").toString());
//        this.fieldsContainer = fieldsContainer;
//        my_timeStamp_start = Instant.parse(this.fieldsContainer.getFieldInstanceValueByName("Timestamp"));
//        this.logger.logGreen("MY TIME START::: " + my_timeStamp_start);
        return true;
    }

    public boolean onDialogClose(FieldsContainer fieldsContainer, String s) {
        String DialogID = String.valueOf(fieldsContainer.getField("DialogID").getFieldInstanceValue());
        String Ani = String.valueOf(fieldsContainer.getField("Ani").getFieldInstanceValue());
        String stepCounter = String.valueOf(fieldsContainer.getField("stepCounter").getFieldInstanceValue());
        String Timestamp = String.valueOf(fieldsContainer.getField("Timestamp").getFieldInstanceValue());
//        Duration duration = Duration.between(my_timeStamp_start, Timestamp);
        String pathToCsv = "/omilia/apps/DiaManT/apps/Olimp/data.csv";
        File file = new File(pathToCsv);
        FileOutputStream fileOutputStream = null;
        List<String> events_via_EventLogger = new ArrayList();
        boolean eligibleForNPS = false;

        EventLogger log;
        for (Iterator var20 = this.dialogEvents.iterator(); var20.hasNext(); events_via_EventLogger.add(log.format())) {
            log = (EventLogger) var20.next();
            if (log.format().matches(".*selfServe:completed.*")) {
                eligibleForNPS = true;
            }
        }

        String endTyped = (String) events_via_EventLogger.get(events_via_EventLogger.size() - 1);
        String endtypes = "";
        if (endTyped.equals("HUP:FAR")) {
            endtypes = "Сброс звонка Клиентом";
        } else if (endTyped.equals("HUP:NEAR")) {
            endtypes = "Робот завершил звонок";
        } else if (endTyped.equals("HUP:TRANSFER")) {
            endtypes = "Звонок переведен";
        }

        String Response = "";
        logger.log(String.valueOf(branchID));
        if (branchID.equals("-1") || branchID.equals("-2")) {
            Response = "Адрес не найден";
            this.logger.log("Response:" + Response);
        } else {
            File f = new File("/omilia/apps/DiaManT/apps/Olimp/wowsh.csv");
            this.logger.log("Response:" + f);
            String line = "";
            String splitBy = ",";
            List<List<String>> records = new ArrayList<>();
            try {
                BufferedReader br = new BufferedReader(new FileReader(f));
                while ((line = br.readLine()) != null) {
                    String[] values = line.split(splitBy);
                    records.add(Arrays.asList(values));
                }
                try {
                    br.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (RuntimeException e) {
                throw new RuntimeException(e);
            }
            Response = records.get(Integer.parseInt(branchID)).toString();
            this.logger.log("Response:" + Response);
            try {
                fileOutputStream = new FileOutputStream(file, true);
                Writer writer = new OutputStreamWriter(fileOutputStream, "UTF8");
                StringBuilder sb = new StringBuilder();
                String text;
                logger.logGreen("До создания файла");
                if (!file.exists()) {
                    logger.logGreen("Зашел в условия 1");
                    file.createNewFile();
                    sb.append("Номер телефона;Дата;Тип завершения;ID диалога;Адрес;Ответ API;Количество шагов в диалоге\n");
                    text = Ani + ";" + Timestamp + ";" + endtypes + ";" + DialogID + ";" + address + ";" + Response + ";" + stepCounter + "\n";
                    text = text.replace("undefined", "");
                    sb.append(text);
                    writer.write(sb.toString());
                    writer.close();
                } else {
                    logger.logWarning("Зашел в else");
                    if (file.length() == 0L) {
                        logger.logGreen("Зашел в else и в первое условия");
                        sb.append("Номер телефона;Дата;Тип завершения;ID диалога;Адрес;Ответ API;Количество шагов в диалоге\n");
                        text = Ani + ";" + Timestamp + ";" + endtypes + ";" + DialogID + ";" + address + ";" + Response + ";" + stepCounter + "\n";
                        text = text.replace("undefined", "");
                        sb.append(text);
                        writer.write(sb.toString());
                        writer.close();
                    } else {
                        logger.logWarning("Зашел в else и else");
                        text = Ani + ";" + Timestamp + ";" + endtypes + ";" + DialogID + ";" + address + ";" + Response + ";" + stepCounter + "\n";
                        text = text.replace("undefined", "");
                        sb.append(text);
                        writer.write(sb.toString());
                        writer.close();
                    }
                }
                this.logger.log(Ani);
                return true;
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            } catch (UnsupportedEncodingException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return true;
    }

    public boolean onApplicationClose() {
        return true;
    }

    public DataPooler getCopy() {
        return new CustomModule();
    }
}

    //    private static String getUrlContents(String theUrl) {
//        StringBuilder content = new StringBuilder();
//
//        try {
//            URL url = new URL(theUrl);
//            URLConnection urlConnection = url.openConnection();
//            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
//
//            String line;
//            while ((line = bufferedReader.readLine()) != null) {
//                content.append(line + "\n");
//            }
//
//            bufferedReader.close();
//        } catch (Exception var6) {
//            var6.printStackTrace();
//        }
//
//        return content.toString();
//    }
//








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


