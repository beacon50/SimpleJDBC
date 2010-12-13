package com.beacon50.jdbc.aws;

import com.amazonaws.services.simpledb.util.SimpleDBUtils;
import net.sf.jsqlparser.expression.*;
import net.sf.jsqlparser.expression.StringValue;
import net.sf.jsqlparser.expression.operators.arithmetic.Addition;
import net.sf.jsqlparser.expression.operators.arithmetic.Division;
import net.sf.jsqlparser.expression.operators.arithmetic.Multiplication;
import net.sf.jsqlparser.expression.operators.arithmetic.Subtraction;
import net.sf.jsqlparser.expression.operators.conditional.AndExpression;
import net.sf.jsqlparser.expression.operators.conditional.OrExpression;
import net.sf.jsqlparser.expression.operators.relational.*;
import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.statement.select.SubSelect;

/**
 *
 */
public class SQLDeleteExpressionVisitor implements ExpressionVisitor {

//    private String value;

    public void setValue(Expression express) {
        express.accept(this);
    }

    public void visit(NullValue nullValue) {

    }

    public void visit(Function function) {

    }

    public void visit(InverseExpression inverseExpression) {

    }

    public void visit(JdbcParameter jdbcParameter) {
    }

    public void visit(DoubleValue doubleValue) {
        //doubleValue.setValue();
        //SimpleDBUtils.encodeZeroPadding((float) doubleValue.getValue(), 5));
        visit(new StringValue(SimpleDBUtils.encodeZeroPadding((float) doubleValue.getValue(), 5)));
    }


    public void visit(LongValue longValue) {
        longValue.setStringValue(SimpleDBUtils.quoteValue(
                SimpleDBUtils.encodeZeroPadding(longValue.getValue(), 5)));
    }

    public void visit(DateValue dateValue) {

    }

    public void visit(TimeValue timeValue) {

    }

    public void visit(TimestampValue timestampValue) {

    }

    public void visit(Parenthesis parenthesis) {

    }

    public void visit(StringValue stringValue) {
        //if (!stringValue.getValue().startsWith("'")) {
        //    stringValue.setValue(SimpleDBUtils.quoteValue(stringValue.getValue()));
        //}
    }

    public void visit(Addition addition) {

    }

    public void visit(Division division) {

    }

    public void visit(Multiplication multiplication) {

    }

    public void visit(Subtraction subtraction) {

    }

    public void visit(AndExpression andExpression) {
//        System.out.println("and expression");
        this.visitBinaryExpression(andExpression);
    }

    public void visitBinaryExpression(BinaryExpression binaryExpression) {
        binaryExpression.getLeftExpression().accept(this);
        binaryExpression.getRightExpression().accept(this);
    }

    public void visit(OrExpression orExpression) {

    }

    public void visit(Between between) {

    }

    public void visit(EqualsTo equalsTo) {
        this.visitBinaryExpression(equalsTo);
//        System.out.println("equals to! --> " + equalsTo.getStringExpression());
//        System.out.println("equals to! left " + equalsTo.getLeftExpression().toString());
//        System.out.println("equals to! right " + equalsTo.getRightExpression().toString());
    }

    public void visit(GreaterThan greaterThan) {

    }

    public void visit(GreaterThanEquals greaterThanEquals) {

    }

    public void visit(InExpression inExpression) {

    }

    public void visit(IsNullExpression isNullExpression) {

    }

    public void visit(LikeExpression likeExpression) {

    }

    public void visit(MinorThan minorThan) {

    }

    public void visit(MinorThanEquals minorThanEquals) {

    }

    public void visit(NotEqualsTo notEqualsTo) {

    }

    public void visit(Column column) {
//        System.out.println("colum is? " + column.getColumnName() + " and toString is " + column.toString());
    }

    public void visit(SubSelect subSelect) {

    }

    public void visit(ExpressionList expressionList) {
//        System.out.println("expression list visited");
    }

    public void visit(CaseExpression caseExpression) {

    }

    public void visit(WhenClause whenClause) {

    }

    public void visit(ExistsExpression existsExpression) {

    }

    public void visit(AllComparisonExpression allComparisonExpression) {

    }

    public void visit(AnyComparisonExpression anyComparisonExpression) {

    }
}
