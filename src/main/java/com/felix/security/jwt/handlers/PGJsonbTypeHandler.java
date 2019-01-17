package com.felix.security.jwt.handlers;

import com.alibaba.fastjson.JSONObject;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;
import org.postgresql.util.PGobject;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


/**
 * @author felix-ma
 * @create 2019/1/17 18:12
 **/
public class PGJsonbTypeHandler implements TypeHandler<JSONObject> {
    private static final PGobject jsonObject = new PGobject();

    @Override
    public void setParameter(PreparedStatement ps, int i, JSONObject parameter, JdbcType jdbcType) throws SQLException {
        jsonObject.setType("jsonb");
        jsonObject.setValue(parameter.toJSONString());
        ps.setObject(i, jsonObject);
    }

    @Override
    public JSONObject getResult(ResultSet rs, String columnName) throws SQLException {
        return JSONObject.parseObject(rs.getString(columnName));
    }

    @Override
    public JSONObject getResult(ResultSet rs, int columnIndex) throws SQLException {
        return JSONObject.parseObject(rs.getString(columnIndex));
    }

    @Override
    public JSONObject getResult(CallableStatement cs, int columnIndex) throws SQLException {
        return JSONObject.parseObject(cs.getString(columnIndex));
    }
}
