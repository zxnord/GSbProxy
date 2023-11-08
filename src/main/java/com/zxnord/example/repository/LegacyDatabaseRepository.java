package com.zxnord.example.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.StoredProcedure;
import org.springframework.stereotype.Repository;

import java.sql.Types;
import java.util.Map;
import java.util.Collections;

@Repository
public class LegacyDatabaseRepository {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public LegacyDatabaseRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Map<String, Object> callGetProductDescWithParameters(int productId) {
        GetProductDescWithParametersStoredProcedure proc = new GetProductDescWithParametersStoredProcedure(jdbcTemplate);
        Map<String, Object> result = proc.execute(productId);
        return result;
    }

    private static class GetProductDescWithParametersStoredProcedure extends StoredProcedure {
        private static final String SP_NAME = "GetProductDesc_withparameters";

        public GetProductDescWithParametersStoredProcedure(JdbcTemplate jdbcTemplate) {
            super(jdbcTemplate, SP_NAME);

            declareParameter(new SqlParameter("PID", Types.INTEGER));
            declareParameter(new SqlOutParameter("ProductID", Types.INTEGER));
            declareParameter(new SqlOutParameter("ProductName", Types.VARCHAR));
            declareParameter(new SqlOutParameter("ProductDescription", Types.VARCHAR));

            compile();
        }

        public Map<String, Object> execute(int productId) {
            Map<String, Object> inParams = Collections.singletonMap("PID", productId);
            return execute(inParams);
        }
    }
}
