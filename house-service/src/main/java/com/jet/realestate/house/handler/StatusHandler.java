package com.jet.realestate.house.handler;

import com.jet.realestate.house.common.HouseStatus;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class StatusHandler extends BaseTypeHandler<HouseStatus> {
    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, HouseStatus parameter, JdbcType jdbcType) throws SQLException {
        ps.setInt(i,parameter.getV());
    }


    @Override
    public HouseStatus getNullableResult(ResultSet resultSet, String s) throws SQLException {
        return resultSet.wasNull() ? null : HouseStatus.valueOf(resultSet.getInt(s));
    }

    @Override
    public HouseStatus getNullableResult(ResultSet resultSet, int i) throws SQLException {
        return resultSet.wasNull() ? null : HouseStatus.valueOf(i);
    }

    @Override
    public HouseStatus getNullableResult(CallableStatement callableStatement, int i) throws SQLException {
        return callableStatement.wasNull() ? null : HouseStatus.valueOf(callableStatement.getInt(i));
    }
}
