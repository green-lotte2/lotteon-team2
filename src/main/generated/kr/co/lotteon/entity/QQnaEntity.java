package kr.co.lotteon.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QQnaEntity is a Querydsl query type for QnaEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QQnaEntity extends EntityPathBase<QnaEntity> {

    private static final long serialVersionUID = -216770268L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QQnaEntity qnaEntity = new QQnaEntity("qnaEntity");

    public final NumberPath<Integer> answercomplete = createNumber("answercomplete", Integer.class);

    public final NumberPath<Integer> cate1 = createNumber("cate1", Integer.class);

    public final QCate1Entity cate1Entity;

    public final NumberPath<Integer> cate2 = createNumber("cate2", Integer.class);

    public final QCate2Entity cate2Entity;

    public final StringPath content = createString("content");

    public final StringPath file1 = createString("file1");

    public final StringPath file2 = createString("file2");

    public final StringPath file3 = createString("file3");

    public final StringPath file4 = createString("file4");

    public final StringPath ono = createString("ono");

    public final NumberPath<Integer> parent = createNumber("parent", Integer.class);

    public final StringPath prodno = createString("prodno");

    public final NumberPath<Integer> qnano = createNumber("qnano", Integer.class);

    public final DateTimePath<java.time.LocalDateTime> rdate = createDateTime("rdate", java.time.LocalDateTime.class);

    public final StringPath regip = createString("regip");

    public final StringPath title = createString("title");

    public final StringPath uid = createString("uid");

    public QQnaEntity(String variable) {
        this(QnaEntity.class, forVariable(variable), INITS);
    }

    public QQnaEntity(Path<? extends QnaEntity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QQnaEntity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QQnaEntity(PathMetadata metadata, PathInits inits) {
        this(QnaEntity.class, metadata, inits);
    }

    public QQnaEntity(Class<? extends QnaEntity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.cate1Entity = inits.isInitialized("cate1Entity") ? new QCate1Entity(forProperty("cate1Entity")) : null;
        this.cate2Entity = inits.isInitialized("cate2Entity") ? new QCate2Entity(forProperty("cate2Entity"), inits.get("cate2Entity")) : null;
    }

}

