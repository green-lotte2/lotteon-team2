package kr.co.lotteon.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QNoticeEntity is a Querydsl query type for NoticeEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QNoticeEntity extends EntityPathBase<NoticeEntity> {

    private static final long serialVersionUID = 400953726L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QNoticeEntity noticeEntity = new QNoticeEntity("noticeEntity");

    public final NumberPath<Integer> cate1 = createNumber("cate1", Integer.class);

    public final QCate1Entity cate1Entity;

    public final NumberPath<Integer> cate2 = createNumber("cate2", Integer.class);

    public final QCate2Entity cate2Entity;

    public final StringPath content = createString("content");

    public final NumberPath<Integer> noticeno = createNumber("noticeno", Integer.class);

    public final DateTimePath<java.time.LocalDateTime> rdate = createDateTime("rdate", java.time.LocalDateTime.class);

    public final StringPath regip = createString("regip");

    public final StringPath title = createString("title");

    public final StringPath uid = createString("uid");

    public QNoticeEntity(String variable) {
        this(NoticeEntity.class, forVariable(variable), INITS);
    }

    public QNoticeEntity(Path<? extends NoticeEntity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QNoticeEntity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QNoticeEntity(PathMetadata metadata, PathInits inits) {
        this(NoticeEntity.class, metadata, inits);
    }

    public QNoticeEntity(Class<? extends NoticeEntity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.cate1Entity = inits.isInitialized("cate1Entity") ? new QCate1Entity(forProperty("cate1Entity")) : null;
        this.cate2Entity = inits.isInitialized("cate2Entity") ? new QCate2Entity(forProperty("cate2Entity"), inits.get("cate2Entity")) : null;
    }

}

