---------------------
Thumbnailator¿‡ø‚	 |
---------------------




import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import net.coobird.thumbnailator.Thumbnails;

public class ThumbnailatorHelper {

	/**
	 * —πÀıÕº∆¨
	 * @param inputStream
	 * @param quality
	 * @return
	 * @throws IOException
	 */
	public byte[] compress(InputStream inputStream, float quality) throws IOException {
		try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream()) {
			Thumbnails.of(inputStream).scale(1f).outputQuality(quality).toOutputStream(byteArrayOutputStream);
			return byteArrayOutputStream.toByteArray();
		}
	}
}
