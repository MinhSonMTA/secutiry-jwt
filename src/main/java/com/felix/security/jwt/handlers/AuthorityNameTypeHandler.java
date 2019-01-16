package com.felix.security.jwt.handlers;

import com.felix.security.jwt.entity.AuthorityName;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * mybatis自定义typeHandler
 *
 * @author grez
 * @since 19-1-13
 **/
public class AuthorityNameTypeHandler implements TypeHandler<AuthorityName> {

    @Override
    public void setParameter(PreparedStatement ps, int i, AuthorityName parameter, JdbcType jdbcType) throws SQLException {
        ps.setString(i, parameter.name());
    }

    @Override
    public AuthorityName getResult(ResultSet rs, String columnName) throws SQLException {
        String name = rs.getString(columnName);
        return AuthorityName.valueOf(name);
    }

    @Override
    public AuthorityName getResult(ResultSet rs, int columnIndex) throws SQLException {
        String name = rs.getString(columnIndex);
        return AuthorityName.valueOf(name);
    }

    @Override
    public AuthorityName getResult(CallableStatement cs, int columnIndex) throws SQLException {
        String name = cs.getString(columnIndex);
        return AuthorityName.valueOf(name);
    }
}
