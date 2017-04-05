package io.trane.ndbc.postgres.encoder;

import io.trane.ndbc.postgres.proto.Message.Parse;
import io.trane.ndbc.proto.BufferWriter;

public class ParseEncoder {

  public final void encode(Parse msg, BufferWriter b) {
    b.writeChar('P');

    b.writeCString(msg.destinationName);
    b.writeCString(msg.query);
    b.writeShort((short) msg.parameterTypes.length);

    for (int p : msg.parameterTypes)
      b.writeInt(p);

    b.writeLength(1);
  }
}
