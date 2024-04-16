package kr.co.lotteon.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QCate1Entity is a Querydsl query type for Cate1Entity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCate1Entity extends EntityPathBase<Cate1Entity> {

    private static final long serialVersionUID = 93739074L;

    public static final QCate1Entity cate1Entity = new QCate1Entity("cate1Entity");

    public final StringPath c1name = createString("c1name");

    public final NumberPath<Integer> cate1 = createNumber("cate1", Integer.class);

    public QCate1Entity(String variable) {
        super(Cate1Entity.class, forVariable(variable));
    }

    public QCate1Entity(Path<? extends Cate1Entity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QCate1Entity(PathMetadata metadata) {
        super(Cate1Entity.class, metadata);
    }

}

