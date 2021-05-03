/* ========================================================================= *
 *                                                                           *
 *                 The QinSoft Ltd. Software License                         *
 *                                                                           *
 *         (C)Copyright 2006 QinSoft Ltd.                                    *
 *                    All rights reserved.                                   *
 *                                                                           *
 * ========================================================================= *
 *                                                                           *
 * THIS SOFTWARE IS PROVIDED   ``AS IS'' AND ANY EXPRESSED OR IMPLIED        *
 * WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES         *
 * OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE               *
 * DISCLAIMED.  IN NO EVENT SHALL THE APACHE SOFTWARE FOUNDATION OR          *
 * ITS CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,          *
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT          *
 * LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF          *
 * USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND       *
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,        *
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT        *
 * OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF        *
 * SUCH DAMAGE.                                                              *
 * ========================================================================= */

package com.tataren.main.security;

import java.io.OutputStream;
import java.io.InputStream;
import java.io.IOException;

import java.io.ByteArrayOutputStream;
import java.io.ByteArrayInputStream;


public class BASE64Decoder {

	/**
	 * This character array provides the character to value map based on
	 * RFC1521.
	 */
	private final static char pem_array[] = {
	// 0 1 2 3 4 5 6 7
			'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', // 0
			'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', // 1
			'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', // 2
			'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f', // 3
			'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', // 4
			'o', 'p', 'q', 'r', 's', 't', 'u', 'v', // 5
			'w', 'x', 'y', 'z', '0', '1', '2', '3', // 6
			'4', '5', '6', '7', '8', '9', '+', '/' // 7
	};

	private byte decode_buffer[] = new byte[4];

	/** This class has 3 bytes per atom */
	private int bytesPerAtom() {
		return (3);
	}

	/** This class has 57 bytes per encoded line */
	private int bytesPerLine() {
		return (57);
	}

	/**
	 * Decode one BASE64 atom into 1, 2, or 3 bytes of data.
	 */
	private void decodeAtom(InputStream inStream, OutputStream outStream, int l)
			throws java.io.IOException {
		int i;
		byte a = (byte) -1, b = (byte) -1, c = (byte) -1, d = (byte) -1;

		decode_buffer[0] = (byte) inStream.read();
		if (decode_buffer[0] == -1) {
			throw new IOException();
		}

		// check to see if we caught the trailing end of a <CR><LF>
		if (decode_buffer[0] == '\n') {
			i = readFully(inStream, decode_buffer, 0, 4);
		} else {
			i = readFully(inStream, decode_buffer, 1, 3);
		}
		if (i == -1) {
			throw new IOException();
		}

		for (i = 0; i < 64; i++) {
			if (decode_buffer[0] == pem_array[i]) {
				a = (byte) i;
			}
			if (decode_buffer[1] == pem_array[i]) {
				b = (byte) i;
			}
			if (decode_buffer[2] == pem_array[i]) {
				c = (byte) i;
			}
			if (decode_buffer[3] == pem_array[i]) {
				d = (byte) i;
			}
		}
		if (decode_buffer[3] == '=') { // correct length based on pad byte
			l = (decode_buffer[2] == '=') ? 1 : 2;
		}

		if ((l == 2) && (decode_buffer[3] != '=')) {
			throw new IOException("BASE64Decoder: Bad Padding byte (2).");
		}

		if ((l == 1)
				&& ((decode_buffer[2] != '=') || (decode_buffer[3] != '='))) {
			throw new IOException("BASE64Decoder: Bad Padding byte (1).");
		}

		switch (l) {
		case 1:
			outStream.write((byte) (((a << 2) & 0xfc) | ((b >>> 4) & 3)));
			break;
		case 2:
			outStream.write((byte) (((a << 2) & 0xfc) | ((b >>> 4) & 3)));
			outStream.write((byte) (((b << 4) & 0xf0) | ((c >>> 2) & 0xf)));
			break;
		case 3:
			outStream.write((byte) (((a << 2) & 0xfc) | ((b >>> 4) & 3)));
			outStream.write((byte) (((b << 4) & 0xf0) | ((c >>> 2) & 0xf)));
			outStream.write((byte) (((c << 6) & 0xc0) | (d & 0x3f)));
			break;
		}
		return;
	}

