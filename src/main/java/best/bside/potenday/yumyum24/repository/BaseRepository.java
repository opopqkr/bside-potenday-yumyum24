package best.bside.potenday.yumyum24.repository;

import com.querydsl.core.Tuple;
import com.querydsl.core.types.EntityPath;
import com.querydsl.core.types.Expression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.*;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class BaseRepository {

    @Autowired
    private JPAQueryFactory jpaQueryFactory;

    protected <T> JPAQuery<T> select(Expression<T> expr) {
        return jpaQueryFactory.select(expr);
    }

    protected JPAQuery<Tuple> select(Expression<?>... exprs) {
        return jpaQueryFactory.select(exprs);
    }

    protected <T> JPAQuery<T> selectDistinct(Expression<T> expr) {
        return select(expr).distinct();
    }

    protected JPAQuery<Tuple> selectDistinct(Expression<?>... exprs) {
        return select(exprs).distinct();
    }

    protected JPAQuery<Integer> selectOne() {
        return select(Expressions.ONE);
    }

    protected JPAQuery<Integer> selectZero() {
        return select(Expressions.ZERO);
    }

    protected <T> JPAQuery<T> selectFrom(EntityPath<T> from) {
        return select(from).from(from);
    }

    protected JPAQuery<?> from(EntityPath<?> from) {
        return jpaQueryFactory.from(from);
    }

    protected JPAQuery<?> from(EntityPath<?>... from) {
        return jpaQueryFactory.from(from);
    }

    protected JPAUpdateClause update(EntityPath<?> path) {
        return jpaQueryFactory.update(path);
    }

    protected JPAInsertClause insert(EntityPath<?> path) {
        return jpaQueryFactory.insert(path);
    }

    protected JPADeleteClause delete(EntityPath<?> path) {
        return jpaQueryFactory.delete(path);
    }
}
