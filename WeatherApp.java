import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class WeatherApp {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Weather Forecast");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 200);

        JLabel cityLabel = new JLabel("Enter City:");
        JTextField cityField = new JTextField(20);
        JButton searchButton = new JButton("Search");
        JLabel resultLabel = new JLabel("");

        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String city = cityField.getText();
                String apiKey = "d9fc73c69be14ddd92b9ef7b86e5d111"; // Thay YOUR_OPENWEATHERMAP_API_KEY bằng API key của bạn
                String apiUrl = "https://api.openweathermap.org/data/2.5/weather?q=" + city + "&appid=" + apiKey;

                try {
                    URL url = new URL(apiUrl);
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("GET");

                    int responseCode = connection.getResponseCode();
                    if (responseCode == HttpURLConnection.HTTP_OK) {
                        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                        StringBuilder response = new StringBuilder();
                        String line;

                        while ((line = reader.readLine()) != null) {
                            response.append(line);
                        }
                        reader.close();

                        // Xử lý thông tin thời tiết từ response (JSON/XML) và cập nhật resultLabel
                        String weatherInfo = parseWeatherData(response.toString());
                        resultLabel.setText("Weather for " + city + ": " + weatherInfo);
                    } else {
                        resultLabel.setText("Unable to fetch weather data.");
                    }
                } catch (IOException ex) {
                    ex.printStackTrace();
                    resultLabel.setText("Error: " + ex.getMessage());
                }
            }
        });

        Container container = frame.getContentPane();
        container.setLayout(new FlowLayout());
        container.add(cityLabel);
        container.add(cityField);
        container.add(searchButton);
        container.add(resultLabel);

        frame.setVisible(true);
    }

    private static String parseWeatherData(String jsonData) {
        // Điều này là nơi bạn xử lý dữ liệu thời tiết từ JSON hoặc XML response từ API
        // Bạn cần phân tích dữ liệu và trích xuất thông tin thời tiết mà bạn muốn hiển thị
        // Ví dụ:
        // JSONObject jsonObject = new JSONObject(jsonData);
        // String temperature = jsonObject.getJSONObject("main").getString("temp");
        // String description = jsonObject.getJSONArray("weather").getJSONObject(0).getString("description");
        // return "Temperature: " + temperature + "°C, " + description;

        // Trong ví dụ này, chúng ta giả định rằng thông tin thời tiết được lấy từ JSON response.
        return "Temperature: 25°C, Sunny";
    }
}
