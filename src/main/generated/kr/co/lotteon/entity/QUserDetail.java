package kr.co.lotteon.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QUserDetail is a Querydsl query type for UserDetail
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QUserDetail extends EntityPathBase<UserDetail> {

    private static final long serialVersionUID = -915036833L;

    public static final QUserDetail userDetail = new QUserDetail("userDetail");

    public final NumberPath<Integer> grade = createNumber("grade", Integer.class);

    public final ComparablePath<Character> location = createComparable("location", Character.class);

    public final NumberPath<Integer> point = createNumber("point", Integer.class);

    public final StringPath uid = createString("uid");

    public QUserDetail(String variable) {
        super(UserDetail.class, forVariable(variable));
    }

    public QUserDetail(Path<? extends UserDetail> path) {
        super(path.getType(), path.getMetadata());
    }

    public QUserDetail(PathMetadata metadata) {
        super(UserDetail.class, metadata);
    }

}

