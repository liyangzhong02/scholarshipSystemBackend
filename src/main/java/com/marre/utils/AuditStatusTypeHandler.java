package com.marre.utils;

import com.marre.enumeration.AuditStatus;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @Class: com.marre.utils
 * @ClassName: AuditStatusTypeHandler
 * @author: Marre
 * @creat: 9月 19
 * 枚举转换工具
 */
public class AuditStatusTypeHandler extends BaseTypeHandler<AuditStatus> {
    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, AuditStatus parameter, JdbcType jdbcType) throws SQLException {
        ps.setInt(i, parameter.getValue());
    }

    @Override
    public AuditStatus getNullableResult(ResultSet rs, String columnName) throws SQLException {
        int value = rs.getInt(columnName);
        return AuditStatus.fromValue(value);
    }

    @Override
    public AuditStatus getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        int value = rs.getInt(columnIndex);
        return AuditStatus.fromValue(value);
    }

    @Override
    public AuditStatus getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        int value = cs.getInt(columnIndex);
        return AuditStatus.fromValue(value);
    }
}