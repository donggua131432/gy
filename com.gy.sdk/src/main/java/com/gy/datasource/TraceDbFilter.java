package com.gy.datasource;

import com.alibaba.druid.filter.FilterEventAdapter;
import com.alibaba.druid.proxy.jdbc.DataSourceProxy;
import com.alibaba.druid.proxy.jdbc.JdbcParameter;
import com.alibaba.druid.proxy.jdbc.ResultSetProxy;
import com.alibaba.druid.proxy.jdbc.StatementProxy;
import com.alibaba.druid.support.json.JSONWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.NClob;
import java.util.Date;

/**
 * 
 * @author Administrator
 *
 */
//@Component(value = "traceDbFilter")
public class TraceDbFilter extends FilterEventAdapter {

	private static final Logger log = LoggerFactory.getLogger(TraceDbFilter.class);

	public void init(DataSourceProxy dataSource) {

	}

	protected void statementExecuteBatchBefore(StatementProxy statement) {
		internalBeforeStatementExecute(statement);
	}

	protected void statementExecuteBefore(StatementProxy statement, String sql) {
		internalBeforeStatementExecute(statement);
	}

	protected void statementExecuteQueryBefore(StatementProxy statement, String sql) {
		internalBeforeStatementExecute(statement);
	}

	protected void statementExecuteUpdateBefore(StatementProxy statement, String sql) {
		internalBeforeStatementExecute(statement);
	}

	protected void statementExecuteAfter(StatementProxy statement, String sql, boolean firstResult) {
		internalAfterStatementExecute(statement);
	}

	protected void statementExecuteBatchAfter(StatementProxy statement, int[] result) {
		internalAfterStatementExecute(statement);
	}

	protected void statementExecuteQueryAfter(StatementProxy statement, String sql, ResultSetProxy resultSet) {
		internalAfterStatementExecute(statement);
	}

	protected void statementExecuteUpdateAfter(StatementProxy statement, String sql, int updateCount) {
		internalAfterStatementExecute(statement);
	}

	private final void internalBeforeStatementExecute(StatementProxy statement) {
		statement.setLastExecuteStartNano();
	}

	private final void internalAfterStatementExecute(StatementProxy statement) {
		String parameters = buildSlowParameters(statement);
		String sql = statement.getLastExecuteSql();
		log.debug("execute.sql:{},parameters:{}", sql, parameters);
	}

	/**
	 * 
	 * 
	 * @param statement
	 * @return
	 */
	private String buildSlowParameters(StatementProxy statement) {
		JSONWriter out = new JSONWriter();

		out.writeArrayStart();
		int i = 0;
		for (int parametersSize = statement.getParametersSize(); i < parametersSize; i++) {
			JdbcParameter parameter = statement.getParameter(i);
			if (i != 0) {
				out.writeComma();
			}
			if (parameter != null) {
				Object value = parameter.getValue();
				if (value == null) {
					out.writeNull();
				} else if ((value instanceof String)) {
					String text = (String) value;
					if (text.length() > 200) {
						out.writeString(text.substring(0, 197) + "...");
					} else {
						out.writeString(text);
					}
				} else if ((value instanceof Number)) {
					out.writeObject(value);
				} else if ((value instanceof Date)) {
					out.writeObject(value);
				} else if ((value instanceof Boolean)) {
					out.writeObject(value);
				} else if ((value instanceof InputStream)) {
					out.writeString("<InputStream>");
				} else if ((value instanceof NClob)) {
					out.writeString("<NClob>");
				} else if ((value instanceof Clob)) {
					out.writeString("<Clob>");
				} else if ((value instanceof Blob)) {
					out.writeString("<Blob>");
				} else {
					out.writeString('<' + value.getClass().getName() + '>');
				}
			}
		}
		out.writeArrayEnd();

		return out.toString();
	}
}
