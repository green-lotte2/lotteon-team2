package kr.co.lotteon.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QDelivery is a Querydsl query type for Delivery
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QDelivery extends EntityPathBase<Delivery> {

    private static final long serialVersionUID = 2117407255L;

    public static final QDelivery delivery = new QDelivery("delivery");

    public final StringPath odate = createString("odate");

    public final NumberPath<Integer> ono = createNumber("ono", Integer.class);

    public final NumberPath<Integer> pcount = createNumber("pcount", Integer.class);

    public final StringPath pname = createString("pname");

    public final NumberPath<Integer> pno = createNumber("pno", Integer.class);

    public final NumberPath<Integer> state = createNumber("state", Integer.class);

    public final StringPath stname = createString("stname");

    public final StringPath uid = createString("uid");

    public QDelivery(String variable) {
        super(Delivery.class, forVariable(variable));
    }

    public QDelivery(Path<? extends Delivery> path) {
        super(path.getType(), path.getMetadata());
    }

    public QDelivery(PathMetadata metadata) {
        super(Delivery.class, metadata);
    }

}