	/**
	 * Decode the contents of the inputstream into a buffer.
	 */
	public byte[] decodeBuffer(InputStream in) throws IOException {
		ByteArrayOutputStream outStream = new ByteArrayOutputStream();
		decodeBuffer(in, outStream);
		return (outStream.toByteArray());
	}

	/**
	 * Decode the text from the InputStream and write the decoded octets to the
	 * OutputStream. This method runs until the stream is exhausted.
	 * 
	 * @exception CEFormatException
	 *                An error has occured while decoding
	 * @exception CEStreamExhausted
	 *                The input stream is unexpectedly out of data
	 */
	public void decodeBuffer(InputStream aStream, OutputStream bStream)
			throws IOException {
		int i;
		int totalBytes = 0;

		decodeBufferPrefix(aStream, bStream);
		while (true) {
			int length;

			try {
				length = decodeLinePrefix(aStream, bStream);
				for (i = 0; (i + bytesPerAtom()) < length; i += bytesPerAtom()) {
					decodeAtom(aStream, bStream, bytesPerAtom());
					totalBytes += bytesPerAtom();
				}
				if ((i + bytesPerAtom()) == length) {
					decodeAtom(aStream, bStream, bytesPerAtom());
					totalBytes += bytesPerAtom();
				} else {
					decodeAtom(aStream, bStream, length - i);
					totalBytes += (length - i);
				}
				decodeLineSuffix(aStream, bStream);
			} catch (IOException e) {
				break;
			}
		}
		decodeBufferSuffix(aStream, bStream);
	}

	/**
	 * Alternate decode interface that takes a String containing the encoded
	 * buffer and returns a byte array containing the data.
	 * 
	 * @exception CEFormatException
	 *                An error has occured while decoding
	 */
	public byte[] decodeBuffer(String inputString) throws IOException {
		byte inputBuffer[] = new byte[inputString.length()];
		ByteArrayInputStream inStream;
		ByteArrayOutputStream outStream;

		// inputString.getBytes(0, inputString.length(), inputBuffer, 0);
		inputBuffer = inputString.getBytes();
		inStream = new ByteArrayInputStream(inputBuffer);
		outStream = new ByteArrayOutputStream();
		decodeBuffer(inStream, outStream);
		return (outStream.toByteArray());
	}

	/** decode the beginning of the buffer, by default this is a NOP. */
	private void decodeBufferPrefix(InputStream aStream, OutputStream bStream)
			throws IOException {
	}

	/** decode the buffer suffix, again by default it is a NOP. */
	private void decodeBufferSuffix(InputStream aStream, OutputStream bStream)
			throws IOException {
	}

	/**
	 * This method should return, if it knows, the number of bytes that will be
	 * decoded. Many formats such as uuencoding provide this information. By
	 * default we return the maximum bytes that could have been encoded on the
	 * line.
	 */
	private int decodeLinePrefix(InputStream aStream, OutputStream bStream)
			throws IOException {
		return (bytesPerLine());
	}

	/**
	 * decodeLineSuffix in this decoder simply finds the [newLine] and positions
	 * us past it.
	 */
	private void decodeLineSuffix(InputStream inStream, OutputStream outStream)
			throws java.io.IOException {
		int c;

		while (true) {
			c = inStream.read();
			if (c == -1) {
				throw new IOException();
			}
			if ((c == '\n') || (c == '\r')) {
				break;
			}
		}
	}

	/**
	 * This method works around the bizarre semantics of BufferedInputStream's
	 * read method.
	 */
	protected int readFully(InputStream in, byte buffer[], int offset, int len)
			throws java.io.IOException {
		for (int i = 0; i < len; i++) {
			int q = in.read();
			if (q == -1)
				return ((i == 0) ? -1 : i);
			buffer[i + offset] = (byte) q;
		}
		return len;
	}
}
