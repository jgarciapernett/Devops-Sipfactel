package co.com.periferia.alfa.core.jwt;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;

public class CachedBodyServletInputStream extends ServletInputStream {

	private byte[] myBytes;
	private int lastIndexRetrieved = -1;
	private InputStream cachedBodyInputStream;

	public CachedBodyServletInputStream(byte[] cachedBody) {
		this.cachedBodyInputStream = new ByteArrayInputStream(cachedBody);
	}

	@Override
	public boolean isFinished() {
		return (lastIndexRetrieved == myBytes.length - 1);
	}

	@Override
	public boolean isReady() {
		return true;
	}

	@Override
	public void setReadListener(ReadListener listener) {
		/**
		 * No se usa
		 */
	}

	@Override
	public int read() throws IOException {
		return cachedBodyInputStream.read();
	}
}
