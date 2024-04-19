package kr.co.lotteon.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QCsCateKey is a Querydsl query type for CsCateKey
 */
@Generated("com.querydsl.codegen.DefaultEmbeddableSerializer")
public class QCsCateKey extends BeanPath<CsCateKey> {

    private static final long serialVersionUID = -606719939L;

    public static final QCsCateKey csCateKey = new QCsCateKey("csCateKey");

    public final NumberPath<Integer> cate1 = createNumber("cate1", Integer.class);

    public final NumberPath<Integer> cate2 = createNumber("cate2", Integer.class);

    public QCsCateKey(String variable) {
        super(CsCateKey.class, forVariable(variable));
    }

    public QCsCateKey(Path<? extends CsCateKey> path) {
        super(path.getType(), path.getMetadata());
    }

    public QCsCateKey(PathMetadata metadata) {
        super(CsCateKey.class, metadata);
    }

}

