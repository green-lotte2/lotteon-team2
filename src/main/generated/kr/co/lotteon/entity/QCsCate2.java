package kr.co.lotteon.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QCsCate2 is a Querydsl query type for CsCate2
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCsCate2 extends EntityPathBase<CsCate2> {

    private static final long serialVersionUID = 1760260528L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QCsCate2 csCate2 = new QCsCate2("csCate2");

    public final StringPath c2name = createString("c2name");

    public final QCsCateKey csCateKey;

    public QCsCate2(String variable) {
        this(CsCate2.class, forVariable(variable), INITS);
    }

    public QCsCate2(Path<? extends CsCate2> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QCsCate2(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QCsCate2(PathMetadata metadata, PathInits inits) {
        this(CsCate2.class, metadata, inits);
    }

    public QCsCate2(Class<? extends CsCate2> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.csCateKey = inits.isInitialized("csCateKey") ? new QCsCateKey(forProperty("csCateKey")) : null;
    }

}

