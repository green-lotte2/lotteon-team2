package kr.co.lotteon.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QCateKey is a Querydsl query type for CateKey
 */
@Generated("com.querydsl.codegen.DefaultEmbeddableSerializer")
public class QCateKey extends BeanPath<CateKey> {

    private static final long serialVersionUID = 1290268173L;

    public static final QCateKey cateKey = new QCateKey("cateKey");

    public final NumberPath<Integer> cate1 = createNumber("cate1", Integer.class);

    public final NumberPath<Integer> cate2 = createNumber("cate2", Integer.class);

    public QCateKey(String variable) {
        super(CateKey.class, forVariable(variable));
    }

    public QCateKey(Path<? extends CateKey> path) {
        super(path.getType(), path.getMetadata());
    }

    public QCateKey(PathMetadata metadata) {
        super(CateKey.class, metadata);
    }

}

