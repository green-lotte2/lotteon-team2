package kr.co.lotteon.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QCsNotice is a Querydsl query type for CsNotice
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCsNotice extends EntityPathBase<CsNotice> {

    private static final long serialVersionUID = -938643061L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QCsNotice csNotice = new QCsNotice("csNotice");

    public final NumberPath<Integer> cate1 = createNumber("cate1", Integer.class);

    public final NumberPath<Integer> cate2 = createNumber("cate2", Integer.class);

    public final StringPath content = createString("content");

    public final QCsCate1 csCate1;

    public final QCsCate2 csCate2;

    public final NumberPath<Integer> noticeno = createNumber("noticeno", Integer.class);

    public final DateTimePath<java.time.LocalDateTime> rdate = createDateTime("rdate", java.time.LocalDateTime.class);

    public final StringPath regip = createString("regip");

    public final StringPath title = createString("title");

    public final StringPath uid = createString("uid");

    public QCsNotice(String variable) {
        this(CsNotice.class, forVariable(variable), INITS);
    }

    public QCsNotice(Path<? extends CsNotice> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QCsNotice(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QCsNotice(PathMetadata metadata, PathInits inits) {
        this(CsNotice.class, metadata, inits);
    }

    public QCsNotice(Class<? extends CsNotice> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.csCate1 = inits.isInitialized("csCate1") ? new QCsCate1(forProperty("csCate1")) : null;
        this.csCate2 = inits.isInitialized("csCate2") ? new QCsCate2(forProperty("csCate2"), inits.get("csCate2")) : null;
    }

}

