package networking;

import java.io.IOException;
import java.io.InputStream;


public class UTFInputStream{

	private InputStream is;

	public UTFInputStream(InputStream stream) {
		is = stream;
	}

	public String readUTF8() throws IOException {

		byte[] tmp = readNBytes(4);
		

		int len = 0;
		len |= (tmp[3] & 0xff);
		len <<= 8;
		len |= (tmp[2] & 0xff);
		len <<= 8;
		len |= (tmp[1] & 0xff);
		len <<= 8;
		len |= (tmp[0] & 0xff);

		byte[] bytes = readNBytes(len);
		String message = new String(bytes, "UTF-8");
		return message;
	}
	
	public void close() throws IOException {
		this.is.close();
	}
	
	private byte[] readNBytes(int n) throws IOException {
		if(n <= 0) throw new IllegalArgumentException();
		byte buf[] = new byte[n];
		
		int readcount = 0;
		while(readcount < n) {
			readcount += is.read(buf, readcount, n-readcount);
		}
		
		return buf;
	}
}


