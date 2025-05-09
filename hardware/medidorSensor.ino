#include <WiFi.h>
#include <HTTPClient.h>
#include <ArduinoJson.h>
#include "DHT.h"

#define DHTPIN 4
#define DHTTYPE DHT22
#define UV_SENSOR_PIN 34

const char* ssid = "Cebem_21";
const char* password = "Cebem2010";

const String endpoint = "https://medidor-i3nn.onrender.com/saveMeasure";

DHT dht(DHTPIN, DHTTYPE);

void setup() {
  Serial.begin(115200);
  dht.begin();

  WiFi.begin(ssid, password);
  Serial.print("Conectando a WiFi...");
  while (WiFi.status() != WL_CONNECTED) {
    delay(500);
    Serial.print(".");
  }
  Serial.println(" Conectado.");
}

void loop() {
  if ((WiFi.status() == WL_CONNECTED)) {
    HTTPClient http;

    // Lecturas del sensor
    float temperature = dht.readTemperature();
    float humidity = dht.readHumidity();
    int uvRaw = analogRead(UV_SENSOR_PIN);
    float solarRadiation = map(uvRaw, 0, 4095, 0, 15); // ajustar según sensor UV

    String mac = WiFi.macAddress();

    // Crear el JSON
    StaticJsonDocument<200> json;
    json["mac"] = mac;
    json["temperature"] = temperature;
    json["humidity"] = humidity;
    json["solarRadiation"] = solarRadiation;

    String requestBody;
    serializeJson(json, requestBody);

    http.begin(endpoint);
    http.addHeader("Content-Type", "application/json");

    int httpResponseCode = http.POST(requestBody);

    if (httpResponseCode > 0) {
      String response = http.getString();
      Serial.println("Respuesta del servidor: " + response);
    } else {
      Serial.printf("Error en POST: %s\n", http.errorToString(httpResponseCode).c_str());
    }

    http.end();
  } else {
    Serial.println("WiFi no conectado");
  }

  delay(1000); // Esperar 1 segundo
}
