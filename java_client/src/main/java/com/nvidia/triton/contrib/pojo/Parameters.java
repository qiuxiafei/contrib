package com.nvidia.triton.contrib.pojo;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.TypeReference;
import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.ParserConfig;
import com.alibaba.fastjson.parser.deserializer.ObjectDeserializer;
import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.ObjectSerializer;
import com.alibaba.fastjson.serializer.SerializeConfig;

/**
 * This class represent
 * <a href="https://github.com/kubeflow/kfserving/blob/master/docs/predict-api/v2/rest_predict_v2.yaml#L254">parameters</a>
 * object in kfserving's v2 rest schema is just a JSON string, and offer some util methods.
 * When serializing/deserializing, Parameters should act just like a map.
 */
public class Parameters {

    static {
        SerializeConfig.getGlobalInstance().put(Parameters.class, new ParamSerDeser());
        ParserConfig.getGlobalInstance().putDeserializer(Parameters.class, new ParamSerDeser());
    }

    public final static String KEY_BINARY_DATA_SIZE = "binary_data_size";

    private Map<String, Object> params;

    public Parameters() {
        this.params = new HashMap<>();
    }

    public Parameters(Map<String, Object> params) {
        this.params = params;
    }

    /**
     * Add or over-write parameter key-values.
     *
     * @param key   name of new parameter.
     * @param value value of new parameter.
     * @return The original value if key exists.
     */
    public Object put(String key, Object value) {
        return this.params.put(key, value);
    }

    /**
     * Remove key from parameters.
     *
     * @param key name of new parameter.
     * @return The original value  if key exists.
     */
    public Object remove(String key) {
        return this.params.remove(key);
    }

    /**
     * Check if parameters are empty.
     *
     * @return true if empty.
     */
    @JSONField(serialize = false)
    public boolean isEmpty() {
        return this.params.isEmpty();
    }

    /**
     * Get an parameter value as bool. Some conversions are done under the hood.
     *
     * @param name name of the parameter.
     * @return null if named parameter not exists, otherwise the parameter value in bool format.
     * @throws IllegalArgumentException if conversions failed.
     */
    public Boolean getBool(final String name) {
        Object o = this.params.get(name);
        if (o == null) {
            return null;
        } else if (o instanceof Boolean) {
            return (Boolean)o;
        } else if (o instanceof String) {
            return Boolean.valueOf(((String)o));
        } else {
            throw new IllegalArgumentException(
                String.format("Could not convert type %s to Boolean", o.getClass().getCanonicalName()));
        }
    }

    /**
     * Get an parameter value as integer. Some conversions are done under the hood.
     *
     * @param name name of the parameter.
     * @return null if named parameter not exists, otherwise the parameter value in integer format.
     * @throws IllegalArgumentException if conversions failed.
     */
    public Integer getInt(final String name) {
        Object o = this.params.get(name);
        if (o == null) {
            return null;
        } else if (o instanceof Number) {
            return ((Number)o).intValue();
        } else if (o instanceof String) {
            return Integer.valueOf(((String)o));
        } else {
            throw new IllegalArgumentException(
                String.format("Could not convert type %s to Integer", o.getClass().getCanonicalName()));
        }
    }

    /**
     * Get an parameter value as float. Some conversions are done under the hood.
     *
     * @param name name of the parameter.
     * @return null if named parameter not exists, otherwise the parameter value in float format.
     * @throws IllegalArgumentException if conversions failed.
     */
    public Float getFloat(final String name) {
        Object o = this.params.get(name);
        if (o == null) {
            return null;
        } else if (o instanceof Number) {
            return ((Number)o).floatValue();
        } else if (o instanceof String) {
            return Float.valueOf(((String)o));
        } else {
            throw new IllegalArgumentException(
                String.format("Could not convert type %s to Float", o.getClass().getCanonicalName()));
        }
    }

    /**
     * Get an parameter value as double. Some conversions are done under the hood.
     *
     * @param name name of the parameter.
     * @return null if named parameter not exists, otherwise the parameter value in double format.
     * @throws IllegalArgumentException if conversions failed.
     */
    public Double getDouble(final String name) {
        Object o = this.params.get(name);
        if (o == null) {
            return null;
        } else if (o instanceof Number) {
            return ((Number)o).doubleValue();
        } else if (o instanceof String) {
            return Double.valueOf(((String)o));
        } else {
            throw new IllegalArgumentException(
                String.format("Could not convert type %s to Double", o.getClass().getCanonicalName()));
        }
    }

    /**
     * Get an parameter value as string.
     *
     * @param name name of the parameter.
     * @return null if named parameter not exists, otherwise the parameter value in string format.
     */
    public String getString(final String name) {
        Object o = this.params.get(name);
        if (o == null) {
            return null;
        } else {
            return String.valueOf(o);
        }
    }

    static class ParamSerDeser implements ObjectSerializer, ObjectDeserializer {
        @Override
        public void write(JSONSerializer serializer, Object object, Object fieldName, Type fieldType, int features)
            throws IOException {
            serializer.write(((Parameters)object).params);
        }

        @Override
        public <T> T deserialze(DefaultJSONParser parser, Type type, Object fieldName) {
            Type t = new TypeReference<Map<String, Object>>() {}.getType();
            Map<String, Object> param = parser.parseObject(t);
            return (T)new Parameters(param);
        }

        @Override
        public int getFastMatchToken() {
            return 0;
        }
    }
}
