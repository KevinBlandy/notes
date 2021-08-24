import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.UUID;

import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

public class MainTest {

	public static void main(String[] args) throws Exception {
		write();
		read();
	}

	public static void write() throws Exception {
		try (BufferedWriter writer = Files.newBufferedWriter(Paths.get("D:\\sobig.json"), StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING)) {

			try (JsonWriter jsonWriter = new JsonWriter(writer)) {

				// 设置缩进，提高可读性
				jsonWriter.setIndent("  ");

				jsonWriter.beginObject(); // 输出：{
				jsonWriter.name("users"); // 输出："name"

				jsonWriter.beginArray(); // 输出：[
				for (int i = 0; i < 10000; i++) {
					jsonWriter.beginObject(); // 输出：{
					jsonWriter.name("name"); // 输出："name"
					jsonWriter.value(UUID.randomUUID().toString()); // 输出："{uuid}"
					jsonWriter.endObject(); // 输出：}
				}

				jsonWriter.endArray(); // 输出：]

				jsonWriter.endObject(); // 输出：}
			}
		}
	}

	public static void read() throws Exception {
		try (BufferedReader reader = Files.newBufferedReader(Paths.get("D:\\sobig.json"))) {
			try (JsonReader jsonReader = new JsonReader(reader)) {
				while (true) {
					JsonToken jsonToken = jsonReader.peek();
					if (jsonToken == JsonToken.BEGIN_OBJECT) {
						System.out.println("开始解析对象");
						jsonReader.beginObject();
					} else if (jsonToken == JsonToken.END_OBJECT) {
						System.out.println("对象解析完毕");
						jsonReader.endObject();
					} else if (jsonToken == JsonToken.BEGIN_ARRAY) {
						System.out.println("开始解析数组");
						jsonReader.beginArray();
					} else if (jsonToken == JsonToken.END_ARRAY) {
						System.out.println("数组解析完毕");
						jsonReader.endArray();
					} else if (jsonToken == JsonToken.NAME) {
						System.out.println("解析到key：" + jsonReader.nextName());
					} else if (jsonToken == JsonToken.STRING) {
						System.out.println("解析到string value：" + jsonReader.nextString());
					} else if (jsonToken == JsonToken.NUMBER) {
						// jsonReader.nextDouble();
						// jsonReader.nextLong();
						System.out.println("解析到number vallue：" + jsonReader.nextInt());
					} else if (jsonToken == JsonToken.BOOLEAN) {
						System.out.println("解析到bool value：" + jsonReader.nextBoolean());
					} else if (jsonToken == JsonToken.NULL) {
						jsonReader.nextNull(); // void
						System.out.println("解析到null value： null");
					} else if (jsonToken == JsonToken.END_DOCUMENT) {
						System.out.println("JSON解析完毕");
						break;
					}
				}
			}
		}
	}
}
