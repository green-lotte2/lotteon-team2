package kr.co.lotteon.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QCsQna is a Querydsl query type for CsQna
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCsQna extends EntityPathBase<CsQna> {

    private static final long serialVersionUID = 2147094545L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QCsQna csQna = new QCsQna("csQna");

    public final NumberPath<Integer> answercomplete = createNumber("answercomplete", Integer.class);

    public final NumberPath<Integer> cate1 = createNumber("cate1", Integer.class);

    public final NumberPath<Integer> cate2 = createNumber("cate2", Integer.class);

    public final StringPath content = createString("content");

    public final QCsCate1 csCate1;

    public final QCsCate2 csCate2;

    public final StringPath file1 = createString("file1");

    public final StringPath file2 = createString("file2");

    public final NumberPath<Integer> parent = createNumber("parent", Integer.class);

    public final NumberPath<Integer> qnano = createNumber("qnano", Integer.class);

    public final DateTimePath<java.time.LocalDateTime> rdate = createDateTime("rdate", java.time.LocalDateTime.class);

    public final StringPath regip = createString("regip");

    public final StringPath title = createString("title");

    public final StringPath uid = createString("uid");

    public QCsQna(String variable) {
        this(CsQna.class, forVariable(variable), INITS);
    }

    public QCsQna(Path<? extends CsQna> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QCsQna(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QCsQna(PathMetadata metadata, PathInits inits) {
        this(CsQna.class, metadata, inits);
    }

    public QCsQna(Class<? extends CsQna> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.csCate1 = inits.isInitialized("csCate1") ? new QCsCate1(forProperty("csCate1")) : null;
        this.csCate2 = inits.isInitialized("csCate2") ? new QCsCate2(forProperty("csCate2"), inits.get("csCate2")) : null;
    }

}

