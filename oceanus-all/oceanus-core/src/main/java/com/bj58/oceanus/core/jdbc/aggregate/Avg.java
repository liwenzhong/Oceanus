/*
 *  Copyright Beijing 58 Information Technology Co.,Ltd.
 *
 *  Licensed to the Apache Software Foundation (ASF) under one
 *  or more contributor license agreements.  See the NOTICE file
 *  distributed with this work for additional information
 *  regarding copyright ownership.  The ASF licenses this file
 *  to you under the Apache License, Version 2.0 (the
 *  "License"); you may not use this file except in compliance
 *  with the License.  You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing,
 *  software distributed under the License is distributed on an
 *  "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 *  KIND, either express or implied.  See the License for the
 *  specific language governing permissions and limitations
 *  under the License.
 */
package com.bj58.oceanus.core.jdbc.aggregate;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.bj58.oceanus.core.jdbc.result.RowSet;

/**
 * 平均值
 * 
 * @author Service Platform Architecture Team (spat@58.com)
 */
public class Avg implements Aggregate<Number> {
	private String name;
	private String key;
	private Sum sum;
	private Count count;
	private final int resultIndex;

	public Avg(Sum sum, Count count, String key, int resultIndex) {
		this.sum = sum;
		this.count = count;
		this.name = sum.getColumnName();
		this.key = key;
		this.resultIndex = resultIndex;
	}

	@Override
	public String key() {
		return key;
	}

	@Override
	public void addRow(ResultSet result, RowSet item) {

	}

	@Override
	public Number value() throws SQLException {
		Number sumValue=sum.value();
		if(sumValue==null){
			return null;
		}
		BigDecimal sumDecimal=new BigDecimal(sumValue.toString()),countDecimal=new BigDecimal(count.value().toString());
		return sumDecimal.divide(countDecimal,4,RoundingMode.FLOOR);
	}

	@Override
	public String getColumnName() {
		return name;
	}

	@Override
	public int resultIndex() {
		return resultIndex;
	}
}
