
package com.tataren.main.security;

import java.io.OutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.io.IOException;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;


public class BASE64Encoder {

	/** Stream that understands "printing" */
	private PrintStream pStream;

	/** This array maps the characters to their 6 bit values */
	private final static char pem_array[] = {
		//       0   1   2   3   4   5   6   7
		'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', // 0
		'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', // 1
		'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', // 2
		'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f', // 3
		'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', // 4
		'o', 'p', 'q', 'r', 's', 't', 'u', 'v', // 5
		'w', 'x', 'y', 'z', '0', '1', '2', '3', // 6
		'4', '5', '6', '7', '8', '9', '+', '/' // 7
	};

	/** this class encodes three bytes per atom. */
	private int bytesPerAtom() {
		return (3);
	}
	/**
	 * this class encodes 57 bytes per line. This results in a maximum
	 * of 57/3 * 4 or 76 characters per output line. Not counting the
	 * line termination.
	 */
	private int bytesPerLine() {
		return (57);
	}
	/**
	 * enocodeAtom - Take three bytes of input and encode it as 4
	 * printable characters. Note that if the length in len is less
	 * than three is encodes either one or two '=' signs to indicate
	 * padding characters.
	 */
	private void encodeAtom(
		OutputStream outStream,
		byte data[],
		int offset,
		int len)
		throws IOException {
		byte a, b, c;

		if (len == 1) {
			a = data[offset];
			b = 0;
			c = 0;
			outStream.write(pem_array[(a >>> 2) & 0x3F]);
			outStream.write(pem_array[((a << 4) & 0x30) + ((b >>> 4) & 0xf)]);
			outStream.write('=');
			outStream.write('=');
		} else if (len == 2) {
			a = data[offset];
			b = data[offset + 1];
			c = 0;
			outStream.write(pem_array[(a >>> 2) & 0x3F]);
			outStream.write(pem_array[((a << 4) & 0x30) + ((b >>> 4) & 0xf)]);
			outStream.write(pem_array[((b << 2) & 0x3c) + ((c >>> 6) & 0x3)]);
			outStream.write('=');
		} else {
			a = data[offset];
			b = data[offset + 1];
			c = data[offset + 2];
			outStream.write(pem_array[(a >>> 2) & 0x3F]);
			outStream.write(pem_array[((a << 4) & 0x30) + ((b >>> 4) & 0xf)]);
			outStream.write(pem_array[((b << 2) & 0x3c) + ((c >>> 6) & 0x3)]);
			outStream.write(pem_array[c & 0x3F]);
		}
	}
	/**
	 * A 'streamless' version of encode that simply takes a buffer of
	 * bytes and returns a string containing the encoded buffer.
	 */
	public String encodeBuffer(byte aBuffer[]) {
		ByteArrayOutputStream outStream = new ByteArrayOutputStream();
		ByteArrayInputStream inStream = new ByteArrayInputStream(aBuffer);
		try {
			encodeBuffer(inStream, outStream);
		} catch (Exception IOException) {
			// This should never happen.
			throw new Error("ChracterEncoder::encodeBuffer internal error");
		}
		return (outStream.toString());
	}
	/**
	 * Encode the buffer in <i>aBuffer</i> and write the encoded
	 * result to the OutputStream <i>aStream</i>.
	 */
	public void encodeBuffer(byte aBuffer[], OutputStream aStream)
		throws IOException {
		ByteArrayInputStream inStream = new ByteArrayInputStream(aBuffer);
		encodeBuffer(inStream, aStream);
	}
	/**
	 * Encode bytes from the input stream, and write them as text characters
	 * to the output stream. This method will run until it exhausts the
	 * input stream.
	 */
	public void encodeBuffer(InputStream inStream, OutputStream outStream)
		throws IOException {
		int j;
		int numBytes;
		byte tmpbuffer[] = new byte[bytesPerLine()];

		encodeBufferPrefix(outStream);

		while (true) {
			numBytes = readFully(inStream, tmpbuffer);
			if (numBytes == -1) {
				break;
			}
			encodeLinePrefix(outStream, numBytes);
			for (j = 0; j < numBytes; j += bytesPerAtom()) {
				if ((j + bytesPerAtom()) <= numBytes) {
					encodeAtom(outStream, tmpbuffer, j, bytesPerAtom());
				} else {
					encodeAtom(outStream, tmpbuffer, j, (numBytes) - j);
				}
			}
			encodeLineSuffix(outStream);
			if (numBytes < bytesPerLine()) {
				break;
			}
		}
		encodeBufferSuffix(outStream);
	}
	/**
	 * Encode the prefix for the entire buffer. By default is simply
	 * opens the PrintStream for use by the other functions.
	 */
	private void encodeBufferPrefix(OutputStream aStream) throws IOException {
		pStream = new PrintStream(aStream);
	}
	/**
	 * Encode the suffix for the entire buffer.
	 */
	private void encodeBufferSuffix(OutputStream aStream) throws IOException {
	}
	/**
	 * Encode the prefix that starts every output line.
	 */
	private void encodeLinePrefix(OutputStream aStream, int aLength)
		throws IOException {
	}
	/**
	 * Encode the suffix that ends every output line. By default
	 * this method just prints a <newline> into the output stream.
	 */
	private void encodeLineSuffix(OutputStream aStream) throws IOException {
		pStream.println();
	}
	/**
	 * This method works around the bizarre semantics of BufferedInputStream's
	 * read method.
	 */
	private int readFully(InputStream in, byte buffer[])
		throws java.io.IOException {
		for (int i = 0; i < buffer.length; i++) {
			int q = in.read();
			if (q == -1)
				return i;
			buffer[i] = (byte) q;
		}
		return buffer.length;
	}
}
