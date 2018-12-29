package com.gy.serializ;

import com.gy.exception.SerializationException;
import org.nustaq.serialization.FSTConfiguration;
import org.nustaq.serialization.FSTObjectInput;
import org.nustaq.serialization.FSTObjectOutput;

import java.io.*;

public class ObjectSerializer implements RedisSerializer<Object> {
    public static final int BYTE_ARRAY_OUTPUT_STREAM_SIZE = 128;
    private static FSTConfiguration conf = FSTConfiguration.createDefaultConfiguration().setForceSerializable(false);

    @Override
    public byte[] serialize(Object object) throws SerializationException {
        byte[] result = new byte[0];
        if (object == null) {
            return result;
        }
        ByteArrayOutputStream byteStream = new ByteArrayOutputStream(BYTE_ARRAY_OUTPUT_STREAM_SIZE);
        if (!(object instanceof Serializable)) {
            throw new SerializationException("requires a Serializable payload "
                    + "but received an object of type [" + object.getClass().getName() + "]");
        }
        try {
            FSTObjectOutput out = this.conf.getObjectOutput(byteStream);
            out.writeObject(object);
            out.flush();
            result =  byteStream.toByteArray();
        } catch (IOException e) {
            throw new SerializationException("serialize error, object=" + object, e);
        }
        return result;
    }

    @Override
    public Object deserialize(byte[] bytes) throws SerializationException {
        Object result = null;
        if (bytes == null || bytes.length == 0) {
            return result;
        }
        try {
            ByteArrayInputStream byteStream = new ByteArrayInputStream(bytes);
            FSTObjectInput in = this.conf.getObjectInput(byteStream);
            result = in.readObject();
        } catch (IOException e) {
            throw new SerializationException("deserialize error", e);
        } catch (ClassNotFoundException e) {
            throw new SerializationException("deserialize error", e);
        }
        return result;
    }
}
