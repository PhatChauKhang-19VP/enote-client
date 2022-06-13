package pck.enote.api.helper;

import java.util.HashMap;

public class StructClass {
    public static String pack(HashMap<String, String> data) {
        String[] keys = data.keySet().toArray(new String[0]);
        String str = "";
        for (int i = 0; i < keys.length - 1; ++i) {
            str += keys[i] + "=" + data.get(keys[i]) + ";";
        }
        int index = keys.length - 1;
        str += keys[index] + "=" + data.get(keys[index]);
        return str;
    }

    public static HashMap<String, String> unpack(String message, String... type) {
        HashMap<String, String> data = new HashMap<>();
        if (type.length == 0) {
            String[] string;
            try {
                string = message.split(";");
                for (String s : string) {
                    String[] info = s.split("=");
                    if (info.length == 2) {
                        data.put(info[0], info[1]);
                    }
                }
            } catch (Exception e) {
                System.out.println("Unpack = " + message);
            }

        }
        return data;
    }
}

