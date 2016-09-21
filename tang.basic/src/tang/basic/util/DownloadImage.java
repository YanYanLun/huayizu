package tang.basic.util;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;

public class DownloadImage {

	@DatabaseField(dataType = DataType.STRING)
	public String imgurl;
	
	@DatabaseField(dataType = DataType.BYTE_ARRAY)
	public byte[] data;

	@DatabaseField(dataType = DataType.LONG)
	public long lastuse;
	
}
