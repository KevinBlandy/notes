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

				// ������������߿ɶ���
				jsonWriter.setIndent("  ");

				jsonWriter.beginObject(); // �����{
				jsonWriter.name("users"); // �����"name"

				jsonWriter.beginArray(); // �����[
				for (int i = 0; i < 10000; i++) {
					jsonWriter.beginObject(); // �����{
					jsonWriter.name("name"); // �����"name"
					jsonWriter.value(UUID.randomUUID().toString()); // �����"{uuid}"
					jsonWriter.endObject(); // �����}
				}

				jsonWriter.endArray(); // �����]

				jsonWriter.endObject(); // �����}
			}
		}
	}

	public static void read() throws Exception {
		try (BufferedReader reader = Files.newBufferedReader(Paths.get("D:\\sobig.json"))) {
			try (JsonReader jsonReader = new JsonReader(reader)) {
				while (true) {
					JsonToken jsonToken = jsonReader.peek();
					if (jsonToken == JsonToken.BEGIN_OBJECT) {
						System.out.println("��ʼ��������");
						jsonReader.beginObject();
					} else if (jsonToken == JsonToken.END_OBJECT) {
						System.out.println("����������");
						jsonReader.endObject();
					} else if (jsonToken == JsonToken.BEGIN_ARRAY) {
						System.out.println("��ʼ��������");
						jsonReader.beginArray();
					} else if (jsonToken == JsonToken.END_ARRAY) {
						System.out.println("����������");
						jsonReader.endArray();
					} else if (jsonToken == JsonToken.NAME) {
						System.out.println("������key��" + jsonReader.nextName());
					} else if (jsonToken == JsonToken.STRING) {
						System.out.println("������string value��" + jsonReader.nextString());
					} else if (jsonToken == JsonToken.NUMBER) {
						// jsonReader.nextDouble();
						// jsonReader.nextLong();
						System.out.println("������number vallue��" + jsonReader.nextInt());
					} else if (jsonToken == JsonToken.BOOLEAN) {
						System.out.println("������bool value��" + jsonReader.nextBoolean());
					} else if (jsonToken == JsonToken.NULL) {
						jsonReader.nextNull(); // void
						System.out.println("������null value�� null");
					} else if (jsonToken == JsonToken.END_DOCUMENT) {
						System.out.println("JSON�������");
						break;
					}
				}
			}
		}
	}
}
