package codecool;

import DAO.DataPosts;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;

import java.io.*;
import java.net.URLDecoder;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Form implements HttpHandler {

    private DataPosts dataPosts = new DataPosts();

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        String response = "";
        String method = httpExchange.getRequestMethod();


        // Send a form if it wasn't submitted yet.
        if(method.equals("GET")){
            Map<String, String> hashMap = dataPosts.selectAllData();
            Date data = new Date();

            JtwigTemplate template = JtwigTemplate.classpathTemplate("templates/questbook.twig");
            JtwigModel model = JtwigModel.newModel();
            model.with("hashMap", hashMap);
            model.with("time", data);

            response = template.render(model);
        }

//         If the form was submitted, retrieve it's content.
        if(method.equals("POST")){
            InputStreamReader isr = new InputStreamReader(httpExchange.getRequestBody(), "utf-8");
            BufferedReader br = new BufferedReader(isr);
            String formData = br.readLine();

            System.out.println(formData);
            Map inputs = parseFormData(formData);
            dataPosts.insertRecord(inputs.get("txtName").toString(), inputs.get("txtArea").toString());
//            Map<String, String> hashMap = dataPosts.selectAllData();
//
//            JtwigTemplate template = JtwigTemplate.classpathTemplate("templates/questbook.twig");
//            JtwigModel model = JtwigModel.newModel();
//            model.with("hashMap", hashMap);
//
            response = inputs.get("txtName").toString() + inputs.get("txtArea").toString();
        }
        Headers headers= httpExchange.getResponseHeaders();
        headers.add("Content-Type", "text/html");

        httpExchange.sendResponseHeaders(200, response.length());
        OutputStream os = httpExchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }

    /**
     * Form data is sent as a urlencoded string. Thus we have to parse this string to get data that we want.
     * See: https://en.wikipedia.org/wiki/POST_(HTTP)
//     */
    private static Map<String, String> parseFormData(String formData) throws UnsupportedEncodingException {
        Map<String, String> map = new HashMap<>();
        String[] pairs = formData.split("&");
        for(String pair : pairs){
            String[] keyValue = pair.split("=");
            // We have to decode the value because it's urlencoded. see: https://en.wikipedia.org/wiki/POST_(HTTP)#Use_for_submitting_web_forms
            String value = new URLDecoder().decode(keyValue[1], "UTF-8");
            map.put(keyValue[0], value);
        }
        return map;
    }

    private String getFile(String fileName) {

        StringBuilder result = new StringBuilder("");

        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource(fileName).getFile());

        try (Scanner scanner = new Scanner(file)) {

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                result.append(line).append("\n");
            }
            scanner.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result.toString();
    }
}
