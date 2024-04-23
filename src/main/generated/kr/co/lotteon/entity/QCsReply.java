package kr.co.lotteon.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QCsReply is a Querydsl query type for CsReply
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCsReply extends EntityPathBase<CsReply> {

    private static final long serialVersionUID = 1774228951L;

    public static final QCsReply csReply = new QCsReply("csReply");

    public final StringPath content = createString("content");

    public final NumberPath<Integer> qnano = createNumber("qnano", Integer.class);

    public final DateTimePath<java.time.LocalDateTime> rdate = createDateTime("rdate", java.time.LocalDateTime.class);

    public final NumberPath<Integer> replyno = createNumber("replyno", Integer.class);

    public final StringPath title = createString("title");

    public final StringPath writer = createString("writer");

    public QCsReply(String variable) {
        super(CsReply.class, forVariable(variable));
    }

    public QCsReply(Path<? extends CsReply> path) {
        super(path.getType(), path.getMetadata());
    }

    public QCsReply(PathMetadata metadata) {
        super(CsReply.class, metadata);
    }

}

