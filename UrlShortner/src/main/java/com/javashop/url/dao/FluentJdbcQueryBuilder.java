/*
 * FILENAME FluentJdbcQueryBuilder.java
 *
 * FILE LOCATION $Source$
 *
 * VERSION $Id$
 *
 * @version $Revision$ Check-Out Tag: $Name$ Locked By: $Lockers$
 *
 * FORMATTING NOTES * Lines should be limited to 200 characters. * Files should contain no tabs. * Indent code using four-character increments.
 *
 * COPYRIGHT
 *
 * @@@@@
 */

package com.javashop.url.dao;

import java.util.Iterator;
import java.util.List;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;

//
// IMPORTS
// NOTE: Import specific classes without using wildcards.
//

/**
 * <p>
 * JDBC statements and query builder. The class provides methods can be used to build simple and complex statements and queries.
 * </p>
 * <p>
 * <h2>Responsibilities</h2>
 * </p>
 * <p>
 * <ul>
 * <li>TODO Reponsibility #1.</li>
 * <li>TODO Reponsibility #2.</li>
 * <li>TODO Reponsibility #3.</li>
 * <li>TODO Reponsibility #4.</li>
 * </ul>
 * </p>
 *
 * @since 1.0
 * @author sukenshah
 * @version $Id$
 **/
public final class FluentJdbcQueryBuilder {

    private static final String SPACE = " ";
    private static final String COMMA = ", ";

    public static QueryStatement select() {
        return new QueryStatement(Type.SELECT);
    }

    public static QueryStatement insert() {
        return new QueryStatement(Type.INSERT);
    }

    public static QueryStatement update() {
        return new QueryStatement(Type.UPDATE);
    }

    public static QueryStatement delete() {
        return new QueryStatement(Type.DELETE);
    }

    public static final class QueryStatement {
        private StringBuilder sb = new StringBuilder();
        private final Type type;
        private String table;
        private final List<String> fields = Lists.newArrayList();
        private boolean changed = true;
        private Condition condition;

        private QueryStatement(final Type type) {
            this.type = type;
        }

        public String getQuery() {
            build();
            return sb.toString();
        }

        public QueryStatement addFields(String... fieldsIn) {
            changed = true;
            for (String field : fieldsIn) {
                fields.add(field);
            }
            return this;
        }

        public QueryStatement from(final String tableName) {
            changed = true;
            this.table = tableName;
            return this;
        }

        public Condition where() {
            condition = new Condition();
            return condition;
        }

        private void build() {
            if (!changed)
                return;

            switch (type) {
                case SELECT:
                    buildSelectQuery();
                    break;
                case INSERT:
                    buildInsertStatement();
                    break;
                case UPDATE:
                    buildUpdateStatement();
                    break;
                case DELETE:
                    buildDeleteStatement();
                    break;
                default:
                    break;
            }
            changed = false;
        }

        private void buildSelectQuery() {
            String queryText = type.getQueryText();
            queryText = queryText.replaceAll("_table_", table);

            if (fields.isEmpty())
                queryText = queryText.replaceAll("_fields_", "*");
            else {
                // add select fields
                StringBuilder fieldsStr = new StringBuilder();
                Iterator<String> fieldsIterator = fields.iterator();
                while (fieldsIterator.hasNext()) {
                    fieldsStr.append(fieldsIterator.next());
                    if (fieldsIterator.hasNext())
                        fieldsStr.append(COMMA);
                }
                queryText = queryText.replaceAll("_fields_", fieldsStr.toString());
            }

            sb = new StringBuilder(queryText);
            //TODO: add where part here
            if (condition != null) {
                sb.append("where ").append(condition.getConditionText()).append(SPACE);
            }
        }

        private void buildInsertStatement() {

        }

        private void buildUpdateStatement() {

        }

        private void buildDeleteStatement() {

        }
    }

    public static final class Condition {
        private final List<Condition> conditions = Lists.newArrayList();
        private boolean leafCondition = true;
        private String field;
        private String operator;
        private String combination;
        private Object param;

        private Condition() {
        }

        private Condition(final String field, final String operator, final Object param, final String combination) {
            this.field = field;
            this.operator = operator;
            this.param = param;
            this.combination = combination;
        }

        public Condition field(final String fieldIn) {
            this.field = fieldIn;
            return this;
        }

        public Condition eq(final Object param) {
            return setParam(" = ", param);
        }

        public Condition ne(final Object param) {
            return setParam(" != ", param);
        }

        public Condition le(final Object param) {
            return setParam("<= ", param);
        }

        public Condition ge(final Object param) {
            return setParam(" >= ", param);
        }

        public Condition lt(final Object param) {
            return setParam(" < ", param);
        }

        public Condition gt(final Object param) {
            return setParam(" > ", param);
        }

        public Condition and() {
            leafCondition = false;
            conditions.add(new Condition(field, operator, param, " and "));
            reset();
            Condition newCondition = new Condition();
            conditions.add(newCondition);
            return newCondition;
        }

        public Condition or() {
            leafCondition = false;
            conditions.add(new Condition(field, operator, param, " or "));
            reset();
            Condition newCondition = new Condition();
            conditions.add(newCondition);
            return newCondition;
        }

        public Condition wrapper() {
            leafCondition = false;
            reset();
            return this;
        }

        private Condition setParam(final String operator, final Object param) {
            this.operator = operator;
            this.param = param;
            return this;
        }

        private String getConditionText() {
            if (leafCondition) {
                System.err.println("Param is = " + param);
                if (Strings.isNullOrEmpty(combination))
                    return new StringBuilder().append(field).append(operator).append("?").append(SPACE).toString();
                else
                    return new StringBuilder().append(field).append(operator).append("?").append(combination).toString();
            }

            // is a wrapper condition
            StringBuilder sb = new StringBuilder("( ");
            for (Condition condition : conditions) {
                sb.append(condition.getConditionText());
            }
            sb.append(") ");
            return sb.toString();
        }

        private void reset() {
            this.field = null;
            this.combination = null;
            this.param = null;
            this.operator = null;
        }
    }

    private enum Type {
        SELECT("select _fields_ from _table_ "),
        INSERT("insert into _table_ (_fields_) values (_values_) "),
        UPDATE("update _table_ set "),
        DELETE("delete from _table_ ");

        private final String querytext;

        private Type(String text) {
            this.querytext = text;
        }

        public String getQueryText() {
            return querytext;
        }
    }
}
