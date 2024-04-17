package kr.co.lotteon.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QFaqEntity is a Querydsl query type for CsFaq
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QFaqEntity extends EntityPathBase<FaqEntity> {

    private static final long serialVersionUID = -1676148522L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QFaqEntity faqEntity = new QFaqEntity("faqEntity");

    public final NumberPath<Integer> cate1 = createNumber("cate1", Integer.class);

    public final QCate1Entity cate1Entity;

    public final NumberPath<Integer> cate2 = createNumber("cate2", Integer.class);

    public final QCate2Entity cate2Entity;

    public final StringPath content = createString("content");

    public final NumberPath<Integer> faqno = createNumber("faqno", Integer.class);

    public final NumberPath<Integer> hit = createNumber("hit", Integer.class);

    public final DateTimePath<java.time.LocalDateTime> rdate = createDateTime("rdate", java.time.LocalDateTime.class);

    public final StringPath regip = createString("regip");

    public final StringPath title = createString("title");

    public final StringPath uid = createString("uid");

    public QFaqEntity(String variable) {
        this(FaqEntity.class, forVariable(variable), INITS);
    }

    public QFaqEntity(Path<? extends FaqEntity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QFaqEntity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QFaqEntity(PathMetadata metadata, PathInits inits) {
        this(FaqEntity.class, metadata, inits);
    }

    public QFaqEntity(Class<? extends FaqEntity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.cate1Entity = inits.isInitialized("cate1") ? new QCate1Entity(forProperty("cate1")) : null;
        this.cate2Entity = inits.isInitialized("cate2") ? new QCate2Entity(forProperty("cate2"), inits.get("cate2")) : null;
    }

}

