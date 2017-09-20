package io.trane.ndbc.mysql.proto.marshaller;

import static io.trane.ndbc.mysql.proto.Message.*;

import io.trane.ndbc.mysql.proto.Collation;
import io.trane.ndbc.mysql.proto.PacketBufferWriter;
import io.trane.ndbc.proto.BufferWriter;

import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import static io.trane.ndbc.mysql.proto.ClientCapabilities.*;

/**
 * https://mariadb.com/kb/en/library/1-connecting-connecting/#handshake-response-packet
 * https://github.com/mysql/mysql-connector-j/blob/83c6dc41b96809df81444362933043b20a1d49d5/src/com/mysql/jdbc/MysqlIO.java#L1271
 * https://github.com/twitter/finagle/blob/7610016b6d01a267c4cc824a1753cce1eb81d2d2/finagle-mysql/src/main/scala/com/twitter/finagle/mysql/Result.scala
 */
public class HandshakeResponsePacketMarshaller {

  public static long MAX_3_BYTES = 255 * 255 * 255;

  public static long BASE_CAPABILITIES = CLIENT_PLUGIN_AUTH |
          CLIENT_PROTOCOL_41 |
          CLIENT_TRANSACTIONS |
          CLIENT_MULTI_RESULTS |
          CLIENT_SECURE_CONNECTION;



  public void encode(HandshakeResponseMessage message, BufferWriter b, Charset charset) {
    PacketBufferWriter packet = new PacketBufferWriter(b, message.sequence, charset);
    long clientCapabilities =  BASE_CAPABILITIES;

    if(message.database.isPresent()) {
      clientCapabilities |= CLIENT_CONNECT_WITH_DB;
    }

    packet.writeUnsignedInt(clientCapabilities);
    packet.writeUnsignedInt(MAX_3_BYTES);
    int collationId = Collation.getCollationByEncoding(message.encoding).id;
    packet.writeByte((byte) collationId);
    packet.writeBytes(new byte[23]);
    packet.writeCString(message.username);
    if(message.password.isPresent()) {
      if(message.authenticationMethod != "mysql_native_password") {
        throw new IllegalArgumentException("authenticationMethod not supported"); // TODO create exception;
      }
      byte[] bytes = scramble411(message.password.get(), message.seed, charset);
      packet.writeByte((byte) 0x14);
      packet.writeBytes(bytes);
    } else {
      packet.writeByte((byte) 0);
    }

    message.database.ifPresent(packet::writeCString);
    packet.writeCString(message.authenticationMethod);
    packet.flush();
  }

  public static byte[] scramble411(final String password, final byte[] seed, final Charset charset) {
    try {
      MessageDigest messageDigest = MessageDigest.getInstance("SHA-1");
      final byte[] initialDigest = messageDigest.digest(password.getBytes(charset));
      final byte[] finalDigest = messageDigest.digest(initialDigest);
      messageDigest.reset();
      messageDigest.update(seed);
      messageDigest.update(finalDigest);
      final byte[] result = messageDigest.digest();
      int counter = 0;
      while(counter  < result.length) {
        result[counter] = (byte) (result[counter] ^initialDigest[counter]);
        counter ++;
      }
      return result;
    } catch (NoSuchAlgorithmException e) {
      throw new RuntimeException("NoSuchAlgorithmException []", e); // TODO create exception
    }
  }
}