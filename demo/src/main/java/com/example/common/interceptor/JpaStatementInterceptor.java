package com.example.common.interceptor;

import com.example.common.config.BeanProvider;
import com.example.common.utils.EnvHolder;
import lombok.Getter;
import lombok.Setter;
import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.expression.*;
import net.sf.jsqlparser.expression.operators.conditional.AndExpression;
import net.sf.jsqlparser.expression.operators.relational.EqualsTo;
import net.sf.jsqlparser.expression.operators.relational.ExpressionList;
import net.sf.jsqlparser.expression.operators.relational.InExpression;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.Statements;
import net.sf.jsqlparser.statement.select.PlainSelect;
import net.sf.jsqlparser.statement.select.Select;
import net.sf.jsqlparser.statement.select.SelectBody;
import org.hibernate.resource.jdbc.spi.StatementInspector;

import java.util.stream.Collectors;

/**
 * 租户statement封装
 *
 * 作用于:(1)标注@TenantSelect的dao方法，会自动在sql where条件中添加 tableName.tenant_id in (tenantIds...)
 * 如果查询sql中涉及到多个tableName（如涉及到关联查询)，则取第一个tableName作为条件, eg: firstTableName.tenantId in (...)
 *  (2)标注于@SystemTypeSelect的dao 方法,会自动在sql where 条件中条件tableName.system_type = system_type,
 *  如果查询sql中涉及到多个tableName（如涉及到关联查询)，则取第一个tableName作为条件, eg: firstTableName.system_type = (...)

 */
@Getter
@Setter
public class JpaStatementInterceptor implements StatementInspector {

  private static final String columnName = "tenant_id";

  JpaConfigAspect aspect = null;

  @Override
  public String inspect(String sql) {
    if (aspect == null) {
      aspect = BeanProvider.getBean(JpaConfigAspect.class);
    }

    if (!aspect.isTenantEnable() && !aspect.isSystemTypeEnable()) {
      return sql;
    }

    try {

      Statements statements = CCJSqlParserUtil.parseStatements(sql);
      for (Statement statement : statements.getStatements()) {
        if (statement instanceof Select) {
          SelectBody selectBody = ((Select) statement).getSelectBody();
          if (selectBody instanceof PlainSelect) {
            PlainSelect pselect = (PlainSelect) selectBody;
            Expression sourceWhereExp = pselect.getWhere();

            if (pselect.getFromItem().getAlias() == null) {
              pselect.getFromItem().setAlias(new Alias("entity"));
            }
            // 租户查询
            if(aspect.isTenantEnable()){
              // 创建 in 条件表达式
              InExpression tenantExp = new InExpression();
              tenantExp.setLeftExpression(new Column(pselect.getFromItem().getAlias().getName() + "." + aspect.getColumnAlias()));

              ExpressionList valueList = new ExpressionList();
              valueList.setExpressions(EnvHolder.getHolder().getTenantIds().stream().map(e -> new StringValue(e)).collect(Collectors.toList()));
              tenantExp.setRightItemsList(valueList);

              if (sourceWhereExp == null) {
                pselect.setWhere(tenantExp);
              } else {
                AndExpression andExpression = new AndExpression(sourceWhereExp, tenantExp);
                pselect.setWhere(andExpression);
              }
            }
            // 等于查询
            if(aspect.isSystemTypeEnable()){
              EqualsTo equalsExpression = new EqualsTo();
              equalsExpression.setLeftExpression(new Column(pselect.getFromItem().getAlias().getName() + "." + aspect.getSystemTypeAlias()));
              equalsExpression.setRightExpression (new LongValue(EnvHolder.getHolder().getEssSystem().longValue()));
              if(null == pselect.getWhere()){
                pselect.setWhere(equalsExpression);
              }else{
                AndExpression andEqualExpression = new AndExpression(pselect.getWhere(),equalsExpression);
                pselect.setWhere(andEqualExpression);
              }
            }
            return pselect.toString();

          }
        }
      }
    } catch (JSQLParserException e) {
      e.printStackTrace();
    }

    return sql;
  }
}
