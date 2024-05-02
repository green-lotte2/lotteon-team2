package kr.co.lotteon.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QReply is a Querydsl query type for Reply
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QReply extends EntityPathBase<Reply> {

    private static final long serialVersionUID = -2134407257L;

    public static final QReply reply = new QReply("reply");

    public final NumberPath<Integer> qnano = createNumber("qnano", Integer.class);

    public final StringPath rcontent = createString("rcontent");

    public final DateTimePath<java.time.LocalDateTime> rdate = createDateTime("rdate", java.time.LocalDateTime.class);

    public final NumberPath<Integer> replyno = createNumber("replyno", Integer.class);

    public final StringPath writer = createString("writer");

    public QReply(String variable) {
        super(Reply.class, forVariable(variable));
    }

    public QReply(Path<? extends Reply> path) {
        super(path.getType(), path.getMetadata());
    }

    public QReply(PathMetadata metadata) {
        super(Reply.class, metadata);
    }

}

