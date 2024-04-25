package kr.co.lotteon.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QCsFaq is a Querydsl query type for CsFaq
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCsFaq extends EntityPathBase<CsFaq> {

    private static final long serialVersionUID = 2147083587L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QCsFaq csFaq = new QCsFaq("csFaq");

    public final NumberPath<Integer> cate1 = createNumber("cate1", Integer.class);

    public final NumberPath<Integer> cate2 = createNumber("cate2", Integer.class);

    public final StringPath content = createString("content");

    public final QCsCate1 csCate1;

    public final QCsCate2 csCate2;

    public final NumberPath<Integer> faqno = createNumber("faqno", Integer.class);

    public final NumberPath<Integer> hit = createNumber("hit", Integer.class);

    public final DateTimePath<java.time.LocalDateTime> rdate = createDateTime("rdate", java.time.LocalDateTime.class);

    public final StringPath regip = createString("regip");

    public final StringPath title = createString("title");

    public final StringPath uid = createString("uid");

    public QCsFaq(String variable) {
        this(CsFaq.class, forVariable(variable), INITS);
    }

    public QCsFaq(Path<? extends CsFaq> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QCsFaq(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QCsFaq(PathMetadata metadata, PathInits inits) {
        this(CsFaq.class, metadata, inits);
    }

    public QCsFaq(Class<? extends CsFaq> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.csCate1 = inits.isInitialized("csCate1") ? new QCsCate1(forProperty("csCate1")) : null;
        this.csCate2 = inits.isInitialized("csCate2") ? new QCsCate2(forProperty("csCate2"), inits.get("csCate2")) : null;
    }

}

