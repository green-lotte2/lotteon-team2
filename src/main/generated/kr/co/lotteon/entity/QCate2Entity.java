package kr.co.lotteon.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QCate2Entity is a Querydsl query type for Cate2
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCate2Entity extends EntityPathBase<Cate2Entity> {

    private static final long serialVersionUID = 981242755L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QCate2Entity cate2Entity = new QCate2Entity("cate2");

    public final StringPath c2name = createString("c2name");

    public final QCateKey cateKey;

    public QCate2Entity(String variable) {
        this(Cate2Entity.class, forVariable(variable), INITS);
    }

    public QCate2Entity(Path<? extends Cate2Entity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QCate2Entity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QCate2Entity(PathMetadata metadata, PathInits inits) {
        this(Cate2Entity.class, metadata, inits);
    }

    public QCate2Entity(Class<? extends Cate2Entity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.cateKey = inits.isInitialized("csCateKey") ? new QCateKey(forProperty("csCateKey")) : null;
    }

}

