package kr.co.lotteon.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QCsCate1 is a Querydsl query type for CsCate1
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCsCate1 extends EntityPathBase<CsCate1> {

    private static final long serialVersionUID = 1760260527L;

    public static final QCsCate1 csCate1 = new QCsCate1("csCate1");

    public final StringPath c1name = createString("c1name");

    public final NumberPath<Integer> cate1 = createNumber("cate1", Integer.class);

    public QCsCate1(String variable) {
        super(CsCate1.class, forVariable(variable));
    }

    public QCsCate1(Path<? extends CsCate1> path) {
        super(path.getType(), path.getMetadata());
    }

    public QCsCate1(PathMetadata metadata) {
        super(CsCate1.class, metadata);
    }

}

